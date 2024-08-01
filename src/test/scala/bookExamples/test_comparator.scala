import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_Comparator  extends  AnyFlatSpec  with  ChiselScalatestTester
{
    "DUT Comparator" should "do comparaison between to signed integer" in
    {
        test(new  Comparator)
        {
            dut => dut.io.a.poke(3.S(4.W))
                   dut.io.b.poke(-5.S(4.W))
                   dut.clock.step(1)
                   dut.io.equ.expect(false.B)
                   dut.io.gt.expect(true.B)

                   dut.io.a.poke(-3.S(4.W))
                   dut.io.b.poke(-5.S(4.W))
                   dut.clock.step(1)
                   dut.io.equ.expect(false.B)
                   dut.io.gt.expect(true.B)

                   dut.io.a.poke(-3.S(4.W))
                   dut.io.b.poke(5.S(4.W))
                   dut.clock.step(1)
                   dut.io.equ.expect(false.B)
                   dut.io.gt.expect(false.B)
                //    println("the values eq is :" + dut.io.equ.peekBoolean())
                   dut.io.a.poke(-2.S(4.W))
                   dut.io.b.poke(-2.S(4.W))
                   dut.clock.step(1)
                   dut.io.equ.expect(true.B)
                   dut.io.gt.expect(false.B)

        }
    }
}