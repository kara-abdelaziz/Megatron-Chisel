import  chisel3._ 
import  chisel3.util._
import  _root_.circt.stage.ChiselStage

class  AnalogBlinkingLED  extends  Module
{
    val  io  =  IO(new  Bundle{  val  sig  =  Output(Bool())  })

    def  PWM(period : Int, modulation : UInt) : Bool =
    {
        val  countReg  =  RegInit(0.U(unsignedBitLength(period-1).W))

        countReg := countReg + 1.U
        
        when(countReg === period.U)
        {
            countReg  :=  0.U
        }

        countReg < modulation
    }

    val  modulationReg  =  RegInit(0.U(32.W))
    val  updownReg      =  RegInit(true.B)

    when ((modulationReg < 100.U) && updownReg)
    {
        modulationReg  :=  modulationReg + 1.U
    }
    .elsewhen (modulationReg === 100.U)
    {
        updownReg      :=  false.B
        modulationReg  :=  modulationReg - 1.U
    }
    .elsewhen (!updownReg && (modulationReg > 0.U))
    {
        modulationReg  :=  modulationReg - 1.U
    }
    .otherwise
    {
        updownReg      :=  true.B
        modulationReg  :=  modulationReg + 1.U
    }
    
    io.sig  :=  PWM(10, modulationReg>>4)
}

object  mainAnalogBlinkingLED  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  AnalogBlinkingLED,
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}