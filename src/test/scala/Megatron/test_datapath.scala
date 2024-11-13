import  megatron._
import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_datapath  extends  AnyFlatSpec  with  ChiselScalatestTester
{
    "DUT datapath" should "perform diffrent test units to test the proper working of the circuit." in
    {
        test(new  DataPath).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut =>

                // Testing the ROM output
                
                // dut.io.addr.poke(1.U)
                // dut.clock.step(1)
                // dut.io.ir.expect("h67".U)
                // dut.io.data.expect("h45".U)
                // dut.io.addr.poke(0.U)
                // dut.clock.step(1)

                // Testing the addition of data with the accumulator

                // Load acc with the value -1 - ROM[0] = 0xFF_34

                dut.io.dBusAccess.poke(0.U)    // dBus mux on dd
                dut.io.ramAddrSel.poke(0.U)
                dut.io.ramWrite.poke(0.U)
                dut.io.xWrite.poke(0.U)
                dut.io.xInc.poke(0.U)
                dut.io.yWrite.poke(0.U)
                dut.io.accWrite.poke(1.U)      // write the value of FF (-1) to ACC
                dut.io.iocWrite.poke(0.U)
                dut.io.inputEnble.poke(0.U)
                dut.io.outputEnble.poke(0.U)
                dut.io.ioCtlEnble.poke(0.U)
                dut.io.pcHighWrite.poke(0.U)
                dut.io.pcLowWrite.poke(0.U)
                dut.io.aluFuct.poke(0.U)       // passing the value of dBus to resultBus

                dut.clock.step(2)

                dut.io.dBusAccess.poke(0.U)
                dut.io.ramAddrSel.poke(0.U)
                dut.io.ramWrite.poke(0.U)
                dut.io.xWrite.poke(0.U)
                dut.io.xInc.poke(0.U)
                dut.io.yWrite.poke(0.U)
                dut.io.accWrite.poke(0.U)
                dut.io.iocWrite.poke(0.U)
                dut.io.inputEnble.poke(0.U)
                dut.io.outputEnble.poke(0.U)
                dut.io.ioCtlEnble.poke(0.U)
                dut.io.pcHighWrite.poke(0.U)
                dut.io.pcLowWrite.poke(0.U)
                dut.io.aluFuct.poke(4.U)
                
        }
    }
}   