import  chisel3._ 
import  chisel3.util._ 
import  _root_.circt.stage.ChiselStage

class  Priority4bit  extends  Module
{
    val  io  =  IO(new  Bundle{ val  req  =  Input(UInt(4.W))
                                val  grt  =  Output(UInt(4.W))})

    val  granted        =  VecInit(0.U(1.W), 0.U, 0.U, 0.U)
    val  prevNotGnted   =  VecInit(0.U(1.W), 0.U, 0.U, 0.U)

    granted(0)      := io.req(0)
    prevNotGnted(0) := ~io.req(0)

    granted(1)      := io.req(1) & prevNotGnted(0)
    prevNotGnted(1) := ~io.req(1) & prevNotGnted(0)

    granted(2)      := io.req(2) & prevNotGnted(1)
    prevNotGnted(2) := ~io.req(2) & prevNotGnted(1)

    granted(3)      := io.req(3) & prevNotGnted(2)
    prevNotGnted(3) := ~io.req(3) & prevNotGnted(2)
    
    io.grt := Cat(granted(3), granted(2), granted(1), granted(0))
}

class  Encoder4bit  extends  Module
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

class  PriorityEncoder  extends  Module
{
    val  io  =  IO(new  Bundle{ val  reqest  =  Input(UInt(4.W))
                                val  grtEnc  =  Output(UInt(2.W))})

    val  priority4bit  =  Module(new  Priority4bit())
    val  encoder4bit   =  Module(new  Encoder4bit())

    priority4bit.io.req   <>  io.reqest
    encoder4bit.io.input  <>  priority4bit.io.grt
    io.grtEnc             <>  encoder4bit.io.output
}

object  mainPriorityEncoder  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  PriorityEncoder,
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}