import  chisel3._
import  chisel3.util._
import  _root_.circt.stage.ChiselStage

class  Decoder  extends  Module
{
    val  io  =  IO(new  Bundle{  val  i0  =  Input(UInt(1.W))
                                 val  i1  =  Input(UInt(1.W))
                                 val  o0  =  Output(UInt(1.W))
                                 val  o1  =  Output(UInt(1.W))
                                 val  o2  =  Output(UInt(1.W))
                                 val  o3  =  Output(UInt(1.W)) })

    val  sel  =  io.i1 ## io.i0

    io.o0  :=  0.U
    io.o1  :=  0.U
    io.o2  :=  0.U
    io.o3  :=  0.U

    switch (sel)
    {
        is(0.U(2.W)) {  io.o0  :=  1.U  }
        is(1.U(2.W)) {  io.o1  :=  1.U  }
        is(2.U(2.W)) {  io.o2  :=  1.U  }
        is(3.U(2.W)) {  io.o3  :=  1.U  }
    }

    printf("Decoder output = %d %d %d %d\n", io.o0, io.o1, io.o2, io.o3)
}

object  mainDecoder  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  Decoder,
                                        Array("--target-dir", "generated"), 
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}