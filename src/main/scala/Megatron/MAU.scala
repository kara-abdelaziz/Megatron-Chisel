package  megatron

import  chisel3._
import  chisel3.util._
import  _root_.circt.stage.ChiselStage

class  MAU  extends  Module
{
    val  io  =  IO(new  Bundle{ val  data  =  Input(UInt(8.W))
                                val  x     =  Input(UInt(8.W))
                                val  y     =  Input(UInt(8.W))
                                val  highAddr  =  Input(Bool())
                                val  lowAddr   =  Input(Bool())
                                val  memAddr   =  Output(UInt(16.W)) })

    val  highOutput  =  Wire(UInt(8.W))
    val  lowOutput   =  Wire(UInt(8.W))
    
    when(io.lowAddr)
    {
        lowOutput  :=  io.x
    }
    .otherwise
    {
        lowOutput  :=  io.data
    }
    
    when(io.highAddr)
    {
        highOutput  :=  io.y
    }
    .otherwise
    {
        highOutput  :=  0.U
    }
    
    io.memAddr  :=  highOutput ## lowOutput
}

object  mainMAU extends  App
{
    ChiselStage.emitSystemVerilogFile(new  MAU,
                                      Array("--target-dir", "generated"), 
                                      firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}