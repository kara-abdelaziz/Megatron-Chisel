package  megatron

import  chisel3._
import  chisel3.util._
import  _root_.circt.stage.ChiselStage

class  Register8bit  extends  Module
{
    val  io  =  IO(new  Bundle{ val  in     =  Input(UInt(8.W))
                                val  write  =  Input(Bool())
                                val  out    =  Output(UInt(8.W))  })

    val  negClock  =  (~clock.asUInt).asBool.asClock

    val  negReg  =  withClock(negClock)
                    {        
                        RegInit(0.U)
                    }

    when(io.write)
    {
        negReg  :=  io.in
    }
    
    io.out  :=  negReg
}

class  counter8bit  extends  Module
{
    val  io  =  IO(new  Bundle{ val  in     =  Input(UInt(8.W))
                                val  write  =  Input(Bool())
                                val  inc    =  Input(Bool())
                                val  out    =  Output(UInt(8.W)) })
    val  negClock  =  (~clock.asUInt).asBool.asClock

    val  negReg  =  withClock(negClock)
                    {        
                        RegInit(0.U)
                    }

    when(io.write)
    {
        negReg  :=  io.in
    }
    .elsewhen(io.inc)
    {
        negReg  :=  negReg + 1.U
    }
      
    io.out  :=  negReg    
}

class  counter16bit  extends  Module
{
    val  io  =  IO(new  Bundle{ val  lowerIn     =  Input(UInt(8.W))
                                val  upperIn     =  Input(UInt(8.W))
                                val  lowerWrite  =  Input(Bool())
                                val  upperWrite  =  Input(Bool())
                                val  out         =  Output(UInt(16.W)) })

    val  lowCounterReg   =  RegInit(0.U(8.W))
    val  highCounterReg  =  RegInit(0.U(8.W))

    val  maxLowCounter   =  lowCounterReg.andR

    when(io.lowerWrite)
    {
        lowCounterReg  :=  io.lowerIn
    }
    .otherwise
    {
        lowCounterReg  :=  lowCounterReg + 1.U
    }

    when(io.upperWrite)
    {
        highCounterReg  :=  io.upperIn
    }
    .elsewhen(maxLowCounter)
    {
        highCounterReg  :=  highCounterReg + 1.U
    }   
      
    io.out  :=  highCounterReg ## lowCounterReg  
}

class  Shifter8bit  extends  Module
{
    val  io  =  IO(new  Bundle{ val  in           =  Input(UInt(1.W))
                                val  pallelClock  =  Input(Bool())
                                val  out          =  Output(UInt(8.W))  })

    val  outputReg  =  RegInit(0.U(8.W))

    val  negClock   =  (~clock.asUInt).asBool.asClock

    val  shiftReg   =   withClock(negClock)
                        {        
                            RegInit(0.U(8.W))
                        }
    
    shiftReg  :=  shiftReg(6,0) ## io.in

    when(io.pallelClock)
    {
        outputReg  :=  shiftReg
    }
    
    io.out  :=  outputReg
}

object  mainShifter  extends  App
{
    ChiselStage.emitSystemVerilogFile(new  Shifter8bit,
                                      Array("--target-dir", "generated"), 
                                      firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}