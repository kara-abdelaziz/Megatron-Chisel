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
                ////////////////////////////////////////////
                //                                        //
                //    THIS BLOC IS FOR TESTING THE ROM    //
                //                                        //
                ////////////////////////////////////////////

                // Testing the ROM output 
                
                // dut.io.addr.poke(1.U)
                // dut.clock.step(1)
                // dut.io.ir.expect("h67".U)
                // dut.io.data.expect("h45".U)
                // dut.io.addr.poke(0.U)
                // dut.clock.step(1)

                // Testing the addition of data with the accumulator
                // First initialization of the input signals
                // Load acc <= -1 (ROM[0] = 0xFFXX)

                ////////////////////////////////////////////
                //                                        //
                //    THIS BLOC IS FOR TESTING THE ALU    //
                //        AND IT IS THE MAIN BLOC         //
                //    USED TO TEST THE FOLLOWING BLOCS    //
                //                                        //
                ////////////////////////////////////////////

                dut.io.dBusAccess.poke(0.U)    // dBus mux on dd (dBus=-1)
                dut.io.ramAddrSel.poke(0.U)
                dut.io.ramWrite.poke(0.U)
                dut.io.xWrite.poke(0.U)
                dut.io.xInc.poke(0.U)
                dut.io.yWrite.poke(0.U)
                dut.io.accWrite.poke(1.U)      // write the value of -1 to ACC
                dut.io.iocWrite.poke(0.U)
                dut.io.inputEnble.poke(0.U)
                dut.io.outputEnble.poke(0.U)
                dut.io.ioCtlEnble.poke(0.U)
                dut.io.pcHighWrite.poke(0.U)
                dut.io.pcLowWrite.poke(0.U)
                dut.io.aluFuct.poke(0.U)      // passing dBus to resultBus
                dut.io.GamepadIn.poke(0.U)
                dut.io.KeyboardIn.poke(0.U)

                dut.clock.step(1)              

                // Adding acc=-1 with dd=2 (02) from the ROM[3] = 0x02XX 
                
                dut.io.dBusAccess.poke(0.U)   // dBus mux on dd
                dut.io.accWrite.poke(0.U)     // deasserting Acc write
                dut.io.aluFuct.poke(4.U)      // ALU addition

                dut.clock.step(1)

                //////////////////////////////////////////////////
                //                                              //
                //    THIS BLOC IS FOR TESTING THE REGISTERS    //
                //                                              //
                //////////////////////////////////////////////////

                // Saving the ALU result in x and y

                dut.io.dBusAccess.poke(0.U)   // dBus mux on dd
                dut.io.xWrite.poke(1.U)       // write the value 1 to Y
                dut.io.yWrite.poke(1.U)       // write the value 1 to X
                dut.io.aluFuct.poke(4.U)      // ALU addition
                
                dut.clock.step(1)

                // Incrementing X to the value 2

                dut.io.dBusAccess.poke(0.U)   // dBus mux on dd
                dut.io.xWrite.poke(0.U)       // deassert X writing
                dut.io.xInc.poke(1.U)         // increment X
                dut.io.yWrite.poke(0.U)       // deassert Y writing
                dut.io.aluFuct.poke(4.U)      // ALU addition

                dut.clock.step(1)

                // Incrementing X to the value 3

                dut.io.xInc.poke(1.U)         // increment X

                dut.clock.step(1)

                ////////////////////////////////////////////////////
                //                                                //
                //    THIS BLOC IS FOR TESTING FUTTHER THE ALU    //
                //                                                //
                ////////////////////////////////////////////////////

                // Substracting acc=-1 with dd=2 (02) from the ROM[3] = 0x02XX 
                
                // dut.io.dBusAccess.poke(0.U)   // dBus mux on dd
                // dut.io.aluFuct.poke(5.U)      // ALU substraction

                // dut.clock.step(1)

                // // Anding acc=-1 with dd=2 (02) from the ROM[3] = 0x02XX 
                
                // dut.io.dBusAccess.poke(0.U)   // dBus mux on dd
                // dut.io.aluFuct.poke(1.U)      // ALU bitwise And

                // dut.clock.step(1)

                // // Oring acc=-1 with dd=2 (02) from the ROM[3] = 0x02XX 
                
                // dut.io.dBusAccess.poke(0.U)   // dBus mux on dd
                // dut.io.aluFuct.poke(2.U)      // ALU bitwise Or

                // dut.clock.step(1)

                // // Xoring acc=-1 with dd=2 (02) from the ROM[3] = 0x02XX 
                
                // dut.io.dBusAccess.poke(0.U)   // dBus mux on dd
                // dut.io.aluFuct.poke(3.U)      // ALU bitwise Xor

                // dut.clock.step(1)

                // // returning acc value from ALU acc=-1 with dd=2 (02) from the ROM[3] = 0x02XX 

                // dut.io.dBusAccess.poke(0.U)   // dBus mux on dd
                // dut.io.aluFuct.poke(6.U)      // ALU return a

                // dut.clock.step(1)

                // // ALU decrement acc value acc=-1 with dd=2 (02) from the ROM[3] = 0x02XX 

                // dut.io.dBusAccess.poke(0.U)   // dBus mux on dd
                // dut.io.aluFuct.poke(7.U)      // ALU decriment a

                // dut.clock.step(1)

                /////////////////////////////////////////////
                //                                         //
                //    THIS BLOC IS FOR TESTING THE MAU     //
                //         NEEDS THE REGISTERS BLOC        //
                //                                         //
                /////////////////////////////////////////////

                // // Composing the RAM address from dd - ramAddrSel = 0 

                // dut.io.ramAddrSel.poke(0.U)   // RAM addres comes form dd
                // dut.io.xInc.poke(0.U)         // deasserting the increment X
                // dut.io.aluFuct.poke(0.U)      // deaasering the ALU

                // dut.clock.step(1)

                // // Composing the RAM address from X - ramAddrSel = 1 
                
                // dut.io.ramAddrSel.poke(1.U)   // RAM addres comes form X

                // dut.clock.step(1)

                // // Composing the RAM address from Y:dd - ramAddrSel = 2 
                
                // dut.io.ramAddrSel.poke(2.U)   // RAM addres comes form Y:dd

                // dut.clock.step(1)

                // // Composing the RAM address from Y:X - ramAddrSel = 2 
                
                // dut.io.ramAddrSel.poke(3.U)   // RAM addres comes form Y:X

                // dut.clock.step(1) 

                /////////////////////////////////////////////
                //                                         //
                //    THIS BLOC IS FOR TESTING THE PC      //
                //                                         //
                /////////////////////////////////////////////

                // // Updating PC low part with dd - dd = 2 

                // dut.io.ramAddrSel.poke(0.U)   // RAM addres comes form dd
                // dut.io.xInc.poke(0.U)         // deasserting the increment X
                // dut.io.pcLowWrite.poke(1.U)   // updating the lower part of PC

                // dut.clock.step(1)

                // // Updating PC low and high part with Y:dd - dd = 2 , y = 1

                // dut.io.ramAddrSel.poke(0.U)   // RAM addres comes form dd
                // dut.io.pcLowWrite.poke(1.U)   // updating the lower part of PC
                // dut.io.pcHighWrite.poke(1.U)   // updating the higher part of PC

                // dut.clock.step(1)

                /////////////////////////////////////////////
                //                                         //
                //    THIS BLOC IS FOR TESTING THE RAM     //
                //                                         //
                /////////////////////////////////////////////

                // // Reading from the RAM at address 0:dd = 2 , RAM[2] = 18

                // dut.io.dBusAccess.poke(1.U)   // dBus get the value of RAM
                // dut.io.xInc.poke(0.U)         // deasserting the increment X
                // dut.io.aluFuct.poke(0.U)      // passing dBus to resultBuss

                // dut.clock.step(1)

                // // Writing to the RAM at address 0:dd = 2 the value of dd = 2, RAM[2] = 18

                // dut.io.dBusAccess.poke(0.U)   // dBus get the value of dd
                // dut.io.ramWrite.poke(1.U)     // Write to the RAM command                

                // dut.clock.step(1)

                // dut.io.ramWrite.poke(0.U)     // deasserting the Write to the RAM command

                // dut.clock.step(1)

                //////////////////////////////////////////////
                //                                          //
                //    THIS BLOC IS FOR TESTING THE dBus     //
                //                                          //
                //////////////////////////////////////////////

                // // Putting the value of Acc to dBus

                // dut.io.dBusAccess.poke(2.U)   // dBus get the value of Acc
                // dut.io.xInc.poke(0.U)         // deasserting the increment X
                // dut.io.aluFuct.poke(0.U)      // passing dBus to resultBuss

                // dut.clock.step(1)

                ////////////////////////////////////////////////
                //                                            //
                //    THIS BLOC IS FOR TESTING THE OUTPUT     //
                //                                            //
                ////////////////////////////////////////////////

                // // Putting the value 0x10 from the ROM to IOC register to allow OUT register writing
                // // We need to change the ROM in a way that dd = 0x10 (the old value was 0x02XX)

                // dut.io.dBusAccess.poke(0.U)   // dBus get the value of dd
                // dut.io.xInc.poke(0.U)         // deasserting the increment X
                // dut.io.aluFuct.poke(0.U)      // passing dBus to resultBuss
                // dut.io.iocWrite.poke(1.U)     // Writing the write control signal to IOC

                // dut.clock.step(1)

                // // Outputting the Write signal of OUT register from IOU.
                // // We need to change the ROM again to the initial value dd = 0x02 (ROM[7] = 0x02XX)

                // dut.io.dBusAccess.poke(0.U)   // dBus get the value of dd
                // dut.io.aluFuct.poke(0.U)      // passing dBus to resultBuss
                // dut.io.iocWrite.poke(0.U)     // deassert the writing on IOC
                // dut.io.outputEnble.poke(1.U)  // Outputting the write control of OUT from IOU

                // dut.clock.step(2)

                ////////////////////////////////////////////////
                //                                            //
                //    THIS BLOC IS FOR TESTING THE INPUTS     //
                //                                            //
                ////////////////////////////////////////////////

                // Putting the value 0x03 from the ROM to IOC register to allow OUT register writing
                // We need to change the ROM in a way that dd = 0x03 (the old value in the ROM was 0x02XX)

                dut.io.xInc.poke(0.U)         // deasserting the increment X
                dut.io.dBusAccess.poke(0.U)   // dBus get the value of dd
                dut.io.aluFuct.poke(0.U)      // passing dBus to resultBuss
                dut.io.iocWrite.poke(1.U)     // Writing the write control signal to IOC

                dut.clock.step(1)

                dut.io.iocWrite.poke(0.U)     // deassert the writing on IOC

                dut.clock.step(1)
                
                // Putting a value in serial to the Gamepad and Keyboard shifters
                // the value 0x0F to the Gamepad and the value 0xF0 to the Keyboard
                
                dut.io.GamepadIn.poke(0.U)    // putting the value 0, 4 times in GamepadIn shifter
                dut.io.KeyboardIn.poke(1.U)   // putting the value 1, 4 times in KeyboardIn shifter

                dut.clock.step(4) 
                
                dut.io.GamepadIn.poke(1.U)    // putting the value 1, 4 times in GamepadIn shifter
                dut.io.KeyboardIn.poke(0.U)   // putting the value 0, 4 times in KeyboardIn shifte

                dut.clock.step(4)

                dut.io.GamepadIn.poke(0.U)    // deasserting the input of the GamepadIn shifter
                dut.io.KeyboardIn.poke(0.U)   // deasserting the input of the KeyboardIn shifter
                dut.io.ioCtlEnble.poke(1.U)   // outputting the parallel clock from IOU for Keyboard and Gamepad

                dut.clock.step(1)

                dut.io.ioCtlEnble.poke(0.U)   // deasserting the IOU control signals

                dut.clock.step(1)

                // Putting the value 0x01 from the ROM to IOC register to allow the Gamepad reading
                // We need to change the ROM in a way that dd = 0x01 (the old value in the ROM was 0x03XX)

                dut.io.dBusAccess.poke(0.U)   // dBus get the value of dd
                dut.io.aluFuct.poke(0.U)      // passing dBus to resultBuss
                dut.io.iocWrite.poke(1.U)     // Writing the write control signal to IOC

                dut.clock.step(1)

                dut.io.iocWrite.poke(0.U)     // deassert the writing on IOC

                dut.clock.step(1)

                dut.io.dBusAccess.poke(3.U)   // dBus get the value of the inputs
                dut.io.aluFuct.poke(0.U)      // passing dBus to resultBuss
                dut.io.inputEnble.poke(1.U)   // Outputting the read control of the Gamepad from IOU

                dut.clock.step(1)

                // Putting the value 0x02 from the ROM to IOC register to allow the Keyboard reading
                // We need to change the ROM in a way that dd = 0x02 (the old value in the ROM was 0x01XX)

                dut.io.dBusAccess.poke(0.U)   // dBus get the value of dd
                dut.io.aluFuct.poke(0.U)      // passing dBus to resultBuss
                dut.io.iocWrite.poke(1.U)     // Writing the write control signal to IOC

                dut.clock.step(1)

                dut.io.iocWrite.poke(0.U)     // deassert the writing on IOC

                dut.clock.step(1)

                dut.io.dBusAccess.poke(3.U)   // dBus get the value of the inputs
                dut.io.aluFuct.poke(0.U)      // passing dBus to resultBuss
                dut.io.inputEnble.poke(1.U)   // Outputting the read control of the keyboard from IOU

                dut.clock.step(1)
        }
    }
}   