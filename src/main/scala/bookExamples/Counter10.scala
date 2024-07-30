import  chisel3._
import  _root_.circt.stage.ChiselStage

class  Adder  extends  Module
{
    val  io  =  IO(new  Bundle{  val  a  =  Input(UInt(4.W))
                                 val  b  =  Input(UInt(4.W))
                                 val  sum  =  Output(UInt(4.W))})
    
    io.sum  :=  io.a + io.b
}

class  Register  extends  Module
{
    val  io  =  IO(new  Bundle{  val  regIn   =  Input(UInt(4.W))
                                 val  regOut  =  Output(UInt(4.W))})

    val  reg  =  RegInit(0.U(4.W))

    reg       := io.regIn
    io.regOut := reg
}

class  Counter  extends  Module
{
    val  io  =  IO(new  Bundle{  val  count  =  Output(UInt(4.W))})

    val  adder     =  Module(new  Adder())
    val  register  =  Module(new  Register())

    adder.io.a := 1.U(4.W)
    adder.io.b := register.io.regOut

    io.count := register.io.regOut

    when (adder.io.sum === 10.U)
    {
        register.io.regIn := 0.U(4.W)
    }
    .otherwise
    {
        register.io.regIn := adder.io.sum
    }
}

object  mainCounter  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new Adder, 
                                        Array("--target-dir", "generated"), 
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))

    ChiselStage.emitSystemVerilogFile(  new Register, 
                                        Array("--target-dir", "generated"), 
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))

    ChiselStage.emitSystemVerilogFile(  new Counter, 
                                        Array("--target-dir", "generated"), 
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))

}