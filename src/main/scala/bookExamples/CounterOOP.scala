import  chisel3._
import  chisel3.util._
import  _root_.circt.stage.ChiselStage

abstract  class  CouterSuperClass  extends  Module
{
    val  io  =  IO(new  Bundle{ val  tick  =  Output(Bool()) })
}

class  CounterUp(n : Int)  extends  CouterSuperClass
{
    val  counterReg  =  RegInit(0.U(log2Ceil(n).W))

    counterReg  :=  counterReg + 1.U
    io.tick     :=  false.B

    when(counterReg === (n-1).U)
    {
        counterReg  :=  0.U
        io.tick     :=  true.B
    }
}

class  CounterDown(n : Int)  extends  CouterSuperClass
{
    val  counterReg  =  RegInit((n-1).U(log2Ceil(n).W))

    counterReg  :=  counterReg - 1.U
    io.tick     :=  false.B

    when(counterReg === 0.U)
    {
        counterReg  :=  (n-1).U
        io.tick     :=  true.B
    }
}

class  CounterNerd(n : Int)  extends  CouterSuperClass
{
    val  countReg  =  RegInit((n-2).S((log2Ceil(n-1)+1).W))

    countReg  :=  countReg - 1.S
    io.tick   :=  false.B  
    
    when (countReg(log2Ceil(n-1)) === 1.U)
    {
        countReg  :=  (n-2).S
        io.tick   :=  true.B
    }
}

object  mainCouterOOP  extends  App
{
    ChiselStage.emitSystemVerilogFile(  new  CounterNerd(10),
                                        Array("--target-dir", "generated"),
                                        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}