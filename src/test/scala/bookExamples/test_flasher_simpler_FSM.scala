import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_flasher_simpler extends  AnyFlatSpec  with  ChiselScalatestTester
{
    "DUT flasher simpler" should "tests the sequence execution of the light" in
    {
        test(new  FlasherSimpleFSM).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut => 

                dut.io.start.poke(0.U)
                dut.clock.step(1)

                dut.io.start.poke(1.U)
                dut.clock.step(1)

                dut.io.start.poke(0.U)
                dut.clock.step(50)

                dut.io.start.poke(1.U)
                dut.clock.step(1)

                dut.io.start.poke(0.U)
                dut.clock.step(50)
        }
    }
}