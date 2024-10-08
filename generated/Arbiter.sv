// Generated by CIRCT firtool-1.62.0
module Arbiter(
  input        clock,
               reset,
  output       io_inputs_0_ready,
  input        io_inputs_0_valid,
  input  [7:0] io_inputs_0_bits,
  output       io_inputs_1_ready,
  input        io_inputs_1_valid,
  input  [7:0] io_inputs_1_bits,
  output       io_inputs_2_ready,
  input        io_inputs_2_valid,
  input  [7:0] io_inputs_2_bits,
  output       io_inputs_3_ready,
  input        io_inputs_3_valid,
  input  [7:0] io_inputs_3_bits,
  input        io_output_ready,
  output       io_output_valid,
  output [7:0] io_output_bits
);

  reg [7:0] io_output_dataReg;
  reg       io_output_emptyReg;
  reg       io_output_readyAReg;
  reg       io_output_readyBReg;
  reg       io_output_balanceRag;
  reg [7:0] io_output_dataReg_1;
  reg       io_output_emptyReg_1;
  reg       io_output_readyAReg_1;
  reg       io_output_readyBReg_1;
  reg       io_output_balanceRag_1;
  reg [7:0] io_output_dataReg_2;
  reg       io_output_emptyReg_2;
  reg       io_output_readyAReg_2;
  reg       io_output_readyBReg_2;
  reg       io_output_balanceRag_2;
  always @(posedge clock) begin
    if (io_output_readyBReg)
      io_output_dataReg <= io_inputs_1_bits;
    else if (io_output_readyAReg)
      io_output_dataReg <= io_inputs_0_bits;
    if (io_output_readyBReg_1)
      io_output_dataReg_1 <= io_inputs_3_bits;
    else if (io_output_readyAReg_1)
      io_output_dataReg_1 <= io_inputs_2_bits;
    if (io_output_readyBReg_2)
      io_output_dataReg_2 <= io_output_dataReg_1;
    else if (io_output_readyAReg_2)
      io_output_dataReg_2 <= io_output_dataReg;
    if (reset) begin
      io_output_emptyReg <= 1'h1;
      io_output_readyAReg <= 1'h0;
      io_output_readyBReg <= 1'h0;
      io_output_balanceRag <= 1'h0;
      io_output_emptyReg_1 <= 1'h1;
      io_output_readyAReg_1 <= 1'h0;
      io_output_readyBReg_1 <= 1'h0;
      io_output_balanceRag_1 <= 1'h0;
      io_output_emptyReg_2 <= 1'h1;
      io_output_readyAReg_2 <= 1'h0;
      io_output_readyBReg_2 <= 1'h0;
      io_output_balanceRag_2 <= 1'h0;
    end
    else begin
      automatic logic _io_output_T_4 =
        io_inputs_0_valid & io_inputs_1_valid & ~io_output_readyAReg
        & ~io_output_readyBReg;
      automatic logic _io_output_T_6 = io_inputs_0_valid & ~io_output_readyBReg;
      automatic logic _io_output_T_14 =
        io_inputs_2_valid & io_inputs_3_valid & ~io_output_readyAReg_1
        & ~io_output_readyBReg_1;
      automatic logic _io_output_T_16 = io_inputs_2_valid & ~io_output_readyBReg_1;
      automatic logic _io_output_T_24 =
        ~io_output_emptyReg & ~io_output_emptyReg_1 & ~io_output_readyAReg_2
        & ~io_output_readyBReg_2;
      automatic logic _io_output_T_26 = ~io_output_emptyReg & ~io_output_readyBReg_2;
      io_output_emptyReg <=
        io_output_readyAReg_2 | ~(io_output_readyBReg | io_output_readyAReg)
        & io_output_emptyReg;
      io_output_readyAReg <=
        ~io_output_readyAReg
        & (io_output_emptyReg
             ? (_io_output_T_4
                  ? io_output_balanceRag | io_output_readyAReg
                  : _io_output_T_6 | io_output_readyAReg)
             : io_output_readyAReg);
      io_output_readyBReg <=
        ~io_output_readyBReg
        & (io_output_emptyReg
             ? (_io_output_T_4
                  ? ~io_output_balanceRag | io_output_readyBReg
                  : ~_io_output_T_6 & io_inputs_1_valid & ~io_output_readyAReg
                    | io_output_readyBReg)
             : io_output_readyBReg);
      io_output_balanceRag <= io_output_emptyReg & _io_output_T_4 ^ io_output_balanceRag;
      io_output_emptyReg_1 <=
        io_output_readyBReg_2 | ~(io_output_readyBReg_1 | io_output_readyAReg_1)
        & io_output_emptyReg_1;
      io_output_readyAReg_1 <=
        ~io_output_readyAReg_1
        & (io_output_emptyReg_1
             ? (_io_output_T_14
                  ? io_output_balanceRag_1 | io_output_readyAReg_1
                  : _io_output_T_16 | io_output_readyAReg_1)
             : io_output_readyAReg_1);
      io_output_readyBReg_1 <=
        ~io_output_readyBReg_1
        & (io_output_emptyReg_1
             ? (_io_output_T_14
                  ? ~io_output_balanceRag_1 | io_output_readyBReg_1
                  : ~_io_output_T_16 & io_inputs_3_valid & ~io_output_readyAReg_1
                    | io_output_readyBReg_1)
             : io_output_readyBReg_1);
      io_output_balanceRag_1 <=
        io_output_emptyReg_1 & _io_output_T_14 ^ io_output_balanceRag_1;
      io_output_emptyReg_2 <=
        io_output_ready | ~(io_output_readyBReg_2 | io_output_readyAReg_2)
        & io_output_emptyReg_2;
      io_output_readyAReg_2 <=
        ~io_output_readyAReg_2
        & (io_output_emptyReg_2
             ? (_io_output_T_24
                  ? io_output_balanceRag_2 | io_output_readyAReg_2
                  : _io_output_T_26 | io_output_readyAReg_2)
             : io_output_readyAReg_2);
      io_output_readyBReg_2 <=
        ~io_output_readyBReg_2
        & (io_output_emptyReg_2
             ? (_io_output_T_24
                  ? ~io_output_balanceRag_2 | io_output_readyBReg_2
                  : ~_io_output_T_26 & ~io_output_emptyReg_1 & ~io_output_readyAReg_2
                    | io_output_readyBReg_2)
             : io_output_readyBReg_2);
      io_output_balanceRag_2 <=
        io_output_emptyReg_2 & _io_output_T_24 ^ io_output_balanceRag_2;
    end
  end // always @(posedge)
  assign io_inputs_0_ready = io_output_readyAReg;
  assign io_inputs_1_ready = io_output_readyBReg;
  assign io_inputs_2_ready = io_output_readyAReg_1;
  assign io_inputs_3_ready = io_output_readyBReg_1;
  assign io_output_valid = ~io_output_emptyReg_2;
  assign io_output_bits = io_output_dataReg_2;
endmodule

