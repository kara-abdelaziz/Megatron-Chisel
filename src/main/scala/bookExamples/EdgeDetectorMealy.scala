import  chisel3._ 
import  chisel3.util._ 
import  _root_.circt.stage.ChiselStage

class  EdgeDetectorMealy  extends  Module
{
    val  io  =  IO(new  Bundle{ val  in   =  Input(UInt(1.W))
                                val  out  =  Output(UInt(1.W)) })

    object  State  extends  ChiselEnum
    {
        val  ZERO  =  Value(0.U(1.W))
        val  ONE   =  Value(1.U(1.W))
    }

    val  stateReg  =  RegInit(State.ZERO)

    io.out  :=  0.U
    
    switch(stateReg)
    {
        is(State.ZERO)
        {
            when(io.in === 1.U)
            {
                stateReg  :=  State.ONE
                io.out    :=  1.U
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
}

object  mainEdgeDetectorMealy  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  EdgeDetectorMealy,
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}