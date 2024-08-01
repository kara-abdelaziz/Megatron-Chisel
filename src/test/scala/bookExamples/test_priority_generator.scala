import  chisel3._ 
import  chiseltest._
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_priority_generator  extends  AnyFlatSpec  with ChiselScalatestTester
{
    "DUT Proirity generator" should "yield the priority relation with the entries" in
    {
        test(new  PriorityGenerator(3)).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut =>
                for(i <- 0 until 16)
                {
                    dut.io.req.poke(i.U(2,0))
                    dut.clock.step(1)
                }
        }
    }
}