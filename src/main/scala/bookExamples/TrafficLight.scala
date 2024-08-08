import  chisel3._ 
import  chisel3.util._ 
import  _root_.circt.stage.ChiselStage

class  TrafficLight  extends  Module
{
    val  io  =  IO(new  Bundle{ val  detect1   =  Input(UInt(1.W))
                                val  detect2   =  Input(UInt(1.W))
                                val  roadPrio  =  Output(UInt(3.W))
                                val  roadScnd  =  Output(UInt(3.W)) })

    object  State  extends  ChiselEnum
    {
        val  RPG_RSR, RPO_RSR, RPR_RSG, RPR_RSO = Value
    }

    val  stateReg  =  RegInit(State.RPG_RSR)

    io.roadPrio  :=  "b000".U
    io.roadScnd  :=  "b000".U

    switch(stateReg)
    {
        is(State.RPG_RSR)
        {
            when((io.detect1 === 1.U)||((io.detect2 === 1.U)))
            {
                stateReg  :=  State.RPO_RSR
            }

            io.roadPrio  :=  "b001".U
            io.roadScnd  :=  "b100".U
        }

        is(State.RPO_RSR)
        {
            stateReg  :=  State.RPR_RSG

            io.roadPrio  :=  "b010".U
            io.roadScnd  :=  "b100".U
        }

        is(State.RPR_RSG)
        {
            when((io.detect1 === 0.U)&&((io.detect2 === 0.U)))
            {
                stateReg  :=  State.RPR_RSO
            }

            io.roadPrio  :=  "b100".U
            io.roadScnd  :=  "b001".U
        }

        is(State.RPR_RSO)
        {
            stateReg  :=  State.RPG_RSR

            io.roadPrio  :=  "b100".U
            io.roadScnd  :=  "b010".U
        }

        
    }
}

object  mainTrafficLight  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  TrafficLight,
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}