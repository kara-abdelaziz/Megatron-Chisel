import  chisel3._ 
import  chiseltest._
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_priority_encoder  extends  AnyFlatSpec  with ChiselScalatestTester
{
    "DUT Proirity encoder" should "yield the priority relation with the entries" in
    {
        test(new  PriorityEncoder).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut =>
                for(i <- 0 until 16)
                {
                    dut.io.reqest.poke(i.U)
                    dut.clock.step(1)
                }
        }
    }
}