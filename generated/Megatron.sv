// Generated by CIRCT firtool-1.62.0
module counter16bit(
  input         clock,
                reset,
  input  [7:0]  io_lowerIn,
                io_upperIn,
  input         io_lowerWrite,
                io_upperWrite,
  output [15:0] io_out
);

  reg [7:0] lowCounterReg;
  reg [7:0] highCounterReg;
  always @(posedge clock) begin
    if (reset) begin
      lowCounterReg <= 8'h0;
      highCounterReg <= 8'h0;
    end
    else begin
      if (io_lowerWrite)
        lowCounterReg <= io_lowerIn;
      else
        lowCounterReg <= lowCounterReg + 8'h1;
      if (io_upperWrite)
        highCounterReg <= io_upperIn;
      else if (&lowCounterReg)
        highCounterReg <= highCounterReg + 8'h1;
    end
  end // always @(posedge)
  assign io_out = {highCounterReg, lowCounterReg};
endmodule

// VCS coverage exclude_file
module rom_65536x16(
  input  [15:0] R0_addr,
  input         R0_en,
                R0_clk,
  output [15:0] R0_data
);

  reg [15:0] Memory[0:65535];
  `ifdef ENABLE_INITIAL_MEM_
    initial
      $readmemh("/home/snakeas/Megatron-Chisel/src/main/resources/ROM.hex", Memory);
  `endif // ENABLE_INITIAL_MEM_
  assign R0_data = R0_en ? Memory[R0_addr] : 16'bx;
endmodule

module ROM(
  input         clock,
  input  [15:0] io_addr,
  output [7:0]  io_ir,
                io_data
);

  wire [15:0] _rom_ext_R0_data;
  rom_65536x16 rom_ext (
    .R0_addr (io_addr),
    .R0_en   (1'h1),
    .R0_clk  (clock),
    .R0_data (_rom_ext_R0_data)
  );
  assign io_ir = _rom_ext_R0_data[7:0];
  assign io_data = _rom_ext_R0_data[15:8];
endmodule

module MAU(
  input  [7:0]  io_data,
                io_x,
                io_y,
  input         io_highAddr,
                io_lowAddr,
  output [15:0] io_memAddr
);

  assign io_memAddr = {io_highAddr ? io_y : 8'h0, io_lowAddr ? io_x : io_data};
endmodule

// VCS coverage exclude_file
module ram_65536x8(
  input  [15:0] R0_addr,
  input         R0_en,
                R0_clk,
  output [7:0]  R0_data,
  input  [15:0] W0_addr,
  input         W0_en,
                W0_clk,
  input  [7:0]  W0_data
);

  reg [7:0] Memory[0:65535];
  always @(posedge W0_clk) begin
    if (W0_en & 1'h1)
      Memory[W0_addr] <= W0_data;
  end // always @(posedge)
  assign R0_data = R0_en ? Memory[R0_addr] : 8'bx;
endmodule

module RAM(
  input         clock,
  input  [7:0]  io_in,
  input  [15:0] io_addr,
  input         io_write,
  output [7:0]  io_out
);

  ram_65536x8 ram_ext (
    .R0_addr (io_addr),
    .R0_en   (1'h1),
    .R0_clk  (~clock),
    .R0_data (io_out),
    .W0_addr (io_addr),
    .W0_en   (io_write),
    .W0_clk  (~clock),
    .W0_data (io_in)
  );
endmodule

module ALU(
  input  [7:0] io_a,
               io_b,
  input  [2:0] io_func,
  output [7:0] io_sum,
  output       io_carry
);

  wire [7:0][7:0] _GEN =
    {{8'h0 - io_a},
     {io_a},
     {io_a - io_b},
     {io_a + io_b},
     {io_a ^ io_b},
     {io_a | io_b},
     {io_a & io_b},
     {io_b}};
  assign io_sum = _GEN[io_func];
  assign io_carry = io_a == 8'h0;
endmodule

module IOU(
  input  [7:0] io_in,
  input        io_inputEnCtr,
               io_outputEnCtr,
               io_ioEnCtr,
  output [3:0] io_inputEnable,
               io_outputWrite,
  output [7:0] io_periphralCtr
);

  assign io_inputEnable = io_inputEnCtr ? io_in[3:0] : 4'h0;
  assign io_outputWrite = io_outputEnCtr ? io_in[7:4] : 4'h0;
  assign io_periphralCtr = io_ioEnCtr ? io_in : 8'h0;
endmodule

module counter8bit(
  input        clock,
               reset,
  input  [7:0] io_in,
  input        io_write,
               io_inc,
  output [7:0] io_out
);

  wire       negClock = ~clock;
  reg  [7:0] negReg;
  always @(posedge negClock) begin
    if (reset)
      negReg <= 8'h0;
    else if (io_write)
      negReg <= io_in;
    else if (io_inc)
      negReg <= negReg + 8'h1;
  end // always @(posedge)
  assign io_out = negReg;
endmodule

module Register8bit(
  input        clock,
               reset,
  input  [7:0] io_in,
  input        io_write,
  output [7:0] io_out
);

  wire       negClock = ~clock;
  reg  [7:0] negReg;
  always @(posedge negClock) begin
    if (reset)
      negReg <= 8'h0;
    else if (io_write)
      negReg <= io_in;
  end // always @(posedge)
  assign io_out = negReg;
endmodule

module Shifter8bit(
  input        clock,
               reset,
               io_in,
               io_pallelClock,
  output [7:0] io_out
);

  reg  [7:0] outputReg;
  wire       negClock = ~clock;
  reg  [7:0] shiftReg;
  always @(posedge clock) begin
    if (reset)
      outputReg <= 8'h0;
    else if (io_pallelClock)
      outputReg <= shiftReg;
  end // always @(posedge)
  always @(posedge negClock) begin
    if (reset)
      shiftReg <= 8'h0;
    else
      shiftReg <= {shiftReg[6:0], io_in};
  end // always @(posedge)
  assign io_out = outputReg;
endmodule

module DataPath(
  input        clock,
               reset,
  input  [1:0] io_dBusAccess,
               io_ramAddrSel,
  input        io_ramWrite,
               io_xWrite,
               io_xInc,
               io_yWrite,
               io_accWrite,
               io_iocWrite,
               io_inputEnble,
               io_outputEnble,
               io_ioCtlEnble,
               io_pcHighWrite,
               io_pcLowWrite,
  input  [2:0] io_aluFuct,
  input        io_GamepadIn,
               io_KeyboardIn,
  output       io_acc7,
               io_carry,
  output [7:0] io_opCode,
               io_output1
);

  wire [7:0]      inputBus;
  wire [7:0]      _gamepad_in_io_out;
  wire [7:0]      _keyboard_in_io_out;
  wire [7:0]      _ioc_io_out;
  wire [7:0]      _acc_io_out;
  wire [7:0]      _y_io_out;
  wire [7:0]      _x_io_out;
  wire [3:0]      _iou_io_inputEnable;
  wire [3:0]      _iou_io_outputWrite;
  wire [7:0]      _iou_io_periphralCtr;
  wire [7:0]      _alu_io_sum;
  wire [7:0]      _ram_io_out;
  wire [15:0]     _mau_io_memAddr;
  wire [7:0]      _rom_io_data;
  wire [15:0]     _pc_io_out;
  wire [3:0][7:0] _GEN = {{inputBus}, {_acc_io_out}, {_ram_io_out}, {_rom_io_data}};
  assign inputBus =
    _iou_io_inputEnable == 4'h1
      ? _gamepad_in_io_out
      : _iou_io_inputEnable == 4'h2 ? _keyboard_in_io_out : 8'h0;
  counter16bit pc (
    .clock         (clock),
    .reset         (reset),
    .io_lowerIn    (_GEN[io_dBusAccess]),
    .io_upperIn    (_y_io_out),
    .io_lowerWrite (io_pcLowWrite),
    .io_upperWrite (io_pcHighWrite),
    .io_out        (_pc_io_out)
  );
  ROM rom (
    .clock   (clock),
    .io_addr (_pc_io_out),
    .io_ir   (io_opCode),
    .io_data (_rom_io_data)
  );
  MAU mau (
    .io_data     (_rom_io_data),
    .io_x        (_x_io_out),
    .io_y        (_y_io_out),
    .io_highAddr (io_ramAddrSel[1]),
    .io_lowAddr  (io_ramAddrSel[0]),
    .io_memAddr  (_mau_io_memAddr)
  );
  RAM ram (
    .clock    (clock),
    .io_in    (_GEN[io_dBusAccess]),
    .io_addr  (_mau_io_memAddr),
    .io_write (io_ramWrite),
    .io_out   (_ram_io_out)
  );
  ALU alu (
    .io_a     (_acc_io_out),
    .io_b     (_GEN[io_dBusAccess]),
    .io_func  (io_aluFuct),
    .io_sum   (_alu_io_sum),
    .io_carry (io_carry)
  );
  IOU iou (
    .io_in           (_ioc_io_out),
    .io_inputEnCtr   (io_inputEnble),
    .io_outputEnCtr  (io_outputEnble),
    .io_ioEnCtr      (io_ioCtlEnble),
    .io_inputEnable  (_iou_io_inputEnable),
    .io_outputWrite  (_iou_io_outputWrite),
    .io_periphralCtr (_iou_io_periphralCtr)
  );
  counter8bit x (
    .clock    (clock),
    .reset    (reset),
    .io_in    (_alu_io_sum),
    .io_write (io_xWrite),
    .io_inc   (io_xInc),
    .io_out   (_x_io_out)
  );
  Register8bit y (
    .clock    (clock),
    .reset    (reset),
    .io_in    (_alu_io_sum),
    .io_write (io_yWrite),
    .io_out   (_y_io_out)
  );
  Register8bit acc (
    .clock    (clock),
    .reset    (reset),
    .io_in    (_alu_io_sum),
    .io_write (io_accWrite),
    .io_out   (_acc_io_out)
  );
  Register8bit ioc (
    .clock    (clock),
    .reset    (reset),
    .io_in    (_alu_io_sum),
    .io_write (io_iocWrite),
    .io_out   (_ioc_io_out)
  );
  Register8bit out (
    .clock    (clock),
    .reset    (reset),
    .io_in    (_alu_io_sum),
    .io_write (_iou_io_outputWrite[0]),
    .io_out   (io_output1)
  );
  Shifter8bit keyboard_in (
    .clock          (clock),
    .reset          (reset),
    .io_in          (io_KeyboardIn),
    .io_pallelClock (_iou_io_periphralCtr[1]),
    .io_out         (_keyboard_in_io_out)
  );
  Shifter8bit gamepad_in (
    .clock          (clock),
    .reset          (reset),
    .io_in          (io_GamepadIn),
    .io_pallelClock (_iou_io_periphralCtr[0]),
    .io_out         (_gamepad_in_io_out)
  );
  assign io_acc7 = _acc_io_out[7];
endmodule

module CU(
  input  [7:0] io_opCode,
  input        io_acc7,
               io_carry,
  output [1:0] io_dBusAccess,
               io_ramAddrSel,
  output       io_ramWrite,
               io_xWrite,
               io_xInc,
               io_yWrite,
               io_accWrite,
               io_iocWrite,
               io_inputEnble,
               io_outputEnble,
               io_ioCtlEnble,
               io_pcHighWrite,
               io_pcLowWrite,
  output [2:0] io_aluFuct
);

  wire       io_ioCtlEnble_0;
  wire       ioc_ce_instr = io_opCode[7:5] == 3'h6 & io_opCode[1:0] == 2'h1;
  assign io_ioCtlEnble_0 = io_opCode == 8'hD5;
  wire       io_pcHighWrite_0 = (&(io_opCode[7:5])) & io_opCode[4:2] == 3'h0;
  wire [1:0] _io_pcLowWrite_T_2 = {io_carry, io_acc7};
  assign io_dBusAccess =
    ioc_ce_instr
      ? ((&(io_opCode[4:2]))
           ? 2'h3
           : io_opCode[4:2] == 3'h6
               ? 2'h2
               : {1'h0, io_opCode[4:2] != 3'h5 & io_opCode[4:2] != 3'h4})
      : io_opCode[1:0];
  assign io_ramAddrSel =
    (&(io_opCode[7:5]))
      ? 2'h0
      : {io_opCode[4:2] == 3'h2 | io_opCode[4:2] == 3'h3 | (&(io_opCode[4:2])),
         io_opCode[4:2] == 3'h1 | io_opCode[4:2] == 3'h3 | (&(io_opCode[4:2]))};
  assign io_ramWrite = io_opCode[7:5] == 3'h6 & io_opCode[1:0] != 2'h1;
  assign io_xWrite = io_opCode[4:2] == 3'h4 & io_opCode[7:5] != 3'h7 & ~ioc_ce_instr;
  assign io_xInc = (&(io_opCode[4:2])) & io_opCode[7:5] != 3'h7 & ~ioc_ce_instr;
  assign io_yWrite = io_opCode[4:2] == 3'h5 & io_opCode[7:5] != 3'h7 & ~ioc_ce_instr;
  assign io_accWrite = ~(io_opCode[4]) & io_opCode[7:6] != 2'h3;
  assign io_iocWrite = io_opCode[7:5] == 3'h6 & io_opCode[1:0] == 2'h1 & ~io_ioCtlEnble_0;
  assign io_inputEnble = &(io_opCode[1:0]);
  assign io_outputEnble = (&(io_opCode[4:3])) & io_opCode[7:6] != 2'h3;
  assign io_ioCtlEnble = io_ioCtlEnble_0;
  assign io_pcHighWrite = io_pcHighWrite_0;
  assign io_pcLowWrite =
    io_pcHighWrite_0 | (&(io_opCode[7:5])) & _io_pcLowWrite_T_2 != 2'h3
    & (_io_pcLowWrite_T_2 == 2'h2
         ? io_opCode[4]
         : _io_pcLowWrite_T_2 == 2'h1 ? io_opCode[3] : io_opCode[2]);
  assign io_aluFuct = ioc_ce_instr ? 3'h0 : io_opCode[7:5];
endmodule

module Megatron(
  input        clock,
               reset,
               io_GamepadIn,
               io_KeyboardIn,
  output [7:0] io_output1
);

  wire [1:0] _controlUnit_io_dBusAccess;
  wire [1:0] _controlUnit_io_ramAddrSel;
  wire       _controlUnit_io_ramWrite;
  wire       _controlUnit_io_xWrite;
  wire       _controlUnit_io_xInc;
  wire       _controlUnit_io_yWrite;
  wire       _controlUnit_io_accWrite;
  wire       _controlUnit_io_iocWrite;
  wire       _controlUnit_io_inputEnble;
  wire       _controlUnit_io_outputEnble;
  wire       _controlUnit_io_ioCtlEnble;
  wire       _controlUnit_io_pcHighWrite;
  wire       _controlUnit_io_pcLowWrite;
  wire [2:0] _controlUnit_io_aluFuct;
  wire       _datapath_io_acc7;
  wire       _datapath_io_carry;
  wire [7:0] _datapath_io_opCode;
  DataPath datapath (
    .clock          (clock),
    .reset          (reset),
    .io_dBusAccess  (_controlUnit_io_dBusAccess),
    .io_ramAddrSel  (_controlUnit_io_ramAddrSel),
    .io_ramWrite    (_controlUnit_io_ramWrite),
    .io_xWrite      (_controlUnit_io_xWrite),
    .io_xInc        (_controlUnit_io_xInc),
    .io_yWrite      (_controlUnit_io_yWrite),
    .io_accWrite    (_controlUnit_io_accWrite),
    .io_iocWrite    (_controlUnit_io_iocWrite),
    .io_inputEnble  (_controlUnit_io_inputEnble),
    .io_outputEnble (_controlUnit_io_outputEnble),
    .io_ioCtlEnble  (_controlUnit_io_ioCtlEnble),
    .io_pcHighWrite (_controlUnit_io_pcHighWrite),
    .io_pcLowWrite  (_controlUnit_io_pcLowWrite),
    .io_aluFuct     (_controlUnit_io_aluFuct),
    .io_GamepadIn   (io_GamepadIn),
    .io_KeyboardIn  (io_KeyboardIn),
    .io_acc7        (_datapath_io_acc7),
    .io_carry       (_datapath_io_carry),
    .io_opCode      (_datapath_io_opCode),
    .io_output1     (io_output1)
  );
  CU controlUnit (
    .io_opCode      (_datapath_io_opCode),
    .io_acc7        (_datapath_io_acc7),
    .io_carry       (_datapath_io_carry),
    .io_dBusAccess  (_controlUnit_io_dBusAccess),
    .io_ramAddrSel  (_controlUnit_io_ramAddrSel),
    .io_ramWrite    (_controlUnit_io_ramWrite),
    .io_xWrite      (_controlUnit_io_xWrite),
    .io_xInc        (_controlUnit_io_xInc),
    .io_yWrite      (_controlUnit_io_yWrite),
    .io_accWrite    (_controlUnit_io_accWrite),
    .io_iocWrite    (_controlUnit_io_iocWrite),
    .io_inputEnble  (_controlUnit_io_inputEnble),
    .io_outputEnble (_controlUnit_io_outputEnble),
    .io_ioCtlEnble  (_controlUnit_io_ioCtlEnble),
    .io_pcHighWrite (_controlUnit_io_pcHighWrite),
    .io_pcLowWrite  (_controlUnit_io_pcLowWrite),
    .io_aluFuct     (_controlUnit_io_aluFuct)
  );
endmodule

