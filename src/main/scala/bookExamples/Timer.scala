import  chisel3._ 
import  chisel3.util._ 
import  _root_.circt.stage.ChiselStage

class  Timer  extends  Module
{
    val  io  =  IO(new  Bundle{ val  load  =  Input(Bool())
                                val  din   =  Input(UInt(8.W))
                                val  done  =  Output(Bool()) })
    
    val  countReg  =  RegInit(io.din)

    when(countReg === 0.U)
    {
        io.done  :=  true.B
    }
    .otherwise
    {
        io.done  :=  false.B
    }
    
    when(io.load)
    {
        countReg  :=  io.din
    }
    .elsewhen(io.done)
    {
        countReg  :=  0.U
    }
    .otherwise
    {
        countReg  :=  countReg - 1.U
    }
}

object  mainTimer  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  Timer,
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}