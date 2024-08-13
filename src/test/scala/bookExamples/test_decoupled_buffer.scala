import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_decoupled_buffer extends  AnyFlatSpec  with  ChiselScalatestTester
{
    "DUT decoupled buffer" should "be tested in inputs and outputs" in
    {
        test(new  DecoupledBuffer).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut => 
                // entring the value 14
                dut.io.input.bits.poke(14.U)
                dut.io.input.valid.poke(false.B)
                dut.io.output.ready.poke(false.B)
                dut.clock.step(1)

                dut.io.input.bits.poke(14.U)
                dut.io.input.valid.poke(false.B)
                dut.io.output.ready.poke(true.B)
                dut.clock.step(1)

                dut.io.input.bits.poke(14.U)
                dut.io.input.valid.poke(true.B)
                dut.io.output.ready.poke(false.B)
                dut.clock.step(1)

                dut.io.input.bits.poke(14.U)
                dut.io.input.valid.poke(false.B)
                dut.io.output.ready.poke(false.B)
                dut.clock.step(1)

                dut.io.input.bits.poke(14.U)
                dut.io.input.valid.poke(false.B)
                dut.io.output.ready.poke(true.B)
                dut.clock.step(1)

                dut.io.input.bits.poke(14.U)
                dut.io.input.valid.poke(false.B)
                dut.io.output.ready.poke(false.B)
                dut.clock.step(1)

            
                // entring the value 5
                dut.io.input.bits.poke(5.U)
                dut.io.input.valid.poke(false.B)
                dut.io.output.ready.poke(false.B)
                dut.clock.step(1)

                dut.io.input.bits.poke(5.U)
                dut.io.input.valid.poke(false.B)
                dut.io.output.ready.poke(true.B)
                dut.clock.step(1)

                dut.io.input.bits.poke(5.U)
                dut.io.input.valid.poke(true.B)
                dut.io.output.ready.poke(false.B)
                dut.clock.step(1)

                dut.io.input.bits.poke(5.U)
                dut.io.input.valid.poke(false.B)
                dut.io.output.ready.poke(false.B)
                dut.clock.step(1)

                dut.io.input.bits.poke(5.U)
                dut.io.input.valid.poke(false.B)
                dut.io.output.ready.poke(true.B)
                dut.clock.step(1)

                dut.io.input.bits.poke(5.U)
                dut.io.input.valid.poke(false.B)
                dut.io.output.ready.poke(false.B)
                dut.clock.step(1)

        }
    }
}