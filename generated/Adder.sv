// Generated by CIRCT firtool-1.62.0
module Adder(
  input        clock,
               reset,
  input  [3:0] io_a,
               io_b,
  output [3:0] io_sum
);

  assign io_sum = io_a + io_b;
endmodule

