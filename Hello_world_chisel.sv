// Generated by CIRCT firtool-1.62.0
module Hello_world_chisel(
  input  clock,
         reset,
  output io_led
);

  reg [31:0] count_reg;
  reg        led_output;
  always @(posedge clock) begin
    if (reset) begin
      count_reg <= 32'h0;
      led_output <= 1'h1;
    end
    else begin
      automatic logic _GEN = count_reg < 32'h989680;
      automatic logic _GEN_0;
      _GEN_0 = count_reg < 32'h2FAF080;
      if (_GEN)
        count_reg <= count_reg + 32'h1;
      else if (_GEN_0)
        count_reg <= count_reg + 32'h1;
      else
        count_reg <= 32'h0;
      led_output <= _GEN | ~_GEN_0 & led_output;
    end
  end // always @(posedge)
  assign io_led = led_output;
endmodule
