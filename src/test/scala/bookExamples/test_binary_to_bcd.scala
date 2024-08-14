import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_binary_to_bcd  extends  AnyFlatSpec  with  ChiselScalatestTester
{
    "DUT binary to BCD converter" should "be able to convert a binary number to BCD format" in
    {
        test(new  BinaryToBCD)
        {
            dut => 
                
                dut.io.bin.poke(0.U(8.W))
                dut.clock.step(1)
                dut.io.bcd.expect(0.U)

                dut.io.bin.poke(12.U(8.W))
                dut.clock.step(1)
                dut.io.bcd.expect(18.U)

                dut.io.bin.poke(15.U(8.W))
                dut.clock.step(1)
                dut.io.bcd.expect(21.U)


        }
    }
}