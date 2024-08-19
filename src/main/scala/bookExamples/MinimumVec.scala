import  chisel3._
import  _root_.circt.stage.ChiselStage

object  IndexedMin  extends  Bundle
{
    val  input  =  UInt(8.W)
    val  index  =  UInt(8.W)
}

class  MinimumVector(n : Int)  extends  Module
{
    val  io  =  IO(new  Bundle{ val  inputs    =  Input(Vec(n, UInt(8.W)))
                                val  minValue  =  Output(IndexedMin)    })

    
    val  vec  =  Wire(Vec(n, IndexedMin))

    for(i <- 0 until n)
    {
        vec(i).input  :=  io.inputs(i)
        vec(i).index  :=  i.U
    }

    vec.reduceTree((x, y)  =>  Mux(x.input < y.input, ))
    
    io.minValue  :=  IndexedMin
}