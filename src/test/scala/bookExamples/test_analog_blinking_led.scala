import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_analog_blinking_led  extends  AnyFlatSpec  with ChiselScalatestTester
{
    "DUT analog blinking led" should "dim and bright continuously" in
    {
        test(new  AnalogBlinkingLED).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut =>
                dut.clock.step(400)
        }
    }
}