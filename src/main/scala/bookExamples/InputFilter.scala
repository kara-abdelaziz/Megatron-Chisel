import  chisel3._ 
import  _root_.circt.stage.ChiselStage

class  InputFilter  extends  Module
{
    val  io  =  IO(new  Bundle{ val  input   =  Input(UInt(1.W))
                                val  output  =  Output(UInt(4.W)) })

    val  SAMPLING  =  10.U

    val  countReg  =  RegInit(0.U(4.W))
    val  inputReg  =  RegInit(0.U(1.W))

    countReg := countReg + 1.U

    val tick = (countReg === (SAMPLING-1.U))

    when (tick)
    {
        countReg :=  0.U
        inputReg :=  io.input
    }

    val  shifterReg  =  RegInit(0.U(3.W))

    when (tick)
    {
        shifterReg := shifterReg(1,0) ## inputReg
    }    

    val  filtredInput  = (shifterReg(0) & shifterReg(1)) | (shifterReg(0) & shifterReg(2)) | (shifterReg(1) & shifterReg(2))
    
    val  reCounterReg  =   RegInit(0.U(4.W))

    when (filtredInput & ~RegNext(filtredInput))
    {
        reCounterReg := reCounterReg + 1.U
    }

    io.output := reCounterReg
}

object  mainInputFilter  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  InputFilter,
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}