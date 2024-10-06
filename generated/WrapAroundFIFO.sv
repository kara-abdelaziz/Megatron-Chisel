// Generated by CIRCT firtool-1.62.0
// VCS coverage exclude_file
module mem_8x8(
  input  [2:0] R0_addr,
  input        R0_en,
               R0_clk,
  output [7:0] R0_data,
  input  [2:0] R1_addr,
  input        R1_en,
               R1_clk,
  output [7:0] R1_data,
  input  [2:0] W0_addr,
  input        W0_en,
               W0_clk,
  input  [7:0] W0_data
);

  reg [7:0] Memory[0:7];
  reg       _R0_en_d0;
  reg [2:0] _R0_addr_d0;
  always @(posedge R0_clk) begin
    _R0_en_d0 <= R0_en;
    _R0_addr_d0 <= R0_addr;
  end // always @(posedge)
  reg       _R1_en_d0;
  reg [2:0] _R1_addr_d0;
  always @(posedge R1_clk) begin
    _R1_en_d0 <= R1_en;
    _R1_addr_d0 <= R1_addr;
  end // always @(posedge)
  always @(posedge W0_clk) begin
    if (W0_en & 1'h1)
      Memory[W0_addr] <= W0_data;
  end // always @(posedge)
  assign R0_data = _R0_en_d0 ? Memory[_R0_addr_d0] : 8'bx;
  assign R1_data = _R1_en_d0 ? Memory[_R1_addr_d0] : 8'bx;
endmodule

module WrapAroundFIFO(
  input        clock,
               reset,
  output       io_in_ready,
  input        io_in_valid,
  input  [7:0] io_in_bits,
  input        io_out_ready,
  output       io_out_valid,
  output [7:0] io_out_bits
);

  wire [7:0] _mem_ext_R0_data;
  wire [7:0] _mem_ext_R1_data;
  reg  [2:0] enqPtrReg;
  reg  [2:0] deqPtrReg;
  reg  [2:0] countReg;
  reg  [7:0] outputReg;
  wire       _GEN = (|countReg) & io_out_ready;
  always @(posedge clock) begin
    if (reset) begin
      enqPtrReg <= 3'h0;
      deqPtrReg <= 3'h0;
      countReg <= 3'h0;
    end
    else begin
      if (io_in_valid) begin
        automatic logic [3:0] _enqPtrReg_T_2 = {1'h0, enqPtrReg + 3'h1} % 4'h8;
        enqPtrReg <= _enqPtrReg_T_2[2:0];
      end
      if (_GEN) begin
        automatic logic [3:0] _deqPtrReg_T_2 = {1'h0, deqPtrReg + 3'h1} % 4'h8;
        deqPtrReg <= _deqPtrReg_T_2[2:0];
        countReg <= countReg - 3'h1;
      end
      else if (io_in_valid)
        countReg <= countReg + 3'h1;
    end
    outputReg <= _GEN ? _mem_ext_R0_data : _mem_ext_R1_data;
  end // always @(posedge)
  mem_8x8 mem_ext (
    .R0_addr (deqPtrReg),
    .R0_en   (_GEN),
    .R0_clk  (clock),
    .R0_data (_mem_ext_R0_data),
    .R1_addr (deqPtrReg),
    .R1_en   (1'h1),
    .R1_clk  (clock),
    .R1_data (_mem_ext_R1_data),
    .W0_addr (enqPtrReg),
    .W0_en   (io_in_valid),
    .W0_clk  (clock),
    .W0_data (io_in_bits)
  );
  assign io_in_ready = 1'h1;
  assign io_out_valid = |countReg;
  assign io_out_bits = outputReg;
endmodule
