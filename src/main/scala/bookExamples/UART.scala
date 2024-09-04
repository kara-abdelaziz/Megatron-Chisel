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
                        
            when(io.rxPin === 0.U)
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
                    shiftReg   :=  io.rxPin ## shiftReg(7,1)
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
    
    io.in.ready     :=  stateReg === State.FULL
    io.out.valid    :=  stateReg === State.EMPTY
    io.out.bits     :=  dataReg    
}

class  UARTTransmitterTX(frequency : Int, baudRate : Int)  extends  Module
{
    val  io  =  IO(new  Bundle{ val  in      =  Flipped(new  serialIO())
                                val  txPin   =  Output(UInt(1.W))     })

    val  buffer  =  Module(new  OneCellBuffer)
    val  tx      =  Module(new  TX(frequency, baudRate))

    tx.io.in  <>  buffer.io.out
    io.in     <>  buffer.io.in
    io.txPin  :=  tx.io.txPin
}


object  mainRX  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  RX(50_000_000, 9600),
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}