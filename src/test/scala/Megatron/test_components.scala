import  megatron._
import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_components  extends  AnyFlatSpec  with  ChiselScalatestTester
{
    "DUT register " should "write at the falling edge of the clock" in
    {
        test(new  Register8bit).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut => 
                dut.io.in.poke(10.U(8.W))
                dut.io.write.poke(false.B)
                dut.clock.step(5)

                dut.io.write.poke(true.B)
                dut.clock.step(2)
                dut.io.write.poke(false.B)

                dut.io.in.poke(20.U(8.W))
                dut.io.write.poke(false.B)
                dut.clock.step(5)

                dut.io.write.poke(true.B)
                dut.clock.step(2)
                dut.io.write.poke(false.B)
                dut.clock.step(2)
        }
    }

    "DUT counter 8 bits " should "write and count at the falling edge of the clock" in
    {
        test(new  counter8bit).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut => 
                dut.io.in.poke(10.U(8.W))
                dut.io.write.poke(false.B)
                dut.io.inc.poke(false.B)
                dut.clock.step(5)

                dut.io.inc.poke(true.B)
                dut.clock.step(5)
                dut.io.inc.poke(false.B)

                dut.io.write.poke(true.B)
                dut.clock.step(5)
                dut.io.inc.poke(true.B)
                dut.clock.step(5)

                dut.io.write.poke(false.B)
                dut.clock.step(5)

                dut.io.inc.poke(false.B)
                dut.clock.step(5)
        }
    }

    "DUT shifter 8 bits " should "right shift at the falling edge of the clock" in
    {
        test(new  Shifter8bit).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut => 
                dut.io.in.poke(0.U)
                dut.io.pallelClock.poke(0.U)
                dut.clock.step(1)

                dut.io.in.poke(1.U)
                dut.io.pallelClock.poke(0.U)
                dut.clock.step(1)

                dut.io.in.poke(0.U)
                dut.io.pallelClock.poke(0.U)
                dut.clock.step(1)

                dut.io.in.poke(1.U)
                dut.io.pallelClock.poke(0.U)
                dut.clock.step(1)

                dut.io.in.poke(1.U)
                dut.io.pallelClock.poke(0.U)
                dut.clock.step(5)

                dut.io.in.poke(1.U)
                dut.io.pallelClock.poke(1.U)
                dut.clock.step(1)

                dut.io.in.poke(1.U)
                dut.io.pallelClock.poke(0.U)
                dut.clock.step(5)
        }
    }
}