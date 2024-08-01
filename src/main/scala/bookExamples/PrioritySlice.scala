import  chisel3._ 
import  chisel3.util._ 
import  _root_.circt.stage.ChiselStage

class  PrioritySlice  extends  Module
{
    val  io  =  IO(new  Bundle{ val  req  =  Input(UInt(4.W))
                                val  grt  =  Output(UInt(4.W))})

    val  granted        =  VecInit(0.U(1.W), 0.U, 0.U, 0.U)
    val  prevNotGnted   =  VecInit(0.U(1.W), 0.U, 0.U, 0.U)

    granted(0)      := io.req(0)
    prevNotGnted(0) := ~io.req(0)

    granted(1)      := io.req(1) & prevNotGnted(0)
    prevNotGnted(1) := ~io.req(1) & prevNotGnted(0)

    granted(2)      := io.req(2) & prevNotGnted(1)
    prevNotGnted(2) := ~io.req(2) & prevNotGnted(1)

    granted(3)      := io.req(3) & prevNotGnted(2)
    prevNotGnted(3) := ~io.req(3) & prevNotGnted(2)
    
    io.grt := Cat(granted(3), granted(2), granted(1), granted(0))
}

object  mainPrioritySlice  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  PrioritySlice,
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}