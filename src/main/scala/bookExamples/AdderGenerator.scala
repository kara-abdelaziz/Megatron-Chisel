import  chisel3._ 
import  _root_.circt.stage.ChiselStage

class  AdderGenerator(n : Int)  extends  Module
{
    val  io  =  IO(new  Bundle{ val  a  =  Input(UInt(n.W))
                                val  b  =  Input(UInt(n.W))
                                val  sum  =  Output(UInt(n.W))})

    io.sum  :=  io.a + io.b
}

object  mainAdderGenerator extends  App
{
    ChiselStage.emitSystemVerilogFile(  new AdderGenerator(16),
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}