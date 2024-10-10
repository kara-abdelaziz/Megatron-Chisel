package  megatron

import  chisel3._
import  chisel3.util._
import  chisel3.util.experimental.loadMemoryFromFile
import  _root_.circt.stage.ChiselStage

class  ROM  extends  Module
{
    val  io  =  IO(new  Bundle{ val  addr  =  Input(UInt(16.W))
                                val  ir    =  Output(UInt(8.W))
                                val  data  =  Output(UInt(8.W))  })

    val  rom  =  Mem(65_536, UInt(16.W))

    val  output  =  rom.read(io.addr)

    loadMemoryFromFile(rom, "/home/snakeas/Megatron-Chisel/src/main/resources/ROM.hex")

    io.ir    :=  output(7,0)
    io.data  :=  output(15,8)
}

object  mainROM  extends  App
{
    ChiselStage.emitSystemVerilogFile(new  ROM,
                                      Array("--target-dir", "generated"), 
                                      firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}