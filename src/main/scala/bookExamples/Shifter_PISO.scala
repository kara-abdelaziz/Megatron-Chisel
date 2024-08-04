import  chisel3._
import  _root_.circt.stage.ChiselStage

class  PISO  extends  Module
{
    val  io  =  IO(new  Bundle{ val  parIn   =  Input(UInt(4.W))
                                val  parEn   =  Input(UInt(1.W))
                                val  serOut  =  Output(UInt(1.W)) })

    val  shiftReg  =  RegInit(0.U(4.W))

    when (io.parEn === 1.U)
    {
        shiftReg  :=  io.parIn
    }
    .otherwise
    {
        shiftReg  :=  shiftReg(2,0) ## 0.U(1.W)
    }
    
    io.serOut  :=  shiftReg(3)
}

object  mainPISO  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  PISO,
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}