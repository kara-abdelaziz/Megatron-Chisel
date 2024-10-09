package  megatron

import  chisel3._
import  chisel3.util._
import  _root_.circt.stage.ChiselStage

class  ALU  extends  Module
{
    val  io  =  IO(new  Bundle{ val  a      =  Input(SInt(8.W))
                                val  b      =  Input(SInt(8.W))
                                val  func   =  Input(UInt(3.W))                                                                
                                val  sum    =  Output(SInt(8.W))
                                val  equal  =  Output(Bool()) })
    
    io.sum  :=  0.S

    switch(io.func)
    {
        is(0.U(3.W))
        {
            io.sum  :=  io.b
        }

        is(1.U(3.W))
        {
            io.sum  :=  io.a & io.b
        }

        is(2.U(3.W))
        {
            io.sum  :=  io.a | io.b
        }

        is(3.U(3.W))
        {
            io.sum  :=  io.a ^ io.b
        }

        is(4.U(3.W))
        {
            io.sum  :=  io.a + io.b
        }

        is(5.U(3.W))
        {
            io.sum  :=  io.a - io.b
        }

        is(6.U(3.W))
        {
            io.sum  :=  io.a
        }

        is(7.U(3.W))
        {
            io.sum  :=  io.a - 1.S
        }
    }

    io.equal  :=  (io.a === io.b)
}

object  mainALU extends  App
{
    ChiselStage.emitSystemVerilogFile(new  ALU,
                                      Array("--target-dir", "generated"), 
                                      firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}