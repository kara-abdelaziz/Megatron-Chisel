import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_FSM_Datapath extends  AnyFlatSpec  with  ChiselScalatestTester
{
    "DUT FSM datapath" should "tests the FSM controling the datapath" in
    {
        test(new  FSMDatapath).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut => 

                dut.io.prevValid.poke(false.B)
                dut.io.nextReady.poke(false.B)
                dut.io.inData.poke(20.U)
                dut.clock.step(1)


                dut.io.prevValid.poke(false.B)
                dut.io.nextReady.poke(true.B)
                dut.io.inData.poke(20.U)
                dut.clock.step(1)

                dut.io.prevValid.poke(true.B)
                dut.io.nextReady.poke(false.B)
                dut.io.inData.poke(20.U)
                dut.clock.step(1)


                dut.io.prevValid.poke(false.B)
                dut.io.nextReady.poke(false.B)
                dut.io.inData.poke(20.U)
                dut.clock.step(10)

                dut.io.prevValid.poke(false.B)
                dut.io.nextReady.poke(true.B)
                dut.io.inData.poke(11.U)
                dut.clock.step(1)

                dut.io.prevValid.poke(false.B)
                dut.io.nextReady.poke(false.B)
                dut.io.inData.poke(11.U)
                dut.clock.step(1)
                
                
                dut.io.prevValid.poke(true.B)
                dut.io.nextReady.poke(false.B)
                dut.io.inData.poke(11.U)
                dut.clock.step(1)

                dut.io.prevValid.poke(false.B)
                dut.io.nextReady.poke(false.B)
                dut.io.inData.poke(11.U)
                dut.clock.step(10)

                dut.io.prevValid.poke(false.B)
                dut.io.nextReady.poke(true.B)
                dut.io.inData.poke(11.U)
                dut.clock.step(1)

                dut.io.prevValid.poke(false.B)
                dut.io.nextReady.poke(false.B)
                dut.io.inData.poke(11.U)
                dut.clock.step(1)

        }
    }
}