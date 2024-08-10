import  chisel3._ 
import  chisel3.util._ 
import  _root_.circt.stage.ChiselStage

class  FlasherSimpleFSM  extends  Module
{
    val  io  =  IO(new  Bundle{ val  start  =  Input(UInt(1.W))
                                val  flash  =  Output(UInt(1.W)) })

    object  State  extends  ChiselEnum
    {
        val  OFF, FLASH1, SPACE1, FLASH2, SPACE2, FLASH3  =  Value
    }

    val  stateReg  =  RegInit(State.OFF)

    val  timer  =  Module(new  SimplerTimer)

    timer.io.select  :=  0.U 

    timer.io.load  :=  timer.io.done

    io.flash  :=  0.U

    switch(stateReg)
    {
        is(State.OFF)
        {
            timer.io.load    :=  1.U
            timer.io.select  :=  0.U

            io.flash  :=  0.U
            
            when(io.start.asBool)
            {
                stateReg  :=  State.FLASH1
            }
        }

        is(State.FLASH1)
        {
            timer.io.select  :=  1.U

            io.flash  :=  1.U
            
            when(timer.io.done.asBool)
            {
                stateReg  :=  State.SPACE1
            }
        }

        is(State.SPACE1)
        {
            timer.io.select  :=  0.U

            io.flash  :=  0.U
            
            when(timer.io.done.asBool)
            {
                stateReg  :=  State.FLASH2
            }
        }

        is(State.FLASH2)
        {
            timer.io.select  :=  1.U

            io.flash  :=  1.U
            
            when(timer.io.done.asBool)
            {
                stateReg  :=  State.SPACE2
            }
        }

        is(State.SPACE2)
        {
            timer.io.select  :=  0.U

            io.flash  :=  0.U
            
            when(timer.io.done.asBool)
            {
                stateReg  :=  State.FLASH3
            }
        }

        is(State.FLASH3)
        {
            timer.io.select  :=  1.U

            io.flash  :=  1.U
            
            when(timer.io.done.asBool)
            {
                stateReg  :=  State.OFF
            }
        }
    } 

}

class  SimplerTimer  extends  Module
{
    val  io  =  IO(new  Bundle{ val  load   =  Input(UInt(1.W))
                                val  select =  Input(UInt(1.W))
                                val  done   =  Output(UInt(1.W)) })

    val  counterReg  =  RegInit(0.U(3.W))

    io.done  :=  (counterReg === 0.U)

    when(!io.done)
    {
        counterReg  :=  counterReg - 1.U
    }

    when(io.load.asBool)
    {
        when(io.select.asBool)
        {
            counterReg  :=  3.U
        }
        .otherwise
        {
            counterReg  :=  5.U
        }
    }

}

class  SimpleCounter  extends  Module
{
    val  io  =  IO(new  Bundle{ val  load  =  Input(UInt(1.W))
                                val  done  =  Output(UInt(1.W))})

    val  countReg  =  RegInit(0.U(4.W))

    io.done  :=  (countReg === 0.U)

    when(io.load.asBool)
    {
        countReg  :=  2.U
    }
    .otherwise
    {
        when(!io.done.asBool)
        {
            countReg  :=  countReg - 1.U
        }
    }
}

object  mainFlasherSimplerFSM  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  SimpleCounter,
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}