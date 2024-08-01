import  chisel3._
import  _root_.circt.stage.ChiselStage

class  Comparator  extends  Module
{
    val  io  =  IO(new  Bundle{  val  a  =  Input(SInt(4.W))
                                 val  b  =  Input(SInt(4.W))
                                 val  equ  =  Output(Bool())
                                 val  gt   =  Output(Bool())  })

    io.equ  :=  io.a === io.b 
    io.gt   :=  io.a > io.b
}

object  mainComparator  extends  App
{
    ChiselStage.emitSystemVerilogFile(new  Comparator,
                                      Array("--target-dir", "generated"), 
                                      firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}