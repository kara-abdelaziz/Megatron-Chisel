import  chisel3._
import  scala.io.Source
import  _root_.circt.stage.ChiselStage

class  FileReader  extends  Module
{
    val  io  =  IO(new  Bundle{ val  input   =  Input(UInt(3.W))
                                val  output  =  Output(UInt(8.W)) })

    val  array  =  new  Array[Int](8)
    var  index  =  0

    val  source  =  Source.fromResource("data.txt")

    for(line <- source.getLines())
    {
        array(index)  =  line.toInt
        index        +=  1
    }

    val  table  =  VecInit(array.toIndexedSeq.map(_.U(8.W)))

    io.output  :=  table(io.input)
}

object  mainFileReader extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  FileReader,
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}