
import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_uart  extends  AnyFlatSpec  with  ChiselScalatestTester
{
    "DUT uart" should "test the serial connexion" in
    {
        test(new  RX(10, 2)).withAnnotations(Seq(WriteVcdAnnotation))
        {
            /* test of tx
            dut =>             
                               
                dut.io.in.valid.poke(false.B)
                dut.io.in.bits.poke(0.U)
                dut.clock.step(1)

                while(!dut.io.in.ready.peekBoolean())
                {
                    dut.io.in.valid.poke(true.B)
                    dut.io.in.bits.poke("h00".U)
                    dut.clock.step(1)
                }

                dut.io.in.valid.poke(true.B)
                dut.io.in.bits.poke("h55".U)
                dut.clock.step(1)

                while(!dut.io.in.ready.peekBoolean())
                {
                    dut.io.in.valid.poke(false.B)
                    dut.io.in.bits.poke(0.U)
                    dut.clock.step(1)
                }

                dut.clock.step(5)
            */

            //  test of rx
            dut =>

                dut.io.out.ready.poke(false.B)
                dut.io.rxPin.poke(1.U)
                dut.clock.step(6)

                for(i <- 0 until 11)
                {
                    dut.io.rxPin.poke((i%2).U)
                    dut.clock.step(5)
                }

                dut.io.rxPin.poke(1.U)
                dut.clock.step(10)

                dut.io.out.ready.poke(true.B)
                dut.clock.step(1)

                dut.io.out.ready.poke(false.B)
                dut.clock.step(10)

                for(i <- 0 until 11)
                {
                    dut.io.rxPin.poke(((i*3+5)%2).U)
                    dut.clock.step(5)
                }

                dut.clock.step(20)

        }
    }
}