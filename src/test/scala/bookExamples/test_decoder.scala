import  chisel3._
import  chiseltest._
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_decoder  extends  AnyFlatSpec  with ChiselScalatestTester
{
    "DUT: Decoder" should "one hot the decoded entry" in
    {
        test(new  Decoder)
        {
            dut =>

            dut.io.i0.poke(0.U)
            dut.io.i1.poke(0.U)
            dut.clock.step(1)
            dut.io.o0.expect(1.U)
            dut.io.o1.expect(0.U)
            dut.io.o2.expect(0.U)
            dut.io.o3.expect(0.U)

            dut.io.i0.poke(1.U)
            dut.io.i1.poke(0.U)
            dut.clock.step(1)
            dut.io.o0.expect(0.U)
            dut.io.o1.expect(1.U)
            dut.io.o2.expect(0.U)
            dut.io.o3.expect(0.U)

            dut.io.i0.poke(0.U)
            dut.io.i1.poke(1.U)
            dut.clock.step(1)
            dut.io.o0.expect(0.U)
            dut.io.o1.expect(0.U)
            dut.io.o2.expect(1.U)
            dut.io.o3.expect(0.U)

            dut.io.i0.poke(1.U)
            dut.io.i1.poke(1.U)
            dut.clock.step(1)
            dut.io.o0.expect(0.U)
            dut.io.o1.expect(0.U)
            dut.io.o2.expect(0.U)
            dut.io.o3.expect(1.U)
        }
    }
}