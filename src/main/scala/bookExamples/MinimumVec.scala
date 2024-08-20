import  chisel3._
import  chisel3.util._
import  _root_.circt.stage.ChiselStage

class  IndexedMin  extends  Bundle
{
    val  input  =  UInt(8.W)
    val  index  =  UInt(8.W)
}

class  MinimumVector(n : Int)  extends  Module
{
    val  io  =  IO(new  Bundle{ val  inputs    =  Input(Vec(n, UInt(8.W)))
                                val  minValue  =  Output(new  IndexedMin)    })

    val  vec  =  Wire(Vec(n, new  IndexedMin))

    for(i <- 0 until n)
    {
        vec(i).input  :=  io.inputs(i)
        vec(i).index  :=  i.U
    }

    io.minValue :=  vec.reduceTree((x, y)  =>  Mux(x.input < y.input, x, y))
 }

class  MinimumVectorWithTuples(n : Int)  extends  Module
{
    val  io  =  IO(new  Bundle{ val  inputs    =  Input(Vec(n, UInt(8.W)))
                                val  minValue  =  Output(new  IndexedMin)    })

    val  (in, idx)  =  io.inputs.zipWithIndex.map(x => (x._1, x._2.U)).reduce((x , y) => (Mux((x._1 < y._1), x._1, y._1), Mux((x._1 < y._1), x._2, y._2)))

    val  tmp  =  Wire(new  IndexedMin)
    tmp.input    :=  in
    tmp.index    :=  idx

    io.minValue  :=  tmp
}

class  MinimumVectorWithMixVec(n : Int)  extends  Module
{
    val  io  =  IO(new  Bundle{ val  inputs    =  Input(Vec(n, UInt(8.W)))
                                val  minValue  =  Output(Vec(2, UInt(8.W)))    })

    // val  (in, idx)  =  

    val  vec  =  VecInit(io.inputs.zipWithIndex.map(x => MixedVecInit(x._1, x._2.U))).reduceTree((x , y) => x)
    
    io.minValue  :=  VecInit(io.inputs.zipWithIndex.map(x => MixedVecInit(x._1, x._2.U))).reduceTree((x , y) => x)
}

object  mainMinimumVector extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  MinimumVectorWithMixVec(4),
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}