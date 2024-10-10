import  megatron._
import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_datapath  extends  AnyFlatSpec  with  ChiselScalatestTester
{
    "DUT datapath" should "perform diffrent test units to test the proper working of the circuit." in
    {
        test(new  ROM).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut =>

                dut.io.addr.poke(1.U)
                dut.clock.step(1)
                dut.io.data.expect(34.U)

        }
    }
}   