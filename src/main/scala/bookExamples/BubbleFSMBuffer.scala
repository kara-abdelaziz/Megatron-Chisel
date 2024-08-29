import  chisel3._
import  chisel3.util._ 
import  _root_.circt.stage.ChiselStage

class  WriteIO(n : Int)  extends  Bundle
{
    val  dataIn  =  Input(UInt(n.W))
    val  write   =  Input(Bool())
    val  full    =  Output(Bool())
}

class  readyIO(n : Int)  extends  Bundle
{
    val  dataOut  =  Output(UInt(n.W))
    val  ready    =  Input(Bool())
    val  empty    =  Output(Bool())
}

class  BubbleFSMBuffer(n : Int)  extends  Module
{
    val  io  =  IO(new  Bundle{ val  in   =  new  WriteIO(n)
                                val  out  =  new  readyIO(n)})

       
    object  State  extends  ChiselEnum
    {
        val  EMPTY, FULL  =  Value
    }

    val  dataReg   =  Reg(UInt(n.W))
    val  stateReg  =  RegInit(State.EMPTY)

    switch(stateReg)
    {
        is(State.EMPTY)
        {
            when(io.in.write)
            {
                stateReg  :=  State.FULL
                dataReg   :=  io.in.dataIn
            }
        }
        
        is(State.FULL)
        {
            when(io.out.ready)
            {
                stateReg  :=  State.EMPTY
                dataReg   :=  0.U
            }
        }
    }    
    
    io.in.full      :=  stateReg === State.FULL
    io.out.empty    :=  stateReg === State.EMPTY
    io.out.dataOut  :=  dataReg    
}

class  BubleBufferFIFO(size : Int, depth : Int)  extends  Module
{
    val  io  =  IO(new  Bundle{ val  in   =  new  WriteIO(size)
                                val  out  =  new  readyIO(size)})

    val  buffersArr  =  Array.fill(depth){Module(new  BubbleFSMBuffer(size))}

    for(i <- 0 until size-1)
    {
        buffersArr(i+1).io.in.dataIn  :=   buffersArr(i).io.out.dataOut
        buffersArr(i+1).io.in.write   :=  !buffersArr(i).io.out.empty
        buffersArr(i).io.out.ready    :=  !buffersArr(i+1).io.in.full
    }

    val  full  =  Wire(Bool())  
    full  :=  buffersArr(0).io.in.full | buffersArr(1).io.in.full | buffersArr(2).io.in.full | buffersArr(3).io.in.full | buffersArr(4).io.in.full | buffersArr(5).io.in.full | buffersArr(6).io.in.full | buffersArr(7).io.in.full

    buffersArr(0).io.in.dataIn  :=  io.in.dataIn
    buffersArr(0).io.in.write   :=  io.in.write
    io.in.full  :=  full

    val  empty  =  Wire(Bool())  
    empty  :=  buffersArr(0).io.out.empty & buffersArr(1).io.out.empty & buffersArr(2).io.out.empty & buffersArr(3).io.out.empty & buffersArr(4).io.out.empty & buffersArr(5).io.out.empty & buffersArr(6).io.out.empty & buffersArr(7).io.out.empty
    
    buffersArr(size-1).io.out.ready  :=  io.out.ready
    io.out.dataOut  :=  buffersArr(size-1).io.out.dataOut
    io.out.empty    :=  empty
}

object  mainBubbleBufferFIFO extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  BubleBufferFIFO(8, 8),
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}