import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class  Test_led  extends  AnyFlatSpec  with  ChiselScalatestTester
{
    "LED blinking" should "blinking a fifth of a second" in
    {
        test(new  Hello_world_chisel).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut =>  dut.clock.step(100)
        }
    }

}




