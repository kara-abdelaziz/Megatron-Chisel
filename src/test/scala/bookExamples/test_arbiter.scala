import  scala.util._
import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_arbiter extends  AnyFlatSpec  with  ChiselScalatestTester
{
    "DUT n input decoupled arbiter" should "be able to manage the arbitration of many inputs" in
    {
        val  rand  =  new Random

        test(new  Arbiter(UInt(8.W), 4)).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut => 
            
                dut.io.output.ready.poke(false.B)
                
                dut.io.inputs(0).bits.poke(10)
                dut.io.inputs(0).valid.poke(true.B)
                //dut.io.inputs(0).ready.

                dut.io.inputs(1).bits.poke(20)
                dut.io.inputs(1).valid.poke(false.B)
                //dut.io.inputs(0).ready.

                dut.io.inputs(2).bits.poke(30)
                dut.io.inputs(2).valid.poke(true.B)
                //dut.io.inputs(0).ready.

                dut.io.inputs(3).bits.poke(40)
                dut.io.inputs(3).valid.poke(false.B)
                //dut.io.inputs(0).ready.

                dut.clock.step(2)

                dut.io.inputs(0).valid.poke(false.B)
                dut.io.inputs(2).valid.poke(false.B)
                dut.clock.step(5)

                dut.io.output.ready.poke(true.B)
                dut.clock.step(1)

                dut.io.output.ready.poke(false.B)
                dut.clock.step(4)

                dut.io.output.ready.poke(true.B)
                dut.clock.step(1)

                dut.io.output.ready.poke(false.B)
                dut.clock.step(4)
        }
    }
}