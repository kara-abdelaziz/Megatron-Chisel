import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_flasher extends  AnyFlatSpec  with  ChiselScalatestTester
{
    "DUT flasher" should "tests the sequence execution of the light" in
    {
        test(new  FlasherFSM).withAnnotations(Seq(WriteVcdAnnotation))
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