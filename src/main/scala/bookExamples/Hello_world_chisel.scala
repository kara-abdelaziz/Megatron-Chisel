// hello world for chisel

import chisel3._
import _root_.circt.stage.ChiselStage

class  Hello_world_chisel  extends  Module
{
    val  io = IO(new Bundle{val led = Output(UInt(1.W)) })

    val  count_reg  = RegInit(0.U(4.W))
    val  led_output = RegInit(1.U(1.W))

    when (count_reg < 2.U) // LED turns on for the first 200 ms
    {
        led_output := 1.U
        count_reg  := count_reg + 1.U
    }
    .elsewhen (count_reg < 10.U)      // for the rest of 1 secondd, the LED turns off
    {
        led_output := 0.U
        count_reg  := count_reg + 1.U
    }
    .otherwise                                // when finishing a period of 1 second   
    {
        count_reg := 0.U
    }

    io.led := led_output
}

object  Hello_world_chisel  extends App
{
    ChiselStage.emitSystemVerilogFile(new Hello_world_chisel,
                                      Array("--target-dir", "generated"), 
                                      firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}

