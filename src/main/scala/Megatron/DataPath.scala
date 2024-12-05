package  megatron

import  chisel3._
import  chisel3.util._
import  _root_.circt.stage.ChiselStage

class  DataPath  extends  Module
{
    val  io  =  IO(new  Bundle{ // Control signals
                                val  dBusAccess  =  Input(UInt(2.W))
                                val  ramAddrSel  =  Input(UInt(2.W))
                                val  ramWrite    =  Input(Bool())
                                val  xWrite      =  Input(Bool()) 
                                val  xInc        =  Input(Bool()) 
                                val  yWrite      =  Input(Bool())
                                val  accWrite    =  Input(Bool())
                                val  iocWrite    =  Input(Bool())
                                val  inputEnble  =  Input(Bool())
                                val  outputEnble =  Input(Bool())
                                val  ioCtlEnble  =  Input(Bool())
                                val  pcHighWrite =  Input(Bool()) 
                                val  pcLowWrite  =  Input(Bool()) 
                                val  aluFuct     =  Input(UInt(3.W))

                                // Gamepad and Keyboard serial inputs
                                val  GamepadIn   =  Input(Bool()) 
                                val  KeyboardIn  =  Input(Bool()) 

                                // Megatron outputs
                                val  acc7        =  Output(Bool())
                                val  carry       =  Output(Bool())
                                val  opCode      =  Output(UInt(8.W))
                                val  output1     =  Output(UInt(8.W)) })
    
    //  all components cration
    
    val  pc     =  Module(new  counter16bit)
    val  rom    =  Module(new  ROM)
    val  mau    =  Module(new  MAU)
    val  ram    =  Module(new  RAM)
    val  alu    =  Module(new  ALU)
    val  iou    =  Module(new  IOU)
    val  x      =  Module(new  counter8bit)
    val  y      =  Module(new  Register8bit)
    val  acc    =  Module(new  Register8bit)
    val  ioc    =  Module(new  Register8bit)
    val  out    =  Module(new  Register8bit)
    val  keyboard_in  =  Module(new  Shifter8bit)
    val  gamepad_in   =  Module(new  Shifter8bit)

    //  datapath interconnecting components

    val  dataBus    =  WireDefault(0.U(8.W))  // Data Bus creation
    val  resultBus  =  WireDefault(0.U(8.W))  // ALU Result Bus creation
    val  inputBus   =  WireDefault(0.U(8.W))  // Input Bus creation

    rom.io.addr    :=  pc.io.out   // PC interconnexions
    pc.io.lowerIn  :=  dataBus
    pc.io.upperIn  :=  y.io.out

    io.opCode    :=  rom.io.ir     // ROM interconnexions
    mau.io.data  :=  rom.io.data

    mau.io.y     :=  y.io.out      // MAU interconnexions
    mau.io.x     :=  x.io.out
    ram.io.addr  :=  mau.io.memAddr

    ram.io.in  :=  dataBus         // RAM interconnexions

    y.io.in  :=  resultBus         // y register interconnexions
    x.io.in  :=  resultBus         // x register interconnexions

    resultBus  :=  alu.io.sum.asUInt    // ALU interconnexions
    alu.io.b   :=  dataBus.asSInt
    alu.io.a   :=  acc.io.out.asSInt

    acc.io.in  :=  resultBus       // AC register interconnexions
    ioc.io.in  :=  resultBus       // IOC register interconnexions
    out.io.in  :=  resultBus       // OUT register interconnexions
    io.output1 :=  out.io.out
    iou.io.in  :=  ioc.io.out      // OUI register interconnexions
    

    dataBus  :=  0.U    
    switch(io.dBusAccess)          // Data Bus access multiplexer
    {
        is(0.U)
        {
            dataBus  :=  rom.io.data
        }

        is(1.U)
        {
            dataBus  :=  ram.io.out
        }

        is(2.U)
        {
            dataBus  :=  acc.io.out 
        }

        is(3.U)
        {
            dataBus  :=  inputBus
        }
    }

    inputBus  :=  0.U    
    switch(iou.io.inputEnable)     // Inputs to Data Bus encoder
    {
        is("b0001".U)
        {
            inputBus  :=  gamepad_in.io.out
        }

        is("b0010".U)
        {
            inputBus  :=  keyboard_in.io.out
        }

        is("b0100".U)
        {
            inputBus  :=  0.U
        }

        is("b1000".U)
        {
            inputBus  :=  0.U
        }
    }

    //  connexion of control signals to components

    pc.io.upperWrite  :=  io.pcHighWrite    // pc control signals connexion
    pc.io.lowerWrite  :=  io.pcLowWrite

    mau.io.highAddr  :=  io.ramAddrSel(1).asBool    // pmau control signals connexion
    mau.io.lowAddr   :=  io.ramAddrSel(0).asBool

    ram.io.write     :=  io.ramWrite        // ram control signals connexion
    
    x.io.write       :=  io.xWrite          // x control signals connexion
    x.io.inc         :=  io.xInc
    y.io.write       :=  io.yWrite          // y control signals connexion
    acc.io.write     :=  io.accWrite        // acc control signals connexion
    ioc.io.write     :=  io.iocWrite        // ioc control signals connexion

    alu.io.func      :=  io.aluFuct         // alu functions signals connexion

    iou.io.inputEnCtr   :=  io.inputEnble        // iou control signals connexion
    iou.io.outputEnCtr  :=  io.outputEnble
    iou.io.ioEnCtr      :=  io.ioCtlEnble
    
    io.acc7    :=  acc.io.out(7).asBool       //  control signals yield to CU
    io.carry   :=  alu.io.carry

    // gamepad and keyboard serial and parallel clocks should be outsoursed and not controlled by IOC and IOU
    
    gamepad_in.io.pallelClock    :=  iou.io.periphralCtr(0).asBool   //  connecting the iou control signals to peripherals controls
    keyboard_in.io.pallelClock   :=  iou.io.periphralCtr(1).asBool

    out.io.write  :=  iou.io.outputWrite(0).asBool                  //  connecting the iou control signal to output peripherals

    gamepad_in.io.in    :=  io.GamepadIn
    keyboard_in.io.in   :=  io.KeyboardIn
}

object  mainDataPath  extends  App
{
    ChiselStage.emitSystemVerilogFile(new  DataPath,
                                      Array("--target-dir", "generated"), 
                                      firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"))
}
