package  megatron

import  chisel3._
import  chisel3.util._
import  _root_.circt.stage.ChiselStage

class  IOU  extends  Module
{
    val  io  =  IO(new  Bundle{ val  in          =  Input(UInt(8.W))
                                val  inputEnCtr  =  Input(Bool())
                                val  outputEnCtr =  Input(Bool())
                                val  ioEnCtr     =  Input(Bool())

                                val  inputEnable  =  Output(UInt(4.W))
                                val  outputWrite  =  Output(UInt(4.W))
                                val  periphralCtr =  Output(UInt(8.W)) })

    when(io.inputEnCtr)
    {
        io.inputEnable  :=  io.in(3,0)
    }
    .otherwise
    {
        io.inputEnable  :=  "b0000".U
    }

    when(io.outputEnCtr)
    {
        io.outputWrite  :=  io.in(7,4)
    }
    .otherwise
    {
        io.outputWrite  :=  "b0000".U
    }

    when(io.ioEnCtr)
    {
        io.periphralCtr  :=  io.in
    }
    .otherwise
    {
        io.periphralCtr  :=  "b0000_0000".U
    }    
}

object  mainIOU  extends  App
{
    ChiselStage.emitSystemVerilogFile(new  IOU,
                                      Array("--target-dir", "generated"), 
                                      firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}