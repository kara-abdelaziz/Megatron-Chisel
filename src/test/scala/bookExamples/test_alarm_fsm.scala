import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_alarm_fsm  extends  AnyFlatSpec  with ChiselScalatestTester
{
    "DUT alarm FSL" should "hop from state to the next" in
    {
        test(new  AlarmFSM).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut =>
                // in GREEN state
                dut.io.badEvent.poke(0.U)  
                dut.io.clear.poke(0.U)
                dut.clock.step(1)

                // stay in GREEN state
                dut.io.badEvent.poke(0.U)  
                dut.io.clear.poke(0.U)
                dut.clock.step(1)

                // goto ORANGE state                
                dut.io.badEvent.poke(1.U)  
                dut.io.clear.poke(0.U)
                dut.clock.step(1)

                // stay in ORANGE state
                dut.io.badEvent.poke(0.U)  
                dut.io.clear.poke(0.U)
                dut.clock.step(1)

                // goto GREEN state                
                dut.io.badEvent.poke(0.U)  
                dut.io.clear.poke(1.U)
                dut.clock.step(1)

                // goto ORANGE state                
                dut.io.badEvent.poke(1.U)  
                dut.io.clear.poke(0.U)
                dut.clock.step(1)

                // goto RED state                
                dut.io.badEvent.poke(1.U)  
                dut.io.clear.poke(0.U)
                dut.clock.step(1)

                // stay RED state                
                dut.io.badEvent.poke(0.U)  
                dut.io.clear.poke(0.U)
                dut.clock.step(1)

                // stay RED state                
                dut.io.badEvent.poke(1.U)  
                dut.io.clear.poke(0.U)
                dut.clock.step(1)

                // goto GREEN state                
                dut.io.badEvent.poke(0.U)  
                dut.io.clear.poke(1.U)
                dut.clock.step(1)
        }
    }
}