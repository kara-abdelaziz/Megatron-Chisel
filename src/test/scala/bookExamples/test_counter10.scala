import  chisel3._
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_counter10  extends  AnyFlatSpec  with  ChiselScalatestTester
{
    "Counter 10" should "count from 0 to 9" in
    {
        test(new  Counter).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut => dut.clock.step(30)
        }
    }
}