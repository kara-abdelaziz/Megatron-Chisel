import  chisel3._
import  _root_.circt.stage.ChiselStage

object  generate_components
{
    class  ComplexIO  extends  Bundle
    {
        val  d  =  UInt(10.W)
        val  b  =  Bool()
    }
    
    
    def  muxGen[T <: Data](select : Bool, choise0 : T, choise1 : T): T =  
    {
        val  ret  =  choise0

        when(select)
        {
            ret  :=  choise1
        }

        ret
    }

    val  ch0  =  new  ComplexIO()
    val  ch1  =  new  ComplexIO()

    ch0.d  :=  10.U
    ch0.b  :=  false.B

    ch1.d  :=  20.U
    ch1.b  :=  true.B

    val  sel : Bool = true.B
    val  mux  =  muxGen(sel, ch0, ch1)    
}

