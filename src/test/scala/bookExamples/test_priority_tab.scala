import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_priority_tab  extends  AnyFlatSpec  with  ChiselScalatestTester
{
    "DUT Priority Table : " should  "gives the right arbitration for bottom-up" in
    {
        test(new  PriorityTab).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut =>

                for (i <- 0 until 15)
                {
                    dut.io.req.poke(i.U)
                    dut.clock.step(1)
                }
        }
    }
}