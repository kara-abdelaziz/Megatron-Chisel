import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class  DUT  extends  AnyFlatSpec  with  ChiselScalatestTester
{
    "DUT" should "calculate the addition and test the equality" in
    {
        test(new Testing_example).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut =>  dut.io.a.poke(2.U)
                    dut.io.b.poke(5.U)
                    dut.clock.step(1)
                    dut.io.sum.expect(7.U)
                    dut.io.equ.expect(false.B) 

                    dut.io.a.poke(7.U)
                    dut.io.b.poke(7.U)
                    dut.clock.step(1)
                    dut.io.sum.expect(14.U)
                    dut.io.equ.expect(true.B)

                    assert(dut.io.equ.peekBoolean() == true)

                    dut.io.a.poke(1.U)
                    dut.io.b.poke(2.U)
                    dut.clock.step(1)

                    dut.io.a.poke(3.U)
                    dut.io.b.poke(3.U)
                    dut.clock.step(1)
                    
                    for (x <- 0 to 15 )
                        for (y <- 0 to 15)
                        {
                            dut.io.a.poke(x.U)
                            dut.io.b.poke(y.U)
                            dut.clock.step(1)
                            //println(x , y)
                            dut.io.sum.expect(((y+x)%16).U)
                        }
        }
    }
}