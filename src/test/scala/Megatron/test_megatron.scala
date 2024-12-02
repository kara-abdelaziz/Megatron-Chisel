import  megatron._
import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_megatron  extends  AnyFlatSpec  with  ChiselScalatestTester
{
    "DUT megatron" should "be able to perform a Fibonacci sequence." in
    {
        test(new  Megatron).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut => 

                // putting the the value 3 in the gamepad register

                dut.io.GamepadIn.poke(false.B)
                dut.clock.step(2)
                dut.io.GamepadIn.poke(true.B)
                dut.clock.step(1)
                dut.io.GamepadIn.poke(true.B)
                dut.clock.step(1)
                dut.io.GamepadIn.poke(false.B)                
                dut.clock.step(1)

                
                dut.clock.step(100)

        }
    }
} 