import  scala.util.Random
import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_input_filter  extends  AnyFlatSpec  with ChiselScalatestTester
{
    var  rnd  =  Random
    "DUT input filter" should "filter debounce and metastability entries" in
    {
        test(new  InputFilter).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut =>
                
                /*
                //  3 debouce values
                dut.io.input.poke(0.U)  
                dut.clock.step(3)
                dut.io.input.poke(1.U)
                dut.clock.step(1)
                dut.io.input.poke(0.U)
                dut.clock.step(1)
                dut.io.input.poke(1.U)
                dut.clock.step(1)
                dut.io.input.poke(0.U)
                dut.clock.step(1)
                dut.io.input.poke(1.U)
                dut.clock.step(1)
                dut.io.input.poke(0.U)
                dut.clock.step(7)

                //  1 value without majority
                
                dut.io.input.poke(1.U)
                dut.clock.step(10)
                dut.io.input.poke(0.U)
                dut.clock.step(30)

                //  2 value forcing majority
                
                dut.io.input.poke(1.U)
                dut.clock.step(20)
                dut.io.input.poke(0.U)
                dut.clock.step(30)

                //  2 value forcing majority
                
                dut.io.input.poke(1.U)
                dut.clock.step(20)
                dut.io.input.poke(0.U)
                dut.clock.step(30)
                */

                for (i <- 0 to 500)
                {
                    dut.io.input.poke(rnd.nextInt(2).U)
                    dut.clock.step(1)
                }
        }
    }
}