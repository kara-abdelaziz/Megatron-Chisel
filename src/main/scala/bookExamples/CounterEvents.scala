import  chisel3._ 
import  _root_.circt.stage.ChiselStage

class  EventsCounter  extends  Module
{
    val  io  =  IO(new  Bundle{ val  evt  =  Input(Bool())
                                val  cnt  =  Output(UInt(8.W)) })

    val  countReg  =  RegInit(0.U(8.W))

    io.cnt  :=  countReg
    
    when (io.evt)
    {
        countReg  :=  countReg + 1.U
    }
}

object  mainEventsCounter  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  EventsCounter,
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}