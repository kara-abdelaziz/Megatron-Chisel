import  chisel3._
import  chisel3.util._
import  _root_.circt.stage.ChiselStage

class  FifoIO[T <: Data](data : T)  extends  Bundle
{
    val  in   =  Flipped(new  DecoupledIO(data))
    val  out  =  new  DecoupledIO(data)
}

abstract  class  Buffer[T <: Data](data : T, depth : Int)  extends  Module
{
    val  io  =  IO(new  FifoIO(data))

    assert(depth > 0, "Invalid depth parameter value, depth should be positif.")
}

class  BubbleFIFO[T <: Data](data : T, depth : Int)  extends  Buffer(data, depth)
{
    private  class  Buffer  extends  Module
    {
        val  io  =  IO(new  FifoIO(data))

        val  fullReg  =  RegInit(false.B)
        val  dataReg  =  Reg(data)

        when(fullReg)
        {
            when(io.out.ready)
            {
                fullReg  :=  false.B
            }
        }
        .otherwise
        {
            when(io.in.valid)
            {
                fullReg  :=  true.B
                dataReg  :=  io.in.bits
            }
        }

        io.out.valid  :=  fullReg
        io.in.ready   :=  !fullReg
        io.out.bits   :=  dataReg
    }

    private  class  DoubleBuffer  extends  Module
    {
        val  io  =  IO(new  FifoIO(data))

        object  state  extends  ChiselEnum
        {
            val  EMPTY, HALF, FULL  =  Value
        }
        
        val  stateReg       =  RegInit(state.EMPTY)
        val  dataStage1Reg  =  Reg(data)
        val  dataStage2Reg  =  Reg(data)

        io.out.bits   :=  dataStage1Reg
        io.out.valid  :=  (stateReg === state.HALF) || (stateReg === state.FULL)
        io.in.ready   :=  (stateReg === state.HALF) || (stateReg === state.EMPTY)
        
        switch(stateReg)
        {
            is(state.EMPTY)
            {
                when(io.in.valid)
                {
                    dataStage1Reg  :=  io.in.bits
                    stateReg       :=  state.HALF
                }
            }

            is(state.HALF)
            {
                when(io.in.valid & io.out.ready)
                {
                    dataStage1Reg  :=  io.in.bits
                }
                .elsewhen(io.in.valid)
                {
                    dataStage2Reg  :=  io.in.bits
                    stateReg       :=  state.FULL
                }
                .elsewhen(io.out.ready)
                {
                    stateReg       :=  state.EMPTY
                }
            }

            is(state.FULL)
            {
                when(io.out.ready)
                {
                    stateReg       :=  state.HALF
                    dataStage1Reg  :=  dataStage2Reg
                }
            }
        }
    }

    private  val  bufferArr  =  Array.fill(depth/2){Module(new  DoubleBuffer)}

    for(i <- 0 until (depth/2 - 1))
    {
        bufferArr(i+1).io.in  <>  bufferArr(i).io.out
    }
    
    bufferArr(0).io.in  <>  io.in
    io.out       <>  bufferArr(depth/2 - 1).io.out
}

class  WrapAroundFIFO[T <: Data](data : T, depth : Int)  extends  Buffer(data, depth)
{
    val  mem  =  SyncReadMem(depth, data)

    val  enqPtrReg  =  RegInit(0.U((log2Ceil(depth)).W))
    val  deqPtrReg  =  RegInit(0.U((log2Ceil(depth)).W))

    val  countReg   =  RegInit(0.U((log2Ceil(depth)).W))

    val  outputReg  =  RegNext(mem.read(deqPtrReg))

    io.in.ready   := countReg < depth.U
    io.out.valid  := countReg > 0.U

    io.out.bits  := outputReg

    when(io.in.ready && io.in.valid)
    {
        mem.write(enqPtrReg, io.in.bits)
        enqPtrReg  := (enqPtrReg + 1.U) % depth.U
        countReg   := countReg + 1.U
    }

    when(io.out.valid && io.out.ready)
    {
        outputReg    := mem.read(deqPtrReg)
        deqPtrReg    := (deqPtrReg + 1.U) % depth.U
        countReg     := countReg - 1.U
    }
}


object  mainBuffer  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  WrapAroundFIFO(UInt(8.W), 8),
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}








