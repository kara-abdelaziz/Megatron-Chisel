import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_counter_OOP extends  AnyFlatSpec  with  ChiselScalatestTester
{
    "DUT counter OOP" should "be able to detect if the counter is able to count correctly" in
    {
        test(new  CounterNerd(10)).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut => 
                
                dut.clock.step(50)
        }
    }
}