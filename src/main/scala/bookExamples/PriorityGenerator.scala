import  chisel3._ 
import  chisel3.util._ 
import  _root_.circt.stage.ChiselStage

class  PriorityGenerator (n : Int)  extends  Module
{
    val  io  =  IO(new  Bundle{ val  req  =  Input(UInt(n.W))
                                val  grt  =  Output(UInt(n.W))})

    val  granted        =  VecInit.fill(n)(0.U(1.W))
    val  prevNotGnted   =  VecInit.fill(n)(0.U(1.W))

    granted(0)      := io.req(0)
    prevNotGnted(0) := ~io.req(0)

    for (i <- 1 until n)
    {
        granted(i)      := io.req(i) & prevNotGnted(i-1)
        prevNotGnted(i) := ~io.req(i) & prevNotGnted(i-1)
    }

    io.grt  := granted.asUInt  
}

object  mainPriorityGenerator  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  PriorityGenerator(4),
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}