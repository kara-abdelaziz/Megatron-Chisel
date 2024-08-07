// Generated by CIRCT firtool-1.62.0
module SIPO(
  input        clock,
               reset,
               io_serIn,
  output [3:0] io_parOut
);

  reg [3:0] shiftReg;
  always @(posedge clock) begin
    if (reset)
      shiftReg <= 4'h0;
    else
      shiftReg <= {shiftReg[2:0], io_serIn};
  end // always @(posedge)
  assign io_parOut = shiftReg;
endmodule

