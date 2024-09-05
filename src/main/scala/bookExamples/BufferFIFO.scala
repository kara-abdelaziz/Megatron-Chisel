import  chisel3._
import  chisel3.util._
import  _root_.circt.stage.ChiselStage

class  FifoIO[T <: Data](data : T)  extends  Bundle
{
    val  in   =  Flipped(new  DecoupledIO(data))
    val  out  =  new  DecoupledIO(data)
}

abstract  class  Buffer[T <: Data](data : T, depth : Int)  extends  Module
{
    val  io  =  IO(new  FifoIO(data))
}



