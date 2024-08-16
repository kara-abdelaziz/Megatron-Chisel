// Generated by CIRCT firtool-1.62.0
module FileReader(
  input        clock,
               reset,
  input  [2:0] io_input,
  output [7:0] io_output
);

  wire [7:0][7:0] _GEN = '{8'h25, 8'h10, 8'h5F, 8'h2D, 8'h24, 8'h4E, 8'h32, 8'hC};
  assign io_output = _GEN[io_input];
endmodule

