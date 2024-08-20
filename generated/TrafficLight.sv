// Generated by CIRCT firtool-1.62.0
module TrafficLight(
  input        clock,
               reset,
               io_detect1,
               io_detect2,
  output [2:0] io_roadPrio,
               io_roadScnd
);

  reg  [1:0] stateReg;
  wire       _GEN = stateReg == 2'h0;
  wire       _GEN_0 = stateReg == 2'h1;
  wire       _GEN_1 = stateReg == 2'h2;
  always @(posedge clock) begin
    if (reset)
      stateReg <= 2'h0;
    else begin
      automatic logic [3:0][1:0] _GEN_2 =
        {{2'h0},
         {io_detect1 | io_detect2 ? stateReg : 2'h3},
         {2'h2},
         {io_detect1 | io_detect2 ? 2'h1 : stateReg}};
      stateReg <= _GEN_2[stateReg];
    end
  end // always @(posedge)
  assign io_roadPrio = _GEN ? 3'h1 : _GEN_0 ? 3'h2 : {_GEN_1 | (&stateReg), 2'h0};
  assign io_roadScnd = _GEN | _GEN_0 ? 3'h4 : _GEN_1 ? 3'h1 : {1'h0, &stateReg, 1'h0};
endmodule
