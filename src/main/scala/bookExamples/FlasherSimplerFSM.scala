import  chisel3._ 
import  chisel3.util._ 
import  _root_.circt.stage.ChiselStage

class  FlasherSimpleFSM  extends  Module
{
    val  io  =  IO(new  Bundle{ val  start  =  Input(UInt(1.W))
                                val  flash  =  Output(UInt(1.W)) })

    object  State  extends  ChiselEnum
    {
        val  OFF, FLASH, SPACE  =  Value
    }

    val  stateReg  =  RegInit(State.OFF)

    val  timer  =  Module(new  SimplerTimer)

    timer.io.select  :=  0.U 
    timer.io.load    :=  timer.io.done

    val  counter  =  Module(new  SimpleCounter)

    counter.io.load       :=  0.U
    counter.io.decrement  :=  0.U

    io.flash  :=  0.U                

    switch(stateReg)
    {
        is(State.OFF)
        {
            timer.io.load    :=  1.U
            timer.io.select  :=  0.U

            counter.io.load  :=  1.U

            io.flash  :=  0.U
            
            when(io.start.asBool)
            {
                stateReg  :=  State.FLASH
            }
        }

        is(State.FLASH)
        {
            timer.io.select  :=  1.U

            io.flash  :=  1.U
            
            when(timer.io.done.asBool)
            {
                when(counter.io.done.asBool)
                {
                    stateReg  :=  State.OFF
                }
                .otherwise
                {
                    stateReg  :=  State.SPACE

                    counter.io.decrement  :=  1.U
                }
            }
        }

        is(State.SPACE)
        {
            timer.io.select  :=  0.U

            io.flash  :=  0.U
            
            when(timer.io.done.asBool)
            {
                stateReg  :=  State.FLASH
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
    val  io  =  IO(new  Bundle{ val  load       =  Input(UInt(1.W))
                                val  decrement  =  Input(UInt(1.W))  
                                val  done       =  Output(UInt(1.W))})

    val  countReg  =  RegInit(0.U(4.W))

    io.done  :=  (countReg === 0.U)

    when(io.load.asBool)
    {
        countReg  :=  2.U
    }
    .otherwise
    {
        when((!io.done.asBool)&&(io.decrement.asBool))
        {
            countReg  :=  countReg - 1.U
        }
    }
}

object  mainFlasherSimplerFSM  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  FlasherSimpleFSM,
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}