import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_SIPO  extends  AnyFlatSpec  with  ChiselScalatestTester
{
    "DUT SIPO" should "converts the serial inputs to parallel" in
    {
        test(new  SIPO).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut =>
                dut.io.serIn.poke(0.U(1.W))
                dut.clock.step(1)
                dut.io.serIn.poke(1.U(1.W))
                dut.clock.step(1)
                dut.io.serIn.poke(1.U(1.W))
                dut.clock.step(1)
                dut.io.serIn.poke(0.U(1.W))
                dut.clock.step(1)
                dut.io.serIn.poke(0.U(1.W))
                dut.clock.step(1)
                dut.io.serIn.poke(1.U(1.W))
                dut.clock.step(1)
                dut.io.serIn.poke(1.U(1.W))
                dut.clock.step(1)
                dut.io.serIn.poke(1.U(1.W))
                dut.clock.step(1)
                dut.io.serIn.poke(0.U(1.W))
                dut.clock.step(1)
                dut.io.serIn.poke(0.U(1.W))
                dut.clock.step(1)
        }
    }
}