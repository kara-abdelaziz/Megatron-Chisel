import  scala.util._
import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_bubble_buffer_fifo extends  AnyFlatSpec  with  ChiselScalatestTester
{
    "DUT bubble buffer fifo" should "be able to accomplish a buffer connextion between to devices" in
    {
        test(new  BubleBufferFIFO(8, 8)).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut =>             
                               
                dut.io.in.write.poke(false.B)
                dut.io.out.ready.poke(false.B)
                dut.clock.step(1)

                //  test of writing of 15 values
                for(i <- 0 until 10)
                {
                    dut.io.in.dataIn.poke((i*5).U)
                    dut.io.in.write.poke(true.B)
                    dut.io.out.ready.poke(false.B)
                    dut.clock.step(1)

                    dut.io.in.write.poke(false.B)
                    dut.io.out.ready.poke(false.B)
                    dut.clock.step(1)
                }

                //  test of reading of 15 values
                for(i <- 0 until 15)
                {
                    dut.io.in.write.poke(false.B)
                    dut.io.out.ready.poke(true.B)
                    dut.clock.step(1)

                    dut.io.in.write.poke(false.B)
                    dut.io.out.ready.poke(false.B)
                    dut.clock.step(1)
                }

                // dut.clock.step(2)

                // //  test of reading and writing at the sametime of an empty buffer
                // dut.io.write.poke(true.B)
                // dut.io.ready.poke(true.B)
                // dut.clock.step(1)

                // dut.io.write.poke(false.B)
                // dut.io.ready.poke(false.B)
                // dut.clock.step(1)

                // dut.clock.step(2)

                // //  fill the buffer to the middle with 5 values
                // for(i <- 0 until 5)
                // {
                //     dut.io.dataIn.poke((i*3).U)
                //     dut.io.write.poke(true.B)
                //     dut.io.ready.poke(false.B)
                //     dut.clock.step(1)

                //     dut.io.write.poke(false.B)
                //     dut.io.ready.poke(false.B)
                //     dut.clock.step(1)
                // }

                // //  test of reading and writing at the sametime of a not empty buffer

                // dut.io.dataIn.poke(200.U)
                // dut.io.write.poke(true.B)
                // dut.io.ready.poke(true.B)
                // dut.clock.step(1)

                // dut.io.write.poke(false.B)
                // dut.io.ready.poke(false.B)
                // dut.clock.step(1)

        }
    }
}