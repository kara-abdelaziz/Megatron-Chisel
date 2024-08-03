import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_events_counter  extends  AnyFlatSpec  with ChiselScalatestTester
{
    "DUT events couter" should "counts the events only" in
    {
        test(new  EventsCounter).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut =>
                dut.io.evt.poke(false.B)
                dut.clock.step(2)
                dut.io.evt.poke(true.B)
                dut.clock.step(1)
                dut.io.evt.poke(false.B)
                dut.clock.step(2)
                dut.io.evt.poke(true.B)
                dut.clock.step(3)
        }
    }
}