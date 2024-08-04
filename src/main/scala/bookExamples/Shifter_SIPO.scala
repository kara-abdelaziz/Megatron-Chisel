import  chisel3._
import  _root_.circt.stage.ChiselStage

class  SIPO  extends  Module
{
    val  io  =  IO(new  Bundle{ val  serIn   =  Input(UInt(1.W))
                                val  parOut  =  Output(UInt(4.W)) })

    val  shiftReg  =  RegInit(0.U(4.W))

    shiftReg  :=  shiftReg(2,0) ## io.serIn
    
    io.parOut  :=  shiftReg
}

object  mainSIPO  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  SIPO,
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}