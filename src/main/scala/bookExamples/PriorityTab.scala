import  chisel3._
import  chisel3.util._
import  _root_.circt.stage.ChiselStage

class  PriorityTab  extends  Module
{
    val  io  =  IO(new  Bundle{   val  req  =  Input(UInt(4.W))
                                  val  grt  =  Output(UInt(4.W))})

    io.grt  :=  0.U

    switch(io.req)
    {
        is("h0".U)  {  io.grt  :=  0.U  }
        is("h1".U)  {  io.grt  :=  1.U  }
        is("h2".U)  {  io.grt  :=  2.U  }
        is("h3".U)  {  io.grt  :=  1.U  }

        is("h4".U)  {  io.grt  :=  4.U  }
        is("h5".U)  {  io.grt  :=  1.U  }
        is("h6".U)  {  io.grt  :=  2.U  }
        is("h7".U)  {  io.grt  :=  1.U  }

        is("h8".U)  {  io.grt  :=  8.U  }
        is("h9".U)  {  io.grt  :=  1.U  }
        is("ha".U)  {  io.grt  :=  2.U  }
        is("hb".U)  {  io.grt  :=  1.U  }

        is("hc".U)  {  io.grt  :=  4.U  }
        is("hd".U)  {  io.grt  :=  1.U  }
        is("he".U)  {  io.grt  :=  2.U  }
        is("hf".U)  {  io.grt  :=  1.U  }
    }
}

object  mainPriorityTab  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  PriorityTab,
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}