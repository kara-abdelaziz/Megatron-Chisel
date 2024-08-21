import  scala.util._
import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec

class  DUT_min_vector extends  AnyFlatSpec  with  ChiselScalatestTester
{
    val  rand  =  new Random
    
    "DUT minimum value in a vector" should "find the minimum and its index" in
    {
        test(new  MinimumVector(10))
        {
            dut => 
            
                for(i <- 0 until 10)
                {
                    val  in  =  rand.nextInt(100).U
                    println(in)
                    dut.io.inputs(i).poke(in)
                }

                dut.clock.step(1)

                println(dut.io.minValue.input.peekInt())
                println(dut.io.minValue.index.peekInt())
        }
    }

    "DUT minimum value in a vector of tuples" should "find the minimum and its index" in
    {
        test(new  MinimumVectorWithTuples(10))
        {
            dut => 
            
                for(i <- 0 until 10)
                {
                    val  in  =  rand.nextInt(100).U
                    println(in)
                    dut.io.inputs(i).poke(in)
                }

                dut.clock.step(1)

                println(dut.io.minValue.input.peekInt())
                println(dut.io.minValue.index.peekInt())
        }
    }

    "DUT minimum value in a vector of mixed vector" should "find the minimum and its index" in
    {
        test(new  MinimumVectorWithMixVec(10))
        {
            dut => 
            
                for(i <- 0 until 10)
                {
                    val  in  =  rand.nextInt(100).U
                    println(in.toString + " i = " + i.toString)
                    dut.io.inputs(i).poke(in)
                }

                dut.clock.step(1)

                println(dut.io.minValue(0).peekInt())
                println(dut.io.minValue(1).peekInt())
        }
    }
}