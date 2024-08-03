import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_timer  extends  AnyFlatSpec  with ChiselScalatestTester
{
    "DUT timer" should "decrement until 0 reached" in
    {
        test(new  Timer).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut =>
                dut.io.din.poke(10.U)
                dut.io.load.poke(false.B)
                dut.clock.step(2)
                dut.io.load.poke(true.B)
                dut.clock.step(2)
                dut.io.load.poke(false.B)
                dut.clock.step(5)
                dut.io.din.poke(5.U)
                dut.clock.step(10)
                dut.io.load.poke(true.B)
                dut.clock.step(2)
                dut.io.load.poke(false.B)
                dut.clock.step(10)
        }
    }
}