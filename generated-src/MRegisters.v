module MRegisters(
  input         clock,
  input         reset,
  input         io_clk,
  input         io_resetn,
  input  [1:0]  io_mux_A,
  input  [1:0]  io_mux_B,
  input         io_sub_neg,
  input         io_result_signed,
  input  [2:0]  io_mux_R,
  input  [2:0]  io_mux_D,
  input  [1:0]  io_mux_Z,
  input  [31:0] io_rs1,
  input  [31:0] io_rs2,
  input  [31:0] io_rs1_neg,
  input  [31:0] io_rs2_neg,
  input  [31:0] io_sub_result,
  input  [65:0] io_alu_out,
  output [32:0] io_A,
  output [32:0] io_B,
  output [31:0] io_R,
  output [62:0] io_D,
  output [31:0] io_Z
);
`ifdef RANDOMIZE_REG_INIT
  reg [31:0] _RAND_0;
  reg [63:0] _RAND_1;
  reg [31:0] _RAND_2;
  reg [63:0] _RAND_3;
  reg [63:0] _RAND_4;
`endif // RANDOMIZE_REG_INIT
  reg [31:0] R; // @[register.scala 35:18]
  reg [62:0] D; // @[register.scala 36:18]
  reg [31:0] Z; // @[register.scala 37:18]
  reg [32:0] A; // @[register.scala 38:18]
  reg [32:0] B; // @[register.scala 39:18]
  wire [32:0] _next_A_T_1 = {1'h0,R}; // @[register.scala 52:30]
  wire [32:0] _next_B_T_2 = {1'h0,D[62:31]}; // @[register.scala 53:38]
  wire [31:0] _next_R_T = io_sub_neg ? R : io_sub_result; // @[register.scala 60:41]
  wire [31:0] _GEN_0 = 3'h4 == io_mux_R ? io_alu_out[31:0] : R; // @[register.scala 49:10 56:20 61:35]
  wire [31:0] _GEN_1 = 3'h3 == io_mux_R ? _next_R_T : _GEN_0; // @[register.scala 56:20 60:35]
  wire [31:0] _GEN_2 = 3'h2 == io_mux_R ? io_rs1_neg : _GEN_1; // @[register.scala 56:20 59:35]
  wire [62:0] _next_D_T = {io_rs2,31'h0}; // @[Cat.scala 31:58]
  wire [62:0] _next_D_T_1 = {io_rs2_neg,31'h0}; // @[Cat.scala 31:58]
  wire [62:0] _next_D_T_3 = {1'h0,D[62:1]}; // @[Cat.scala 31:58]
  wire [62:0] _GEN_5 = 3'h4 == io_mux_D ? 63'hb6003b : D; // @[register.scala 50:10 64:20 69:30]
  wire [62:0] _GEN_6 = 3'h3 == io_mux_D ? _next_D_T_3 : _GEN_5; // @[register.scala 64:20 68:30]
  wire [62:0] _GEN_7 = 3'h2 == io_mux_D ? _next_D_T_1 : _GEN_6; // @[register.scala 64:20 67:30]
  wire  _next_Z_T_1 = ~io_sub_neg; // @[register.scala 75:49]
  wire [31:0] _next_Z_T_2 = {Z[30:0],_next_Z_T_1}; // @[Cat.scala 31:58]
  wire [31:0] _next_Z_T_5 = {io_alu_out[65],io_alu_out[62:32]}; // @[Cat.scala 31:58]
  wire [31:0] _next_Z_T_7 = io_result_signed ? _next_Z_T_5 : io_alu_out[63:32]; // @[register.scala 77:20]
  wire [31:0] _GEN_10 = 2'h3 == io_mux_Z ? _next_Z_T_7 : Z; // @[register.scala 51:10 72:20 77:14]
  wire [31:0] _GEN_11 = 2'h2 == io_mux_Z ? _next_Z_T_2 : _GEN_10; // @[register.scala 72:20 75:32]
  wire [32:0] _next_A_T_6 = {R[31],R}; // @[register.scala 83:52]
  wire [32:0] _GEN_14 = 2'h3 == io_mux_A ? $signed(A) : $signed(_next_A_T_1); // @[register.scala 52:10 81:20 85:35]
  wire [32:0] _GEN_15 = 2'h2 == io_mux_A ? $signed(33'sh0) : $signed(_GEN_14); // @[register.scala 81:20 84:35]
  wire [1:0] _next_B_T_5 = {1'h0,D[62]}; // @[register.scala 89:59]
  wire [1:0] _next_B_T_9 = {D[62],D[62]}; // @[register.scala 90:56]
  wire [32:0] _GEN_18 = 2'h3 == io_mux_B ? $signed(B) : $signed(_next_B_T_2); // @[register.scala 53:10 88:20 92:35]
  wire [32:0] _GEN_19 = 2'h2 == io_mux_B ? $signed(33'sh0) : $signed(_GEN_18); // @[register.scala 88:20 91:35]
  assign io_A = A; // @[register.scala 114:8]
  assign io_B = B; // @[register.scala 115:8]
  assign io_R = R; // @[register.scala 111:8]
  assign io_D = D; // @[register.scala 112:8]
  assign io_Z = Z; // @[register.scala 113:8]
  always @(posedge clock) begin
    if (reset) begin // @[register.scala 35:18]
      R <= 32'h0; // @[register.scala 35:18]
    end else if (~io_resetn) begin // @[register.scala 96:20]
      R <= 32'h0; // @[register.scala 97:7]
    end else if (!(3'h0 == io_mux_R)) begin // @[register.scala 56:20]
      if (3'h1 == io_mux_R) begin // @[register.scala 56:20]
        R <= io_rs1; // @[register.scala 58:35]
      end else begin
        R <= _GEN_2;
      end
    end
    if (reset) begin // @[register.scala 36:18]
      D <= 63'h0; // @[register.scala 36:18]
    end else if (~io_resetn) begin // @[register.scala 96:20]
      D <= 63'h0; // @[register.scala 98:7]
    end else if (!(3'h0 == io_mux_D)) begin // @[register.scala 64:20]
      if (3'h1 == io_mux_D) begin // @[register.scala 64:20]
        D <= _next_D_T; // @[register.scala 66:30]
      end else begin
        D <= _GEN_7;
      end
    end
    if (reset) begin // @[register.scala 37:18]
      Z <= 32'h0; // @[register.scala 37:18]
    end else if (~io_resetn) begin // @[register.scala 96:20]
      Z <= 32'h0; // @[register.scala 99:7]
    end else if (!(2'h0 == io_mux_Z)) begin // @[register.scala 72:20]
      if (2'h1 == io_mux_Z) begin // @[register.scala 72:20]
        Z <= 32'h0; // @[register.scala 74:32]
      end else begin
        Z <= _GEN_11;
      end
    end
    if (reset) begin // @[register.scala 38:18]
      A <= 33'sh0; // @[register.scala 38:18]
    end else if (~io_resetn) begin // @[register.scala 96:20]
      A <= 33'sh0; // @[register.scala 100:7]
    end else if (2'h0 == io_mux_A) begin // @[register.scala 81:20]
      A <= _next_A_T_1; // @[register.scala 82:35]
    end else if (2'h1 == io_mux_A) begin // @[register.scala 81:20]
      A <= _next_A_T_6; // @[register.scala 83:35]
    end else begin
      A <= _GEN_15;
    end
    if (reset) begin // @[register.scala 39:18]
      B <= 33'sh0; // @[register.scala 39:18]
    end else if (~io_resetn) begin // @[register.scala 96:20]
      B <= 33'sh0; // @[register.scala 101:7]
    end else if (2'h0 == io_mux_B) begin // @[register.scala 88:20]
      B <= {{31{_next_B_T_5[1]}},_next_B_T_5}; // @[register.scala 89:35]
    end else if (2'h1 == io_mux_B) begin // @[register.scala 88:20]
      B <= {{31{_next_B_T_9[1]}},_next_B_T_9}; // @[register.scala 90:35]
    end else begin
      B <= _GEN_19;
    end
  end
// Register and memory initialization
`ifdef RANDOMIZE_GARBAGE_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_INVALID_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_REG_INIT
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_MEM_INIT
`define RANDOMIZE
`endif
`ifndef RANDOM
`define RANDOM $random
`endif
`ifdef RANDOMIZE_MEM_INIT
  integer initvar;
`endif
`ifndef SYNTHESIS
`ifdef FIRRTL_BEFORE_INITIAL
`FIRRTL_BEFORE_INITIAL
`endif
initial begin
  `ifdef RANDOMIZE
    `ifdef INIT_RANDOM
      `INIT_RANDOM
    `endif
    `ifndef VERILATOR
      `ifdef RANDOMIZE_DELAY
        #`RANDOMIZE_DELAY begin end
      `else
        #0.002 begin end
      `endif
    `endif
`ifdef RANDOMIZE_REG_INIT
  _RAND_0 = {1{`RANDOM}};
  R = _RAND_0[31:0];
  _RAND_1 = {2{`RANDOM}};
  D = _RAND_1[62:0];
  _RAND_2 = {1{`RANDOM}};
  Z = _RAND_2[31:0];
  _RAND_3 = {2{`RANDOM}};
  A = _RAND_3[32:0];
  _RAND_4 = {2{`RANDOM}};
  B = _RAND_4[32:0];
`endif // RANDOMIZE_REG_INIT
  `endif // RANDOMIZE
end // initial
`ifdef FIRRTL_AFTER_INITIAL
`FIRRTL_AFTER_INITIAL
`endif
`endif // SYNTHESIS
endmodule
