import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_memory  extends  AnyFlatSpec  with ChiselScalatestTester
{
    "DUT memory" should "be able to check some cells" in
    {
        test(new  Memory).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut =>
                /*
                dut.io.writeEnable.poke(true.B)                
                for (i <- 0 until 16)
                {
                    dut.io.writeAddress.poke(i.U(4.W))
                    dut.io.writeData.poke((i<<2).U(8.W))
                    dut.clock.step(1)                    
                }*/

                dut.io.writeEnable.poke(false.B)   
                for (i <- 0 until 16)
                {
                    dut.io.readAddress.poke(i.U(4.W))
                    dut.clock.step(1)                 
                }
        }
    }
}