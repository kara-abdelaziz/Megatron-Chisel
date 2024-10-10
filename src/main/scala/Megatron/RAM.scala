package  megatron

import  chisel3._
import  chisel3.util._
import  _root_.circt.stage.ChiselStage

class  RAM  extends  Module
{
    val  io  =  IO(new  Bundle{ val  in     =  Input(UInt(8.W))
                                val  addr   =  Input(UInt(16.W))
                                val  write  =  Input(Bool())
                                val  out    =  Output(UInt(8.W))  })

    val  negClock  =  (~clock.asUInt).asBool.asClock

    withClock(negClock)
    {        
        val  ram  =  Mem(65_536, UInt(8.W))
   

        when(io.write)
        {
            ram.write(io.addr, io.in)
        }
        
        io.out  :=  ram.read(io.addr)
    } 
}

object  mainRAM  extends  App
{
    ChiselStage.emitSystemVerilogFile(new  RAM,
                                      Array("--target-dir", "generated"), 
                                      firtoolOpts = Array("-disable-all-randomization"))
}