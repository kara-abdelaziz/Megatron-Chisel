import  chisel3._ 
import  chisel3.util._ 
import  _root_.circt.stage.ChiselStage

class  EdgeDetectorMoore  extends  Module
{
    val  io  =  IO(new  Bundle{ val  in   =  Input(UInt(1.W))
                                val  out  =  Output(UInt(1.W)) })

    object  State  extends  ChiselEnum
    {
        val  ZERO     =  Value("b00".U)
        val  IMPULSE  =  Value("b01".U)
        val  ONE      =  Value("b10".U)
    }

    val  stateReg  =  RegInit(State.ZERO)

    switch(stateReg)
    {
        is(State.ZERO)
        {
            when(io.in === 1.U)
            {
                stateReg  :=  State.IMPULSE
            }
        }

        is(State.IMPULSE)
        {
            when(io.in === 1.U)
            {
                stateReg  :=  State.ONE
            }
            .otherwise
            {
                stateReg  :=  State.ZERO
            }
        }

        is(State.ONE)
        {
            when(io.in === 0.U)
            {
                stateReg  :=  State.ZERO
            }
        }
    }

    io.out  :=  (stateReg === State.IMPULSE)
}

object  mainEdgeDetectorMoore  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  EdgeDetectorMoore,
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}

