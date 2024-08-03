// Generated by CIRCT firtool-1.62.0
module NerdCounter(
  input  clock,
         reset,
  output io_tick
);

  reg [7:0] countReg;
  always @(posedge clock) begin
    if (reset)
      countReg <= 8'h3E;
    else if (countReg[7])
      countReg <= 8'h3E;
    else
      countReg <= countReg - 8'h1;
  end // always @(posedge)
  assign io_tick = countReg[7];
endmodule

