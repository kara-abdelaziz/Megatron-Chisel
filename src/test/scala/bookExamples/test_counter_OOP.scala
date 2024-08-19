import  chisel3._ 
import  chiseltest._ 
import  org.scalatest.flatspec.AnyFlatSpec


trait  TestCounters[T <: CouterSuperClass]
{
    def  testCounter(dut : T, n : Int)=
    {
        var  count  =  -1
        
        for(_ <- 0 to n * 3)
        {
            if(count > 0)
            {
                dut.io.tick.expect(false.B)
            }
            else if(count == 0)
            {
                dut.io.tick.expect(true.B)
            }

            if(dut.io.tick.peekBoolean())
            {
                count  =  n - 1
            }
            else
            {
                count  -=  1
            }

            dut.clock.step(1)
        }
    }
}

class  DUT_counter_OOP extends  AnyFlatSpec  with  ChiselScalatestTester  with  TestCounters[CouterSuperClass]
{
    "DUT counter OOP" should "the first test is about the counter up" in
    {
        test(new  CounterUp(10))
        {
            dut => testCounter(dut, 10)
        }
    }

    "DUT counter OOP" should "the sencond test is about the counter down" in
    {
        test(new  CounterDown(15))
        {
            dut => testCounter(dut, 15)
        }
    }

    "DUT counter OOP" should "the first test is about the nerd counter" in
    {
        test(new  CounterNerd(7))
        {
            dut => testCounter(dut, 7)
        }
    }
}