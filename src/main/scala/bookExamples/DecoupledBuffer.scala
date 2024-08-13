import  chisel3._ 
import  chisel3.util._ 
import  _root_.circt.stage.ChiselStage

class  DecoupledBuffer  extends  Module
{
    val  io  =  IO(new  Bundle{ val  input   =  Flipped(new  DecoupledIO(UInt(8.W)))
                                val  output  =  new  DecoupledIO(UInt(8.W))       })
    
    val  bufferReg  =  RegInit(0.U(8.W))
    val  emptyReg   =  RegInit(true.B)

    io.input.ready   :=  emptyReg
    io.output.valid  :=  !emptyReg
    io.output.bits   :=  bufferReg

    when(emptyReg && io.input.valid)
    {
        bufferReg  :=  io.input.bits
        emptyReg   :=  false.B
    }

    when(!emptyReg && io.output.ready)
    {
        emptyReg  :=  true.B
    }
}

object  mainDecoupledBuffer  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new DecoupledBuffer,
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}