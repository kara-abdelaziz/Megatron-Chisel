// Generated by CIRCT firtool-1.62.0
module MinimumVector(
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

  wire       _io_minValue_T = io_inputs_6 < io_inputs_7;
  wire [7:0] _io_minValue_T_1_input = _io_minValue_T ? io_inputs_6 : io_inputs_7;
  wire       _io_minValue_T_2 = io_inputs_8 < io_inputs_9;
  wire [7:0] _io_minValue_T_3_input = _io_minValue_T_2 ? io_inputs_8 : io_inputs_9;
  wire       _io_minValue_T_4 = io_inputs_0 < io_inputs_1;
  wire [7:0] _io_minValue_T_5_input = _io_minValue_T_4 ? io_inputs_0 : io_inputs_1;
  wire       _io_minValue_T_6 = io_inputs_2 < io_inputs_3;
  wire [7:0] _io_minValue_T_7_input = _io_minValue_T_6 ? io_inputs_2 : io_inputs_3;
  wire       _io_minValue_T_8 = io_inputs_4 < io_inputs_5;
  wire [7:0] _io_minValue_T_9_input = _io_minValue_T_8 ? io_inputs_4 : io_inputs_5;
  wire       _io_minValue_T_10 = _io_minValue_T_1_input < _io_minValue_T_3_input;
  wire [7:0] _io_minValue_T_11_input =
    _io_minValue_T_10 ? _io_minValue_T_1_input : _io_minValue_T_3_input;
  wire       _io_minValue_T_12 = _io_minValue_T_5_input < _io_minValue_T_7_input;
  wire [7:0] _io_minValue_T_13_input =
    _io_minValue_T_12 ? _io_minValue_T_5_input : _io_minValue_T_7_input;
  wire       _io_minValue_T_14 = _io_minValue_T_9_input < _io_minValue_T_11_input;
  wire [7:0] _io_minValue_T_15_input =
    _io_minValue_T_14 ? _io_minValue_T_9_input : _io_minValue_T_11_input;
  wire       _io_minValue_T_16 = _io_minValue_T_13_input < _io_minValue_T_15_input;
  assign io_minValue_input =
    _io_minValue_T_16 ? _io_minValue_T_13_input : _io_minValue_T_15_input;
  assign io_minValue_index =
    _io_minValue_T_16
      ? (_io_minValue_T_12 ? {7'h0, ~_io_minValue_T_4} : {7'h1, ~_io_minValue_T_6})
      : _io_minValue_T_14
          ? {7'h2, ~_io_minValue_T_8}
          : _io_minValue_T_10 ? {7'h3, ~_io_minValue_T} : {7'h4, ~_io_minValue_T_2};
endmodule

