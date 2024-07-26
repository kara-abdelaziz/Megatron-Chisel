import  chisel3._
import _root_.circt.stage.ChiselStage

class  Mux2  extends  Module
{
    val  io  =  IO(new  Bundle{val  in_1  =  Input(Bits(1.W))
                               val  in_2  =  Input(Bits(1.W))
                               val  sel   =  Input(Bits(1.W))

                               val  output  =  Output(Bits(1.W))})

    io.output  :=  Mux(io.sel.asBool, io.in_1, io.in_2)
}

object  Mux2  extends  App
{
    ChiselStage.emitSystemVerilogFile(new Mux2, firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}