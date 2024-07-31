// Generated by CIRCT firtool-1.62.0
// Standard header to adapt well known macros for prints and assertions.

// Users can define 'PRINTF_COND' to add an extra gate to prints.
`ifndef PRINTF_COND_
  `ifdef PRINTF_COND
    `define PRINTF_COND_ (`PRINTF_COND)
  `else  // PRINTF_COND
    `define PRINTF_COND_ 1
  `endif // PRINTF_COND
`endif // not def PRINTF_COND_

module Decoder(
  input  clock,
         reset,
         io_i0,
         io_i1,
  output io_o0,
         io_o1,
         io_o2,
         io_o3
);

  wire [1:0] sel = {io_i1, io_i0};
  wire       io_o0_0 = sel == 2'h0;
  wire       _GEN = sel == 2'h1;
  wire       io_o1_0 = ~io_o0_0 & _GEN;
  wire       _GEN_0 = sel == 2'h2;
  wire       io_o2_0 = ~(io_o0_0 | _GEN) & _GEN_0;
  wire       io_o3_0 = ~(io_o0_0 | _GEN | _GEN_0) & (&sel);
  `ifndef SYNTHESIS
    always @(posedge clock) begin
      if ((`PRINTF_COND_) & ~reset)
        $fwrite(32'h80000002, "Decoder output = %d %d %d %d\n", io_o0_0, io_o1_0, io_o2_0,
                io_o3_0);
    end // always @(posedge)
  `endif // not def SYNTHESIS
  assign io_o0 = io_o0_0;
  assign io_o1 = io_o1_0;
  assign io_o2 = io_o2_0;
  assign io_o3 = io_o3_0;
endmodule

