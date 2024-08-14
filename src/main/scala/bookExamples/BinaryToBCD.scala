import  chisel3._
import  _root_.circt.stage.ChiselStage

class  BinaryToBCD  extends  Module
{
    val  io  =  IO(new  Bundle{ val  bin  =  Input(UInt(8.W))
                                val  bcd  =  Output(UInt(8.W)) })

    val  table  =  Wire(Vec(100, UInt(8.W)))

    for (i <- 0 until 100)
    {
        table(i)  :=  ((i / 10 << 4) | (i % 10)).U
    }

    io.bcd  :=  table(io.bin)
}

object  mainBinaryToBCD extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  BinaryToBCD,
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}