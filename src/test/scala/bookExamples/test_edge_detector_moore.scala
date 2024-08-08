import  scala.util.Random
import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_edge_detector_moore  extends  AnyFlatSpec  with  ChiselScalatestTester
{
    "DUT edge detector with moore" should "has the ability to detect rising edge" in
    {
        var  rnd  =  Random
        
        test(new  EdgeDetectorMoore).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut => 

                for (i <- 0 to 50)
                {
                    dut.io.in.poke(rnd.nextInt(2).U)
                    dut.clock.step(1)
                }
        }
    }
}