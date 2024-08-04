import  chisel3._ 
import  _root_.circt.stage.ChiselStage

class  Memory  extends  Module
{
    val  io  =  IO(new  Bundle{ val  readAddress   =  Input(UInt(4.W))
                                val  writeAddress  =  Input(UInt(4.W))
                                val  writeEnable   =  Input(Bool())
                                
                                val  writeData     =  Input(UInt(8.W))
                                val  readData      =  Output(UInt(8.W)) })

    val  mem  =  SyncReadMem(16, UInt(8.W))

    io.readData  :=  mem.read(io.readAddress)

    when (io.writeEnable)
    {
        mem.write(io.writeAddress, io.writeData)
    }

}

object  mainMemory  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  Memory,
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}