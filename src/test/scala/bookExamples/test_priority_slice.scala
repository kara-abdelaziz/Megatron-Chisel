import  chisel3._ 
import  chiseltest._
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_priority_slice  extends  AnyFlatSpec  with ChiselScalatestTester
{
    "DUT Proirity slice" should "yield the priority relation with the entries" in
    {
        test(new  PrioritySlice).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut =>
                for(i <- 0 until 16)
                {
                    dut.io.req.poke(i.U)
                    dut.clock.step(1)
                }
        }
    }
}