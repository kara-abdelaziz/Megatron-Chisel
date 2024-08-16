import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_file_reader extends  AnyFlatSpec  with  ChiselScalatestTester
{
    "DUT file reader" should "be able to match the value read from the file data.txt" in
    {
        test(new  FileReader)
        {
            dut => 
                
                dut.io.input.poke(0.U(3.W))
                dut.clock.step(1)
                dut.io.output.expect(12.U)

                dut.io.input.poke(4.U(3.W))
                dut.clock.step(1)
                dut.io.output.expect(45.U)

                dut.io.input.poke(7.U(3.W))
                dut.clock.step(1)
                dut.io.output.expect(37.U)
        }
    }
}