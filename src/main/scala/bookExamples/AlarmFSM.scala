import  chisel3._ 
import  chisel3.util._ 
import  _root_.circt.stage.ChiselStage

class  AlarmFSM  extends  Module
{
    val  io  =  IO(new  Bundle{ val  badEvent  =  Input(UInt(1.W))
                                val  clear     =  Input(UInt(1.W))
                                val  alarm     =  Output(Bool())   })

    object  State  extends  ChiselEnum
    {
        val  GREEN  = Value("b00".U)
        val  ORANGE = Value("b01".U)
        val  RED    = Value("b10".U)
    }

    import  State._
    
    val  stateReg      =  RegInit(GREEN)


    switch(stateReg)
    {
        is(GREEN)
        {
            when(io.badEvent === 1.U)
            {
                stateReg  :=  ORANGE
            }
        }

        is(ORANGE)
        {
            when(io.badEvent === 1.U)
            {
                stateReg  :=  RED
            }
            .elsewhen(io.clear === 1.U)
            {
                stateReg  :=  GREEN
            }
        }

        is(State.RED)
        {
            when((io.clear === 1.U)&&(io.badEvent === 0.U))
            {
                stateReg  :=  GREEN
            }
        }
    }

    io.alarm  :=  (stateReg === RED)
}

object  mainAlarmFSM  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  AlarmFSM,
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}