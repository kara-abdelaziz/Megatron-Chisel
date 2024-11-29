package  megatron

import  chisel3._
import  chisel3.util._
import  _root_.circt.stage.ChiselStage

class  Megatron  extends  Module
{   val  io  =  IO(new  Bundle{ val  opCode      =  Input(UInt(8.W)) 
                                val  acc7        =  Input(Bool())
                                val  a_eq_b      =  Input(Bool())                                                               
                                
                                val  dBusAccess  =  Output(UInt(2.W))
                                val  ramAddrSel  =  Output(UInt(2.W))
                                val  ramWrite    =  Output(Bool())
                                val  xWrite      =  Output(Bool()) 
                                val  xInc        =  Output(Bool()) 
                                val  yWrite      =  Output(Bool())
                                val  accWrite    =  Output(Bool())
                                val  iocWrite    =  Output(Bool())
                                val  inputEnble  =  Output(Bool())
                                val  outputEnble =  Output(Bool())
                                val  ioCtlEnble  =  Output(Bool())
                                val  pcHighWrite =  Output(Bool()) 
                                val  pcLowWrite  =  Output(Bool()) 
                                val  aluFuct     =  Output(UInt(3.W)) })  

}

object  mainMegatron extends  App
{
    ChiselStage.emitSystemVerilogFile(new  Megatron,
                                      Array("--target-dir", "generated"), 
                                      firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}