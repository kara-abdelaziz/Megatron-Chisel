import  chisel3._
import  _root_.circt.stage.ChiselStage

class  PayLoad  extends  Bundle
{
    val  data  =  UInt(16.W)
    val  flag  =  Bool()
}

class  Port[T <: Data](dt : T)  extends  Bundle
{
    val  addr  =  UInt(8.W)
    val  data  =  dt
}

class  NOCRouter[T <: Data](data : T, n : Int)  extends  Module
{
    val  io  =  IO(new  Bundle{ val  input  =  Input(Vec(n, data))
                                val  addre  =  Input(Vec(n, data))
                                val  output =  Output(Vec(n, data))
        
    })
}

object  mainNOCRouter
{
    val  router  =  Module(new NOCRouter(new Port(new PayLoad), 4))
   
    // ChiselStage.emitSystemVerilogFile(  router,
    //                                     Array("--target-dir", "generated"),
    //                                     firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}