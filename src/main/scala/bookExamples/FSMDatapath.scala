import  chisel3._ 
import  chisel3.util._ 
import  _root_.circt.stage.ChiselStage

class  FSMDatapath  extends  Module
{
    val  io  =  IO(new  Bundle{ val  prevValid  =  Input(Bool())
                                val  nextReady  =  Input(Bool())
                                val  inData     =  Input(UInt(8.W))

                                val  prevReady  =  Output(Bool())
                                val  nextValid  =  Output(Bool())
                                val  outData    =  Output(UInt(4.W)) })

    io.prevReady  :=  false.B
    io.nextValid  :=  false.B
    io.outData    :=  0.U

    val  fsm       =  Module(new  FSM)
    val  datapath  =  Module(new  Datapath)

    fsm.io.prevValid  :=  io.prevValid
    fsm.io.nextReady  :=  io.nextReady

    io.prevReady  :=  fsm.io.prevReady
    io.nextValid  :=  fsm.io.nextValid

    datapath.io.inData  :=  io.inData

    io.outData  :=  datapath.io.outData

    datapath.io.loadData  :=  fsm.io.loadData

    fsm.io.countReady  :=  datapath.io.countReady
}

class  FSM  extends  Module
{
    val  io  =  IO(new  Bundle{ val  prevValid  =  Input(Bool())
                                val  nextReady  =  Input(Bool())
                                val  countReady =  Input(Bool())

                                val  prevReady  =  Output(Bool())
                                val  nextValid  =  Output(Bool())
                                val  loadData   =  Output(Bool()) })

    io.prevReady  :=  false.B
    io.nextValid  :=  false.B
    io.loadData   :=  true.B

    object  State  extends  ChiselEnum
    {
        val  IDLE, COUNT, DONE  =  Value
    }

    val  stateReg  =  RegInit(State.IDLE)

    switch(stateReg)
    {
        is(State.IDLE)
        {
            io.prevReady  :=  true.B
            io.nextValid  :=  false.B
            io.loadData   :=  true.B

            when(io.prevValid)
            {
                stateReg  :=  State.COUNT
            }
        }

        is(State.COUNT)
        {
            io.prevReady  :=  false.B
            io.nextValid  :=  false.B
            io.loadData   :=  false.B

            when(io.countReady)
            {
                stateReg  :=  State.DONE
            }
        }

        is(State.DONE)
        {
            io.prevReady  :=  false.B
            io.nextValid  :=  true.B
            io.loadData   :=  false.B

            when(io.nextReady)
            {
                stateReg  :=  State.IDLE
            }
        }
    }
}

class  Datapath  extends  Module
{
    val  io  =  IO(new  Bundle{ val  loadData   =  Input(Bool())
                                val  inData     =  Input(UInt(8.W))
                                val  countReady =  Output(Bool())
                                val  outData    =  Output(UInt(4.W)) })  

    val  shifterReg   =  RegInit(0.U(8.W))
    val  popCountReg  =  RegInit(0.U(4.W))
    val  counterReg   =  RegInit(8.U(4.W))

    val  shifterOutput  =  Cat(0.U(3.W), shifterReg(0))

    when(io.loadData)
    {
        shifterReg   :=  io.inData
        counterReg   :=  8.U
        popCountReg  :=  0.U
    }
    .otherwise
    {
        shifterReg   :=  Cat(0.U(1.W), shifterReg(7,1))
        popCountReg  :=  popCountReg + shifterOutput

        when(counterReg =/= 0.U)
        {
            counterReg  :=  counterReg - 1.U
        }
    }

    io.outData     :=  popCountReg
    io.countReady  :=  (counterReg === 0.U)
}

object  mainFSMDatapath  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new FSMDatapath,
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}