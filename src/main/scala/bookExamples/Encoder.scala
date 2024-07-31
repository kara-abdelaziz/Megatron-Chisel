import  chisel3._
import  chisel3.util._
import  _root_.circt.stage.ChiselStage

class  Encoder  extends  Module
{
    val  io  =  IO(new  Bundle{  val  input  =  Input(UInt(4.W))
                                 val  output =  Output(UInt(2.W))})

    
    io.output  :=  0.U(2.W)
    
    switch(io.input)
    {
        is("b0001".U) {  io.output  :=  0.U(2.W)}
        is("b0010".U) {  io.output  :=  1.U(2.W)}
        is("b0100".U) {  io.output  :=  2.U(2.W)}
        is("b1000".U) {  io.output  :=  3.U(2.W)}
    }
}

object  mainEncoder  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  Encoder,
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}