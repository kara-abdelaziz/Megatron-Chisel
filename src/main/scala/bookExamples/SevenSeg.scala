import  chisel3._
import  chisel3.util._ 
import  _root_.circt.stage.ChiselStage

class  SevenSegment  extends  Module
{
    val  io  =  IO(new  Bundle{ val  digit     =  Input(UInt(4.W))
                                val  segments  =  Output(UInt(7.W)) })
    
    io.segments  :=  0.U(7.W)

    switch (io.digit)
    {
        is (0.U(4.W))  {  io.segments :=  "b111_1110".U(7.W)  }
        is (1.U(4.W))  {  io.segments :=  "b011_0000".U(7.W)  }
        is (2.U(4.W))  {  io.segments :=  "b110_1101".U(7.W)  }
        is (3.U(4.W))  {  io.segments :=  "b111_1001".U(7.W)  }
        is (4.U(4.W))  {  io.segments :=  "b011_0011".U(7.W)  }
        is (5.U(4.W))  {  io.segments :=  "b101_1011".U(7.W)  }
        is (6.U(4.W))  {  io.segments :=  "b101_1111".U(7.W)  }
        is (7.U(4.W))  {  io.segments :=  "b111_0000".U(7.W)  }
        is (8.U(4.W))  {  io.segments :=  "b111_1111".U(7.W)  }
        is (9.U(4.W))  {  io.segments :=  "b111_1011".U(7.W)  }
    }
}

object  mainSevenSegment  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  SevenSegment,
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}

