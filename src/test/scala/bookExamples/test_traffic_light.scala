import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_traffic_light extends  AnyFlatSpec  with  ChiselScalatestTester
{
    "DUT traffic light" should "give priority to the first road" in
    {
        test(new  TrafficLight).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut => 

                dut.io.detect1.poke(0.U)
                dut.io.detect2.poke(0.U)
                dut.clock.step(2)

                dut.io.detect1.poke(1.U)
                dut.io.detect2.poke(0.U)
                dut.clock.step(2)

                dut.io.detect1.poke(0.U)
                dut.io.detect2.poke(1.U)
                dut.clock.step(2)

                dut.io.detect1.poke(0.U)
                dut.io.detect2.poke(0.U)
                dut.clock.step(4)

                // small detection time
                dut.io.detect1.poke(1.U)
                dut.io.detect2.poke(1.U)
                dut.clock.step(1)

                dut.io.detect1.poke(0.U)
                dut.io.detect2.poke(0.U)
                dut.clock.step(5)


        }
    }
}