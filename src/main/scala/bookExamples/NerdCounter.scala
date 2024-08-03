import  chisel3._ 
import  _root_.circt.stage.ChiselStage

class  NerdCounter(N : Int)  extends  Module
{
    val  io  =  IO(new  Bundle{ val  tick  =  Output(Bool()) })

    
    val  MAX       =  (N-2).S(8.W)
    val  countReg  =  RegInit(MAX)

    io.tick  :=  false.B
    
    countReg  :=  countReg - 1.S
    
    when (countReg(7) === 1.U)
    {
        countReg  :=  MAX
        io.tick   :=  true.B
    }
}

object  mainNerdCounter  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  NerdCounter(64),
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}