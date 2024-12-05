package  megatron

import  chisel3._
import  chisel3.util._
import  _root_.circt.stage.ChiselStage

class  Megatron  extends  Module
{   val  io  =  IO(new  Bundle{ val  GamepadIn   =  Input(Bool()) 
                                val  KeyboardIn  =  Input(Bool())                                
                                
                                val  output1     =  Output(UInt(8.W))  })

    val  datapath     =  Module(new  DataPath)
    val  controlUnit  =  Module(new  CU)

    //  connecting the datapath to the CU

    controlUnit.io.opCode  :=  datapath.io.opCode
    controlUnit.io.acc7    :=  datapath.io.acc7
    controlUnit.io.carry   :=  datapath.io.carry

    //  connecting the CU to the datapath

    datapath.io.dBusAccess   :=  controlUnit.io.dBusAccess
    datapath.io.ramAddrSel   :=  controlUnit.io.ramAddrSel
    datapath.io.ramWrite     :=  controlUnit.io.ramWrite
    datapath.io.xWrite       :=  controlUnit.io.xWrite
    datapath.io.xInc         :=  controlUnit.io.xInc
    datapath.io.yWrite       :=  controlUnit.io.yWrite
    datapath.io.accWrite     :=  controlUnit.io.accWrite
    datapath.io.iocWrite     :=  controlUnit.io.iocWrite
    datapath.io.inputEnble   :=  controlUnit.io.inputEnble
    datapath.io.outputEnble  :=  controlUnit.io.outputEnble
    datapath.io.ioCtlEnble   :=  controlUnit.io.ioCtlEnble
    datapath.io.pcHighWrite  :=  controlUnit.io.pcHighWrite
    datapath.io.pcLowWrite   :=  controlUnit.io.pcLowWrite
    datapath.io.aluFuct      :=  controlUnit.io.aluFuct
    
    //  connecting the datapath to megatron inputs/outputs

    datapath.io.GamepadIn   :=  io.GamepadIn
    datapath.io.KeyboardIn  :=  io.KeyboardIn

    io.output1  :=  datapath.io.output1
}

object  mainMegatron extends  App
{
    ChiselStage.emitSystemVerilogFile(new  Megatron,
                                      Array("--target-dir", "generated"), 
                                      firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}