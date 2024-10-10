import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_cast  extends  AnyFlatSpec  with ChiselScalatestTester
{
    "DUT cast" should "to see how casting work" in
    {
        test(new  Cast).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut =>

                dut.io.in.poke("h_ff".U)
                dut.clock.step(1)
                dut.io.out.expect(-1.S)
                println (" Result is: " + dut.io.out.peekInt())
        }
    }
}