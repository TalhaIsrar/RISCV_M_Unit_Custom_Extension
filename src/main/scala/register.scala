package register
import m_definitions.MDefinitions._

import chisel3._
import chisel3.util._

class MRegisters extends Module {
  val io = IO(new Bundle {
    // CONTROL INPUTS
    val clk = Input(Clock())
    val resetn = Input(Bool())
    val mux_A = Input(UInt(MUX_A_LENGTH.W))
    val mux_B = Input(UInt(MUX_B_LENGTH.W))
    val sub_neg = Input(Bool())
    val result_signed = Input(Bool())
    val mux_R = Input(UInt(MUX_R_LENGTH.W))
    val mux_D = Input(UInt(MUX_D_LENGTH.W))
    val mux_Z = Input(UInt(MUX_Z_LENGTH.W))
    // DATA INPUTS
    val rs1 = Input(UInt(32.W))
    val rs2 = Input(UInt(32.W))
    val rs1_neg = Input(UInt(32.W))
    val rs2_neg = Input(UInt(32.W))
    val sub_result = Input(UInt(32.W))
    val alu_out = Input(UInt(66.W))
    // DATA OUTPUTS
    val A = Output(SInt(33.W))
    val B = Output(SInt(33.W))
    val R = Output(UInt(32.W))
    val D = Output(UInt(63.W))
    val Z = Output(UInt(32.W))
  })

  // REGISTERS
  val R = RegInit(0.U(32.W))
  val D = RegInit(0.U(63.W))
  val Z = RegInit(0.U(32.W))
  val A = RegInit(0.S(33.W))
  val B = RegInit(0.S(33.W))

  // NEXT STATE VARIABLES
  val next_R = Wire(UInt(32.W))
  val next_D = Wire(UInt(63.W))
  val next_Z = Wire(UInt(32.W))
  val next_A = Wire(SInt(33.W))
  val next_B = Wire(SInt(33.W))

  // DEFAULT ASSIGNMENTS
  next_R := R
  next_D := D
  next_Z := Z
  next_A := Cat(0.U(1.W), R).asSInt
  next_B := Cat(0.U(1.W), D(62, 31)).asSInt

  // MULTIPLEXERS
  switch(io.mux_R) {
    is(MUX_R_KEEP)       { next_R := R }
    is(MUX_R_A)          { next_R := io.rs1 }
    is(MUX_R_A_NEG)      { next_R := io.rs1_neg }
    is(MUX_R_SUB_KEEP)   { next_R := Mux(io.sub_neg, R, io.sub_result) }
    is(MUX_R_MULT_LOWER) { next_R := io.alu_out(31, 0) }
  }

  switch(io.mux_D) {
    is(MUX_D_KEEP)  { next_D := D }
    is(MUX_D_B)     { next_D := Cat(io.rs2, 0.U(31.W)) }
    is(MUX_D_B_NEG) { next_D := Cat(io.rs2_neg, 0.U(31.W)) }
    is(MUX_D_SHR)   { next_D := Cat(0.U(1.W), D(62, 1)) }
    is(MUX_D_Q)     { next_D := Cat(22.U, Q_LOGIC, 27.U) }
  }

  switch(io.mux_Z) {
    is(MUX_Z_KEEP)    { next_Z := Z }
    is(MUX_Z_ZERO)    { next_Z := 0.U }
    is(MUX_Z_SHL_ADD) { next_Z := Cat(Z(30, 0), !io.sub_neg) }
    is(MUX_Z_MULT_UPPER) {
      next_Z := Mux(io.result_signed, Cat(io.alu_out(65), io.alu_out(62, 32)), io.alu_out(63, 32))
    }
  }

  switch(io.mux_A) {
    is(MUX_A_R_UNSIGNED) { next_A := Cat(0.U(1.W), R).asSInt }
    is(MUX_A_R_SIGNED)   { next_A := Cat(R(31), R).asSInt }
    is(MUX_A_ZERO)       { next_A := 0.S }
    is(MUX_A_KEEP)       { next_A := A }
  }

  switch(io.mux_B) {
    is(MUX_B_D_UNSIGNED) { next_B := Cat(0.U(1.W), D(62)).asSInt }
    is(MUX_B_D_SIGNED)   { next_B := Cat(D(62), D(62)).asSInt }
    is(MUX_B_ZERO)       { next_B := 0.S }
    is(MUX_B_KEEP)       { next_B := B }
  }

  // SEQUENTIAL LOGIC
  when(!io.resetn) {
    R := 0.U
    D := 0.U
    Z := 0.U
    A := 0.S
    B := 0.S
  } .otherwise {
    R := next_R
    D := next_D
    Z := next_Z
    A := next_A
    B := next_B
  }

  // OUTPUT ASSIGNMENTS
  io.R := R
  io.D := D
  io.Z := Z
  io.A := A
  io.B := B
}
