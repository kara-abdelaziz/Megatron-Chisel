import  megatron._
import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_cu  extends  AnyFlatSpec  with  ChiselScalatestTester
{
    "DUT control unit" should "perform diffrent test units with opcodes and check the outputted control signals." in
    {
        test(new  CU).withAnnotations(Seq(WriteVcdAnnotation))
        {
            dut => 

                // initialization

                dut.io.opCode.poke("b000_000_00".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)

                dut.clock.step(1)

                // testing data bus access

                dut.io.opCode.poke("b000_000_00".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)

                dut.clock.step(1)

                dut.io.opCode.poke("b000_000_01".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)

                dut.clock.step(1)

                dut.io.opCode.poke("b000_000_10".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)

                dut.clock.step(1)

                dut.io.opCode.poke("b000_000_11".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)

                dut.clock.step(1)



                ////////////////////////////////////////////
                //                                        //
                //        TESTING LOAD INSTRUCTION        //
                //                                        //
                ////////////////////////////////////////////

                // BUS ACCESS from DD
                
                // MV AC,$dd
                dut.io.opCode.poke("b000_000_00".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // MV X,$dd
                dut.io.opCode.poke("b000_100_00".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // MV Y,$dd
                dut.io.opCode.poke("b000_101_00".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // MV OUT,$dd
                dut.io.opCode.poke("b000_110_00".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // MV OUTxx,$dd
                dut.io.opCode.poke("b000_111_00".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                

                // BUS ACCESS from MEM
                
                // LD AC,[$dd]
                dut.io.opCode.poke("b000_000_01".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // LD AC,[X]
                dut.io.opCode.poke("b000_001_01".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // LD AC,[Y,$dd]
                dut.io.opCode.poke("b000_010_01".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // LD AC,[Y,X]
                dut.io.opCode.poke("b000_011_01".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // LD X,[$dd]
                dut.io.opCode.poke("b000_100_01".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // LD Y,[$dd]
                dut.io.opCode.poke("b000_101_01".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // LD OUT,[$dd]
                dut.io.opCode.poke("b000_110_01".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // LD OUTxx,[Y,X]
                dut.io.opCode.poke("b000_111_01".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                

                // BUS ACCESS from ACC
                
                // MV X,AC
                dut.io.opCode.poke("b000_100_10".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // MV Y,AC
                dut.io.opCode.poke("b000_101_10".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // MV OUT,AC
                dut.io.opCode.poke("b000_110_10".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // MV OUTxx,AC
                dut.io.opCode.poke("b000_111_10".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                ////////////////////////////////////////////
                //                                        //
                //         TESTING ADD INSTRUCTION        //
                //                                        //
                ////////////////////////////////////////////

                // BUS ACCESS from DD
                
                // ADD AC,$dd
                dut.io.opCode.poke("b100_000_00".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // ADD X,$dd
                dut.io.opCode.poke("b100_100_00".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // ADD Y,$dd
                dut.io.opCode.poke("b100_101_00".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // ADD OUT,$dd
                dut.io.opCode.poke("b100_110_00".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // ADD OUTxx,$dd
                dut.io.opCode.poke("b100_111_00".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                

                // BUS ACCESS from MEM
                
                // ADD AC,[$dd]
                dut.io.opCode.poke("b100_000_01".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // ADD AC,[X]
                dut.io.opCode.poke("b100_001_01".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // ADD AC,[Y,$dd]
                dut.io.opCode.poke("b100_010_01".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // ADD AC,[Y,X]
                dut.io.opCode.poke("b100_011_01".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // ADD X,[$dd]
                dut.io.opCode.poke("b100_100_01".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // ADD Y,[$dd]
                dut.io.opCode.poke("b100_101_01".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // ADD OUT,[$dd]
                dut.io.opCode.poke("b100_110_01".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // ADD OUTxx,[Y,X]
                dut.io.opCode.poke("b100_111_01".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                

                // BUS ACCESS from ACC
                
                // SL AC
                dut.io.opCode.poke("b100_000_10".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // SL X
                dut.io.opCode.poke("b100_100_10".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // SL Y
                dut.io.opCode.poke("b100_101_10".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // SL OUT
                dut.io.opCode.poke("b100_110_10".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // SL OUTxx
                dut.io.opCode.poke("b100_111_10".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)


                ////////////////////////////////////////////
                //                                        //
                //          TESTING ALU OPERATIONS        //
                //                                        //
                ////////////////////////////////////////////

                // LD AC,$dd
                dut.io.opCode.poke("b000_000_00".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // AND AC,$dd
                dut.io.opCode.poke("b001_000_00".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)
                
                // OR AC,$dd
                dut.io.opCode.poke("b010_000_00".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // XOR AC,$dd
                dut.io.opCode.poke("b011_000_00".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // ADD AC,$dd
                dut.io.opCode.poke("b100_000_00".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // SUB AC,$dd
                dut.io.opCode.poke("b101_000_00".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // ST [$dd],$dd
                dut.io.opCode.poke("b110_000_00".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // JMP $dd
                dut.io.opCode.poke("b111_000_00".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)


                ////////////////////////////////////////////
                //                                        //
                //          TESTING ST INSTRUCTION        //
                //                                        //
                ////////////////////////////////////////////

                // BUS ACCESS from DD
                
                // ST [$dd],$dd
                dut.io.opCode.poke("b110_000_00".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // ST [X],$dd
                dut.io.opCode.poke("b110_001_00".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // ST [Y,$dd],$dd
                dut.io.opCode.poke("b110_010_00".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // ST [Y,X],$dd
                dut.io.opCode.poke("b110_011_00".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                

                // BUS ACCESS from ACC
                
                // ST [$dd],AC
                dut.io.opCode.poke("b110_000_10".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // ST [X],AC
                dut.io.opCode.poke("b110_001_10".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // ST [Y,$dd],AC
                dut.io.opCode.poke("b110_010_10".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // ST [Y,X],AC
                dut.io.opCode.poke("b110_011_10".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                

                // BUS ACCESS from IN
                
                //ST [$dd],IN
                dut.io.opCode.poke("b110_000_11".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // ST [X],IN
                dut.io.opCode.poke("b110_001_11".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // ST [Y,$dd],IN
                dut.io.opCode.poke("b110_010_11".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // ST [Y,X],IN
                dut.io.opCode.poke("b110_011_11".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)


                ////////////////////////////////////////////
                //                                        //
                //          TESTING IO INSTRUCTION        //
                //                                        //
                ////////////////////////////////////////////
             
                // IO [$dd]
                dut.io.opCode.poke("b110_000_01".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // IO [X]
                dut.io.opCode.poke("b110_001_01".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // IO [Y,$dd]
                dut.io.opCode.poke("b110_010_01".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // IO [Y,X]
                dut.io.opCode.poke("b110_011_01".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // IO $dd
                dut.io.opCode.poke("b110_100_01".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // CE
                dut.io.opCode.poke("b110_101_01".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // IO AC
                dut.io.opCode.poke("b110_110_01".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)

                // IO IN
                dut.io.opCode.poke("b110_111_01".U)
                dut.io.acc7.poke(false.B)
                dut.io.a_eq_b.poke(false.B)
                
                dut.clock.step(1)
        }
    }
}   