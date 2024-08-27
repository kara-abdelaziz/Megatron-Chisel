import  chisel3._
import  chisel3.util._
import  _root_.circt.stage.ChiselStage

class  Arbiter[T <: Data](data : T, n : Int)  extends  Module
{
    val  io  =  IO(new  Bundle{ val  inputs  =  Flipped(Vec(n, new  DecoupledIO(data)))
                                val  output  =  new  DecoupledIO(data)    })

    io.output  <>  io.inputs.reduceTree((x, y) => arbiter_2_1(x, y))

    def  arbiter_2_1(a : DecoupledIO[T], b : DecoupledIO[T]): DecoupledIO[T] =
    {
        val  dataReg  =  Reg(data)

        val  emptyReg    =  RegInit(true.B)
        val  readyAReg   =  RegInit(false.B)
        val  readyBReg   =  RegInit(false.B)
        val  balanceRag  =  RegInit(false.B)

        a.ready  :=  readyAReg
        b.ready  :=  readyBReg
        
        val  out  =  Wire(new  DecoupledIO(data))

        when(emptyReg)
        {
            when(a.valid & b.valid & !readyAReg  & !readyBReg)
            {
                when(balanceRag)
                {
                    readyAReg   :=  true.B
                    balanceRag  :=  false.B
                }
                .otherwise
                {
                    readyBReg   :=  true.B
                    balanceRag  :=  true.B
                }
            }        
            .elsewhen(a.valid & !readyBReg)
            {
                readyAReg  :=  true.B
            }
            .elsewhen(b.valid & !readyAReg)
            {
                readyBReg  :=  true.B
            }
        }

        when(readyAReg)
        {
            dataReg    :=  a.bits
            readyAReg  :=  false.B
            emptyReg   :=  false.B 
        }

        when(readyBReg)
        {
            dataReg    :=  b.bits
            readyBReg  :=  false.B
            emptyReg   :=  false.B 
        }

        out.valid  :=  false.B

        when(!emptyReg)
        {
            out.valid  :=  true.B
        }

        out.bits  :=  dataReg

        when(out.ready)
        {
            emptyReg  :=  true.B
        }

        out
    }
}

object  mainArbiter extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  Arbiter(UInt(8.W), 4),
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}