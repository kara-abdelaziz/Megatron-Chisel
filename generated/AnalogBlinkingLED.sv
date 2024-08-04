// Generated by CIRCT firtool-1.62.0
module AnalogBlinkingLED(
  input  clock,
         reset,
  output io_sig
);

  reg [31:0] modulationReg;
  reg        updownReg;
  reg [3:0]  io_sig_countReg;
  always @(posedge clock) begin
    if (reset) begin
      modulationReg <= 32'h0;
      updownReg <= 1'h1;
      io_sig_countReg <= 4'h0;
    end
    else begin
      if (modulationReg < 32'hA & updownReg)
        modulationReg <= modulationReg + 32'h1;
      else begin
        automatic logic _GEN = modulationReg == 32'hA;
        automatic logic _GEN_0 = ~updownReg & (|modulationReg);
        if (_GEN | ~_GEN_0) begin
        end
        else
          modulationReg <= modulationReg - 32'h1;
        updownReg <= ~_GEN & (~_GEN_0 | updownReg);
      end
      if (io_sig_countReg == 4'hA)
        io_sig_countReg <= 4'h0;
      else
        io_sig_countReg <= io_sig_countReg + 4'h1;
    end
  end // always @(posedge)
  assign io_sig = {28'h0, io_sig_countReg} < modulationReg;
endmodule

