import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_PISO  extends  AnyFlatSpec  with  ChiselScalatestTester
{
    "DUT PISO" should "converts the paralled input to serial" in
    {
        test(new  PISO).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut =>
                dut.io.parIn.poke(9.U(4.W))
                dut.io.parEn.poke(0.U(1.W))
                dut.clock.step(1)

                dut.io.parIn.poke(9.U(4.W))
                dut.io.parEn.poke(1.U(1.W))
                dut.clock.step(2)

                dut.io.parEn.poke(0.U(1.W))
                dut.clock.step(5)

                dut.io.parIn.poke(13.U(4.W))
                dut.io.parEn.poke(1.U(1.W))
                dut.clock.step(1)

                dut.io.parEn.poke(0.U(1.W))
                dut.clock.step(5)
                
        }
    }
}