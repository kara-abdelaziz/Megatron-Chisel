import  megatron._
import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_components  extends  AnyFlatSpec  with  ChiselScalatestTester
{
    "DUT register " should "write at the falling edge of the clock" in
    {
        test(new  Register8bit).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut => 
                dut.io.in.poke(10.U(8.W))
                dut.io.write.poke(false.B)
                dut.clock.step(5)

                dut.io.write.poke(true.B)
                dut.clock.step(2)
                dut.io.write.poke(false.B)

                dut.io.in.poke(20.U(8.W))
                dut.io.write.poke(false.B)
                dut.clock.step(5)

                dut.io.write.poke(true.B)
                dut.clock.step(2)
                dut.io.write.poke(false.B)
                dut.clock.step(2)
        }
    }

    "DUT counter 8 bits " should "write and count at the falling edge of the clock" in
    {
        test(new  counter8bit).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut => 
                dut.io.in.poke(10.U(8.W))
                dut.io.write.poke(false.B)
                dut.io.inc.poke(false.B)
                dut.clock.step(5)

                dut.io.inc.poke(true.B)
                dut.clock.step(5)
                dut.io.inc.poke(false.B)

                dut.io.write.poke(true.B)
                dut.clock.step(5)
                dut.io.inc.poke(true.B)
                dut.clock.step(5)

                dut.io.write.poke(false.B)
                dut.clock.step(5)

                dut.io.inc.poke(false.B)
                dut.clock.step(5)
        }
    }

    "DUT counter 16 bits " should "write and count at the falling edge of the clock" in
    {
        test(new  counter16bit).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut => 
                dut.io.lowerIn.poke(0.U(8.W))
                dut.io.upperIn.poke(0.U(8.W))
                dut.io.lowerWrite.poke(false.B)
                dut.io.upperWrite.poke(false.B)
                dut.clock.step(10)

                dut.io.lowerIn.poke("b1111_1100".U(8.W))
                dut.io.upperIn.poke(0.U(8.W))
                dut.io.lowerWrite.poke(true.B)
                dut.io.upperWrite.poke(false.B)
                dut.clock.step(1)

                dut.io.lowerIn.poke("b1111_1100".U(8.W))
                dut.io.upperIn.poke(0.U(8.W))
                dut.io.lowerWrite.poke(false.B)
                dut.io.upperWrite.poke(false.B)
                dut.clock.step(10)

                dut.io.lowerIn.poke("b1111_1100".U(8.W))
                dut.io.upperIn.poke(10.U(8.W))
                dut.io.lowerWrite.poke(true.B)
                dut.io.upperWrite.poke(true.B)
                dut.clock.step(1)

                dut.io.lowerIn.poke("b1111_1100".U(8.W))
                dut.io.upperIn.poke(0.U(8.W))
                dut.io.lowerWrite.poke(false.B)
                dut.io.upperWrite.poke(false.B)
                dut.clock.step(10)

                dut.io.lowerIn.poke("b1111_1100".U(8.W))
                dut.io.upperIn.poke("b1111_1111".U(8.W))
                dut.io.lowerWrite.poke(true.B)
                dut.io.upperWrite.poke(true.B)
                dut.clock.step(1)

                dut.io.lowerIn.poke("b1111_1100".U(8.W))
                dut.io.upperIn.poke("b1111_1100".U(8.W))
                dut.io.lowerWrite.poke(false.B)
                dut.io.upperWrite.poke(false.B)
                dut.clock.step(10)
        }
    }
}