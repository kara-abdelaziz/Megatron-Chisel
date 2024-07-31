import  chisel3._
import  chiseltest._
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_encoder  extends  AnyFlatSpec  with  ChiselScalatestTester
{
    "DUT encoder : " should "encode the one-hot entry" in
    {
        test(new  Encoder)
        {
            dut =>
                dut.io.input.poke("b0001".U)
                dut.clock.step(1)
                dut.io.output.expect(0.U)

                dut.io.input.poke("b0010".U)
                dut.clock.step(1)
                dut.io.output.expect(1.U)

                dut.io.input.poke("b0100".U)
                dut.clock.step(1)
                dut.io.output.expect(2.U)

                dut.io.input.poke("b1000".U)
                dut.clock.step(1)
                dut.io.output.expect(3.U)

                dut.io.input.poke("b0000".U)
                dut.clock.step(1)
                dut.io.output.expect(0.U)
        }
    }
}