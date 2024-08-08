import  chisel3._ 
import  chisel3.util._ 
import  _root_.circt.stage.ChiselStage

class  FlasherFSM  extends  Module
{
    val  io  =  IO(new  Bundle{ val  start  =  Input(UInt(1.W))
                                val  light  =  Output(UInt(1.W)) })

    val  timer  =  Module(new  TimerFSM)

    val  timer_laod    =  RegInit(0.U(1.W))
    val  timer_select  =  RegInit(0.U(1.W))
    val  timer_done    =  RegInit(0.U(1.W))

    timer.io.load    :=  timer_laod
    timer.io.select  :=  timer_select
    timer_done       :=  timer.io.done

    object  State  extends  ChiselEnum
    {
        val  ON1, ON2, ON3, OFF1, OFF2, OFF3  =  Value
    }

    val  stateReg  =  RegInit(State.ON1)
    val  startReg  =  RegInit(0.U(1.W))

    io.light  :=  0.U

    when(io.start === 1.U)
    {
        startReg  :=  1.U
    }

    when(startReg === 1.U)
    {
        timer_laod  :=  1.U
        timer_select  :=  0.U

        when(timer_done === 1.U)
        {
            timer_laod  :=  0.U
            io.light    :=  1.U
        }
    }

    /*
    when(startReg === 1.U)
    {
        switch(stateReg)
        {
            is(State.ON1)
            {
                io.light  :=  1.U

                timer_laod    :=  1.U
                timer_select  :=  0.U
                timer_laod    :=  0.U

                when(timer_laod === 1.U)
                {
                    stateReg       :=  State.OFF1
                }
            }

            is(State.OFF1)
            {
                io.light  :=  0.U

                timer_laod    :=  1.U
                timer_select  :=  1.U
                timer_laod    :=  0.U

                when(timer_laod === 1.U)
                {
                    stateReg       :=  State.ON2
                }
            }

            is(State.ON2)
            {
                io.light  :=  1.U

                timer_laod    :=  1.U
                timer_select  :=  0.U
                timer_laod    :=  0.U

                when(timer_laod === 1.U)
                {
                    stateReg       :=  State.OFF2
                }
            }

            is(State.OFF2)
            {
                io.light  :=  0.U

                timer_laod    :=  1.U
                timer_select  :=  1.U
                timer_laod    :=  0.U

                when(timer_laod === 1.U)
                {
                    stateReg       :=  State.ON3
                }
            }

            is(State.ON3)
            {
                io.light  :=  1.U

                timer_laod    :=  1.U
                timer_select  :=  0.U
                timer_laod    :=  0.U

                when(timer_laod === 1.U)
                {
                    stateReg       :=  State.OFF3
                }
            }

            is(State.OFF3)
            {
                io.light  :=  0.U

                timer_laod    :=  1.U
                timer_select  :=  1.U
                timer_laod    :=  0.U

                when(timer_laod === 1.U)
                {
                    stateReg       :=  State.ON1
                    startReg       :=  0.U
                }
            }
        }
    }*/ 
}

class  TimerFSM  extends  Module
{
    val  io  =  IO(new  Bundle{ val  load    =  Input(UInt(1.W))
                                val  select  =  Input(UInt(1.W))
                                val  done    =  Output(UInt(1.W)) })
    
    val  countReg  =  RegInit(0.U(3.W))
    val  doneReg   =  RegInit(1.U(1.W))

    when(doneReg === 0.U)
    {
        countReg  :=  countReg + 1.U

        when(((io.select === 0.U)&&(countReg === 5.U))||((io.select === 1.U)&&(countReg === 3.U)))
        {
            doneReg   :=  1.U
            countReg  :=  0.U
        }
    }
    
    when((doneReg === 1.U)&&(io.load === 1.U))
    {
        doneReg   :=  0.U
    }

    io.done  :=  doneReg
}

object  mainFlasherFSM  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  TimerFSM,
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}