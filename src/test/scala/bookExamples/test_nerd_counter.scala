import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_nerd_counter  extends  AnyFlatSpec  with ChiselScalatestTester
{
    "DUT nerd couter" should "counts the events only" in
    {
        test(new  NerdCounter(10)).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut =>
                dut.clock.step(20)
        }
    }
}