import  chisel3._
import  chisel3.util._
import  _root_.circt.stage.ChiselStage

class  Cast  extends  Module
{
    val  io  =  IO(new  Bundle{ val  in     =  Input(UInt(8.W))
                                val  out    =  Output(SInt(8.W))  })
    
    io.out  :=  io.in.asSInt
}

object  mainCast extends  App
{
    ChiselStage.emitSystemVerilogFile(new  Cast,
                                      Array("--target-dir", "generated"), 
                                      firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}