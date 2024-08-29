import  chisel3._
import  _root_.circt.stage.ChiselStage

class  BubbleBufferFIFO  extends  Module
{
    val  io  =  IO(new  Bundle{ val  dataIn   =  Input(UInt(8.W))
                                val  write    =  Input(Bool())
                                val  ready    =  Input(Bool())
                                val  dataOut  =  Output(UInt(8.W))
                                val  full     =  Output(Bool())
                                val  empty    =  Output(Bool())  })
    
    val  stackVec  =  Reg(Vec(10, UInt(8.W)))
    val  enbVec    =  VecInit(0.U(1.W), 0.U, 0.U, 0.U, 0.U, 0.U, 0.U, 0.U, 0.U, 0.U)

    val  tailReg  =  RegInit(0.U(4.W))

    io.empty  :=  tailReg === 0.U        
    io.full   :=  tailReg === 10.U

    when(io.ready && io.write)
    {
        for(i <- 0 until 10)
        {
            enbVec(i)  :=  1.U            
        }

        stackVec(tailReg)  :=  io.dataIn
    }
    .elsewhen(io.ready && !io.empty)
    {
        for(i <- 0 until 10)
        {
            enbVec(i)  :=  1.U
        }

        tailReg  :=  tailReg - 1.U
    }
    .elsewhen(io.write && !io.full)
    {
        stackVec(tailReg)  :=  io.dataIn       

        tailReg  :=  tailReg + 1.U
    }


    for(i <- 0 until 9)
    {
        when(enbVec(i) === 1.U)
        {
            stackVec(i)  :=  stackVec(i+1) 
        }
    }

    io.dataOut  :=  stackVec(0)
}

object  mainBubbleBuffer extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  BubbleBufferFIFO,
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}