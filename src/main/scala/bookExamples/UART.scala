import  chisel3._
import  chisel3.util._
import  _root_.circt.stage.ChiselStage

class  serialIO  extends  DecoupledIO(UInt(8.W)) {}

class  TX(frequency : Int, baudRate : Int)  extends  Module
{
    val  io  =  IO(new  Bundle{ val  in      =  Flipped(new  serialIO)
                                val  txPin   =  Output(UInt(1.W))  })

    val  BAUD_TICK  =  (((frequency + (baudRate/2)) / baudRate) - 1).U

    val  tickReg    =  RegInit(0.U(20.W)) 
    val  shiftReg   =  RegInit("b11_11111111_1".U)
    val  bitCntReg  =  RegInit(0.U(4.W))

    io.txPin     :=  shiftReg(0)
    io.in.ready  :=  (bitCntReg === 0.U) && (tickReg === 0.U)

    when(tickReg === 0.U)
    {              
        tickReg  :=  BAUD_TICK
        
        when(bitCntReg === 0.U)
        {                                  
            when(io.in.valid)
            {
                shiftReg   :=  "b11".U ## io.in.bits ## "b0".U
                bitCntReg  :=  10.U
            }
            .otherwise
            {
                shiftReg  :=  "b11_11111111_1".U
            }
        }
        .otherwise
        {
            shiftReg   :=  1.U(1.W) ## shiftReg(10, 1)            
            bitCntReg  :=  bitCntReg - 1.U
        }
    }
    .otherwise
    {
        tickReg  :=  tickReg - 1.U
    }
}

class  RX(frequency : Int, baudRate : Int)  extends  Module
{
    val  io  =  IO(new  Bundle{ val  rxPin   =  Input(UInt(1.W))
                                val  out     =  new  serialIO })

    val  BAUD_TICK  =  (((frequency + (baudRate/2)) / baudRate) - 1)

    val  tickReg    =  RegInit(0.U(20.W)) 
    val  shiftReg   =  RegInit(0.U(8.W))
    val  bitCntReg  =  RegInit(0.U(4.W))

    val  rxSyncReg  =  RegNext(RegNext(io.rxPin, 1.U), 1.U)

    val  readyReg   =  RegInit(false.B)

    object  state  extends  ChiselEnum
    {
        val  IDLE, STARTING, READING, FINISHING  =  Value
    }
    
    val  stateReg   =  RegInit(state.IDLE)

    switch(stateReg)
    {
        is(state.IDLE)
        {
            tickReg    :=  (BAUD_TICK + (BAUD_TICK/2)).U
                        
            when(rxSyncReg === 0.U)
            {
                stateReg   :=  state.STARTING
            }
        }

        is(state.STARTING)
        {
            tickReg    :=  tickReg - 1.U
            bitCntReg  :=  8.U
                        
            when(tickReg === 0.U)
            {
                tickReg    :=  BAUD_TICK.U  
                stateReg   :=  state.READING
            }
        }

        is(state.READING)
        {
            tickReg  :=  tickReg - 1.U     
            
            when(tickReg === 0.U)
            {
                tickReg  :=  BAUD_TICK.U

                when(bitCntReg =/= 0.U)
                {
                    bitCntReg  :=  bitCntReg - 1.U
                    shiftReg   :=  rxSyncReg ## shiftReg(7,1)
                }
                .otherwise
                {
                    stateReg   :=  state.FINISHING
                    readyReg   :=  true.B
                }
            }
        }

        is(state.FINISHING)
        {
            val  cntReg  =  RegInit(1.U)

            tickReg  :=  tickReg - 1.U

            when(tickReg === 0.U)
            {
                tickReg  :=  BAUD_TICK.U

                when(cntReg === 1.U)
                {
                    cntReg  :=  cntReg - 1.U
                }
                .otherwise
                {
                    stateReg  :=  state.IDLE
                }
            }

        }
    }

    when((io.out.ready)&&(io.out.valid))
    {
        readyReg  :=  false.B
    }

    io.out.bits   :=  shiftReg
    io.out.valid  :=  ((bitCntReg === 0.U) && readyReg)
}

class  HelloWorldSender(frequency : Int, baudRate : Int)  extends  Module
{
    val  io  =  IO(new  Bundle{ val  txPin  =  Output(UInt(1.W))    })

    val  buffredTX  =  Module(new  BuffredTX(frequency, baudRate))

    io.txPin  :=  buffredTX.io.txPin
    
    val  msg  =  new  String("Hello World !")

    val  textVec  =  VecInit(msg.map(_.U))
    val  i        =  RegInit(0.U)

    buffredTX.io.in.valid  :=  i =/= msg.length.U
    buffredTX.io.in.bits   :=  textVec(i)

    when((buffredTX.io.in.ready)&&(i =/= msg.length.U))
    {
        i  :=  i + 1.U
    }
}

class  OneCellBuffer  extends  Module
{
    val  io  =  IO(new  Bundle{ val  in   =  Flipped(new  serialIO)
                                val  out  =  new  serialIO})

       
    object  State  extends  ChiselEnum
    {
        val  EMPTY, FULL  =  Value
    }

    val  dataReg   =  Reg(UInt(8.W))
    val  stateReg  =  RegInit(State.EMPTY)

    switch(stateReg)
    {
        is(State.EMPTY)
        {
            when(io.in.valid)
            {
                stateReg  :=  State.FULL
                dataReg   :=  io.in.bits
            }
        }
        
        is(State.FULL)
        {
            when(io.out.ready)
            {
                stateReg  :=  State.EMPTY
            }
        }
    }    
    
    io.in.ready     :=  stateReg === State.EMPTY
    io.out.valid    :=  stateReg === State.FULL
    io.out.bits     :=  dataReg    
}

class  BuffredTX(frequency : Int, baudRate : Int)  extends  Module
{
    val  io  =  IO(new  Bundle{ val  in      =  Flipped(new  serialIO())
                                val  txPin   =  Output(UInt(1.W))     })

    val  buffer  =  Module(new  OneCellBuffer)
    val  tx      =  Module(new  TX(frequency, baudRate))

    tx.io.in  <>  buffer.io.out
    io.in     <>  buffer.io.in
    io.txPin  :=  tx.io.txPin
}

class  Echo(frequency : Int, baudRate : Int)  extends  Module
{
    val  io  =  IO(new  Bundle{ val  rxPin  =  Input(UInt(1.W))
                                val  txPin  =  Output(UInt(1.W))   })

    val  befferedTransmetter  =  Module(new  BuffredTX(frequency, baudRate))
    val  receiver             =  Module(new  RX(frequency, baudRate))

    receiver.io.out  <>  befferedTransmetter.io.in

    receiver.io.rxPin  :=  io.rxPin
    io.txPin           :=  befferedTransmetter.io.txPin
}

class  EchedHelloWorld(frequency : Int, baudRate : Int)  extends  Module
{
    val  io  =  IO(new  Bundle{ val  txPin  =  Output(UInt(1.W)) })

    val  helloMessage  =  Module(new  HelloWorldSender(frequency, baudRate))
    val  echoModule    =  Module(new  Echo(frequency, baudRate))

    echoModule.io.rxPin  :=  helloMessage.io.txPin

    io.txPin  :=  echoModule.io.txPin
}

object  mainRX  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  EchedHelloWorld(50_000_000, 9600),
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}