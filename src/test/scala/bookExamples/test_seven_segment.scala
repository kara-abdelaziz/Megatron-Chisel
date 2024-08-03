import  chisel3._ 
import  chiseltest._
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_seven_segment  extends  AnyFlatSpec  with  ChiselScalatestTester
{
    "DUT seven segment" should "produces the right combinational outputs" in
    {
        test(new  SevenSegment).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut =>
                for (i <- 0 until 16)
                {
                    dut.io.digit.poke(i.U)
                    dut.clock.step(1)
                }
        }
    }
}