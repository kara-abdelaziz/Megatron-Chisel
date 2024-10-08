// Generated by CIRCT firtool-1.62.0
module MinimumVectorWithTuples(
  input        clock,
               reset,
  input  [7:0] io_inputs_0,
               io_inputs_1,
               io_inputs_2,
               io_inputs_3,
               io_inputs_4,
               io_inputs_5,
               io_inputs_6,
               io_inputs_7,
               io_inputs_8,
               io_inputs_9,
  output [7:0] io_minValue_input,
               io_minValue_index
);

  wire       _GEN = io_inputs_0 < io_inputs_1;
  wire [7:0] _GEN_0 = _GEN ? io_inputs_0 : io_inputs_1;
  wire       _GEN_1 = _GEN_0 < io_inputs_2;
  wire [7:0] _GEN_2 = _GEN_1 ? _GEN_0 : io_inputs_2;
  wire       _GEN_3 = _GEN_2 < io_inputs_3;
  wire [7:0] _GEN_4 = _GEN_3 ? _GEN_2 : io_inputs_3;
  wire       _GEN_5 = _GEN_4 < io_inputs_4;
  wire [7:0] _GEN_6 = _GEN_5 ? _GEN_4 : io_inputs_4;
  wire       _GEN_7 = _GEN_6 < io_inputs_5;
  wire [7:0] _GEN_8 = _GEN_7 ? _GEN_6 : io_inputs_5;
  wire       _GEN_9 = _GEN_8 < io_inputs_6;
  wire [7:0] _GEN_10 = _GEN_9 ? _GEN_8 : io_inputs_6;
  wire       _GEN_11 = _GEN_10 < io_inputs_7;
  wire [7:0] _GEN_12 = _GEN_11 ? _GEN_10 : io_inputs_7;
  wire       _GEN_13 = _GEN_12 < io_inputs_8;
  wire [7:0] _GEN_14 = _GEN_13 ? _GEN_12 : io_inputs_8;
  wire       _GEN_15 = _GEN_14 < io_inputs_9;
  assign io_minValue_input = _GEN_15 ? _GEN_14 : io_inputs_9;
  assign io_minValue_index =
    {4'h0,
     _GEN_15
       ? (_GEN_13
            ? {1'h0,
               _GEN_11
                 ? (_GEN_9
                      ? (_GEN_7
                           ? (_GEN_5
                                ? {1'h0, _GEN_3 ? (_GEN_1 ? {1'h0, ~_GEN} : 2'h2) : 2'h3}
                                : 3'h4)
                           : 3'h5)
                      : 3'h6)
                 : 3'h7}
            : 4'h8)
       : 4'h9};
endmodule

