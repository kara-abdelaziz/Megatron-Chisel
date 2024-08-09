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

    val  counterReg  =  RegInit(0.U(2.W))

    timer.io.load    :=  timer_laod
    timer.io.select  :=  timer_select
    
    object  State  extends  ChiselEnum
    {
        val  ON   =  Value("b0".U)
        val  OFF  =  Value("b1".U)
    }

    val  stateReg  =  RegInit(State.ON)
    val  startReg  =  RegInit(0.U(1.W))

    io.light  :=  0.U

    when(io.start === 1.U)
    {
        startReg  :=  1.U
    }

    when(startReg === 1.U)
    {
        switch(stateReg)
        {
            is(State.ON)
            {
                io.light  :=  1.U

                timer_laod    :=  io.light & !RegNext(io.light)
                timer_select  :=  0.U

                when((timer.io.done & !RegNext(timer.io.done)).asBool)
                {
                    stateReg  :=  State.OFF
                }
            }
            
            is(State.OFF)
            {
                io.light  :=  0.U

                timer_laod    :=  !io.light & RegNext(io.light)
                timer_select  :=  1.U

                when((timer.io.done & !RegNext(timer.io.done)).asBool)
                {
                    stateReg   :=  State.ON
                    counterReg := counterReg + 1.U
                    //printf("counter = %d", counterReg)
                    when(counterReg === 2.U)
                    {
                        startReg   := 0.U
                        counterReg := 0.U
                    }
                }
            }
        }
    }
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

        when(((io.select === 0.U)&&(countReg === 2.U))||((io.select === 1.U)&&(countReg === 0.U)))
        {
            doneReg   :=  1.U
            countReg  :=  0.U
        }
    }
    
    when((doneReg === 1.U)&&(io.load === 1.U))
    {
        doneReg   :=  0.U
    }

    io.done  :=  1.U

    when((doneReg === 0.U) || ((doneReg === 1.U)&&(io.load === 1.U)))
    {
        io.done  :=  0.U
    }
}

object  mainFlasherFSM  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  TimerFSM,
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}