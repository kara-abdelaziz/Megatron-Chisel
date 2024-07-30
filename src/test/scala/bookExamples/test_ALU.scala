import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_ALU  extends  AnyFlatSpec  with  ChiselScalatestTester
{
    "DUT ALU" should "do the arithmetic and logic operation correctly" in
    {
        test(new  ALU)
        {
            dut => dut.io.fn.poke(0.U(2.W))
                   dut.io.a.poke(3.S(16.W))
                   dut.io.b.poke(-5.S(16.W))
                   dut.clock.step(1)
                   dut.io.sum.expect(-2.S(16.W))

                   dut.io.fn.poke(1.U(2.W))
                   dut.io.a.poke(3.S(16.W))
                   dut.io.b.poke(-5.S(16.W))
                   dut.clock.step(1)
                   dut.io.sum.expect(8.S(16.W))

                   dut.io.fn.poke(2.U(2.W))
                   dut.io.a.poke(3.S(16.W))
                   dut.io.b.poke(5.S(16.W))
                   dut.clock.step(1)
                   dut.io.sum.expect(7.S(16.W))

                   dut.io.fn.poke(3.U(2.W))
                   dut.io.a.poke(3.S(16.W))
                   dut.io.b.poke(5.S(16.W))
                   dut.clock.step(1)
                   dut.io.sum.expect(1.S(16.W))
        }
    }
}