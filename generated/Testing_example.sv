// Generated by CIRCT firtool-1.62.0
module Testing_example(
  input        clock,
               reset,
  input  [3:0] io_a,
               io_b,
  output [3:0] io_sum,
  output       io_equ
);

  assign io_sum = io_a + io_b;
  assign io_equ = io_a == io_b;
endmodule

