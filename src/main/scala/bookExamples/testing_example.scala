import   chisel3._
import   _root_.circt.stage.ChiselStage

class  Testing_example  extends  Module
{
    val  io  =  IO(new  Bundle{  val  a  =  Input(UInt(4.W))
                                 val  b  =  Input(UInt(4.W))
                                 val  sum  =  Output(UInt(4.W))
                                 val  equ  =  Output(Bool())

    })

    io.sum  :=  io.a + io.b
    io.equ  :=  io.a === io.b

    printf("inputs : %d, %d => %d %d \n", io.a, io.b, io.sum, io.equ)
}

object  Testing_example  extends  App
{
    ChiselStage.emitSystemVerilogFile(new Testing_example, Array("--target-dir", "generated"), firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}

