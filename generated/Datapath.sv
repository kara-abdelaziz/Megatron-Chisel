// Generated by CIRCT firtool-1.62.0
module Datapath(
  input        clock,
               reset,
               io_loadData,
  input  [7:0] io_inData,
  output       io_countReady,
  output [3:0] io_outData
);

  reg [7:0] shifterReg;
  reg [3:0] popCountReg;
  reg [3:0] counterReg;
  always @(posedge clock) begin
    if (reset) begin
      shifterReg <= 8'h0;
      popCountReg <= 4'h0;
      counterReg <= 4'h8;
    end
    else if (io_loadData) begin
      shifterReg <= io_inData;
      popCountReg <= 4'h0;
      counterReg <= 4'h8;
    end
    else begin
      shifterReg <= {1'h0, shifterReg[7:1]};
      popCountReg <= popCountReg + {3'h0, shifterReg[0]};
      if (|counterReg)
        counterReg <= counterReg - 4'h1;
    end
  end // always @(posedge)
  assign io_countReady = ~(|counterReg);
  assign io_outData = popCountReg;
endmodule

