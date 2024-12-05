package  megatron

import  chisel3._
import  chisel3.util._
import  _root_.circt.stage.ChiselStage

class  CU  extends  Module
{   val  io  =  IO(new  Bundle{ val  opCode      =  Input(UInt(8.W)) 
                                val  acc7        =  Input(Bool())
                                val  carry       =  Input(Bool())                                                               
                                
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
    
    val  ioc_ce_instr  =  (io.opCode(7,5) === "b110".U) & (io.opCode(1,0) === "b01".U)   // detect the IOU instruction
    
    io.dBusAccess  :=  Mux(ioc_ce_instr, MuxLookup(io.opCode(4,2), "b01".U)(Seq(0.U -> "b01".U, 1.U -> "b01".U, 2.U -> "b01".U, 3.U -> "b01".U, 4.U -> "b00".U, 5.U -> "b00".U, 6.U -> "b10".U, 7.U -> "b11".U)) , io.opCode(1,0))
    io.ramAddrSel  :=  Mux((io.opCode(7,5) === "b111".U), "b00".U, ((io.opCode(4,2) === 2.U(3.W)) | (io.opCode(4,2) === 3.U(3.W)) | (io.opCode(4,2) === 7.U(3.W))) ## ((io.opCode(4,2) === 1.U(3.W)) | (io.opCode(4,2) === 3.U(3.W)) | (io.opCode(4,2) === 7.U(3.W)))) 
    io.ramWrite    :=  (io.opCode(7,5) === "b110".U) &  (io.opCode(1,0) =/= "b01".U) 
    io.xWrite      :=  (io.opCode(4,2) === 4.U(3.W)) & ~(io.opCode(7,5) === "b111".U) & ~ioc_ce_instr
    io.xInc        :=  (io.opCode(4,2) === 7.U(3.W)) & ~(io.opCode(7,5) === "b111".U) & ~ioc_ce_instr
    io.yWrite      :=  (io.opCode(4,2) === 5.U(3.W)) & ~(io.opCode(7,5) === "b111".U) & ~ioc_ce_instr
    io.accWrite    :=  ~io.opCode(4) & ~(io.opCode(7,6) === "b11".U)
    io.iocWrite    :=  (io.opCode(7,5) === "b110".U) & (io.opCode(1,0) === "b01".U) & ~io.ioCtlEnble // Check the instruction set table
    io.inputEnble  :=  (io.opCode(1,0) === "b11".U)
    io.outputEnble :=  (io.opCode(4,3) === "b11".U) & ~(io.opCode(7,6) === "b11".U)
    io.ioCtlEnble  :=  io.opCode === "b110_101_01".U   // Check the instruction set table
    io.pcHighWrite :=  (io.opCode(7,5) === "b111".U) & (io.opCode(4,2) === "b000".U)
    io.pcLowWrite  :=  io.pcHighWrite | ((io.opCode(7,5) === "b111".U) & MuxLookup((io.carry ## io.acc7), 0.U)(Seq(0.U -> io.opCode(2), 1.U -> io.opCode(3), 2.U -> io.opCode(4), 3.U -> 0.U)))
    io.aluFuct     :=  Mux(ioc_ce_instr, "b000".U, io.opCode(7,5))
}

object  mainControlUnit extends  App
{
    ChiselStage.emitSystemVerilogFile(new  CU,
                                      Array("--target-dir", "generated"), 
                                      firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}