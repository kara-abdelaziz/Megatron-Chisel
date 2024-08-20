// Generated by CIRCT firtool-1.62.0
module SimplerTimer(
  input  clock,
         reset,
         io_load,
         io_select,
  output io_done
);

  reg  [2:0] counterReg;
  wire       io_done_0 = counterReg == 3'h0;
  always @(posedge clock) begin
    if (reset)
      counterReg <= 3'h0;
    else if (io_load)
      counterReg <= io_select ? 3'h3 : 3'h5;
    else if (~io_done_0)
      counterReg <= counterReg - 3'h1;
  end // always @(posedge)
  assign io_done = io_done_0;
endmodule

module SimpleCounter(
  input  clock,
         reset,
         io_load,
         io_decrement,
  output io_done
);

  reg  [3:0] countReg;
  wire       io_done_0 = countReg == 4'h0;
  always @(posedge clock) begin
    if (reset)
      countReg <= 4'h0;
    else if (io_load)
      countReg <= 4'h2;
    else if (~io_done_0 & io_decrement)
      countReg <= countReg - 4'h1;
  end // always @(posedge)
  assign io_done = io_done_0;
endmodule

module FlasherSimpleFSM(
  input  clock,
         reset,
         io_start,
  output io_flash
);

  wire       _counter_io_done;
  wire       _timer_io_done;
  reg  [1:0] stateReg;
  wire       _GEN = stateReg == 2'h0;
  wire       _GEN_0 = stateReg == 2'h1;
  wire       io_flash_0 = ~_GEN & _GEN_0;
  always @(posedge clock) begin
    if (reset)
      stateReg <= 2'h0;
    else if (_GEN) begin
      if (io_start)
        stateReg <= 2'h1;
    end
    else if (_GEN_0) begin
      if (_timer_io_done)
        stateReg <= {~_counter_io_done, 1'h0};
    end
    else if (stateReg == 2'h2 & _timer_io_done)
      stateReg <= 2'h1;
  end // always @(posedge)
  SimplerTimer timer (
    .clock     (clock),
    .reset     (reset),
    .io_load   (_GEN | _timer_io_done),
    .io_select (io_flash_0),
    .io_done   (_timer_io_done)
  );
  SimpleCounter counter (
    .clock        (clock),
    .reset        (reset),
    .io_load      (_GEN),
    .io_decrement (~_GEN & _GEN_0 & _timer_io_done & ~_counter_io_done),
    .io_done      (_counter_io_done)
  );
  assign io_flash = io_flash_0;
endmodule
