import  chisel3._ 
import  chisel3.util._
import  _root_.circt.stage.ChiselStage

class  ALU  extends  Module
{
    val  io  =  IO(new  Bundle{ val  a  =  Input(SInt(16.W))
                                val  b  =  Input(SInt(16.W))
                                val  fn =  Input(UInt(2.W))
                                val  sum  =  Output(SInt(16.W))})

    io.sum  :=  0.S

    switch(io.fn)
    {
        is (0.U(2.W))
        {
            io.sum  :=  io.a + io.b
        }

        is (1.U(2.W))
        {
            io.sum  :=  io.a - io.b
        }
        is (2.U(2.W))
        {
            io.sum  :=  io.a | io.b
        }

        is (3.U(2.W))
        {
            io.sum  :=  io.a & io.b
        }
    }
}

object  mainALU  extends  App
{
    ChiselStage.emitSystemVerilogFile(new  ALU,
                                                Array("--target-dir", "generated"), 
                                                firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}