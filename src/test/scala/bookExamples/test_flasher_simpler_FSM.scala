import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_flasher_simpler extends  AnyFlatSpec  with  ChiselScalatestTester
{
    "DUT flasher simpler" should "tests the sequence execution of the light" in
    {
        test(new  SimpleCounter).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut => 

                dut.io.load.poke(0.U)
                dut.clock.step(1)

                dut.io.load.poke(1.U)
                dut.clock.step(2)

                dut.io.load.poke(0.U)
                dut.clock.step(5)

                dut.io.load.poke(1.U)
                dut.clock.step(1)

                dut.io.load.poke(0.U)
                dut.clock.step(5)
        }
    }
}