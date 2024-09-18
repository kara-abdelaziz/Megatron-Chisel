import  chisel3._
import  chisel3.util._
import  _root_.circt.stage.ChiselStage

class  memoryIOport(size : Int, width : Int)  extends  Bundle
{
    val  clock    = Input(Bool())
    val  enable   = Input(Bool())
    val  writeEn  = Input(Bool())
    val  address  = Input(UInt(log2Ceil(size).W))
    val  dataIn   = Input(UInt(width.W))
    val  dataOut  = Output(UInt(width.W))
}

class  MultiClockMemory(ports : Int, size : Int, width : Int)  extends  Module
{
    val  io  = IO(new  Bundle{ val portsVec  =  Vec(ports, new memoryIOport(size, width))  })

    val  mem  = SyncReadMem(size, UInt(width.W))
    
    for(i <- 0 until ports)
    {
        val  port  = io.portsVec(i)

        withClock(port.clock.asClock)
        {
            port.dataOut  := 0.U

            when(port.enable)
            {
                port.dataOut  := mem(port.address)

                when(port.writeEn)
                {
                    mem(port.address)  := port.dataIn
                }
            }
        }
    }
}

object  mainMultiClockMemory  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  MultiClockMemory(2, 8, 8),
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}