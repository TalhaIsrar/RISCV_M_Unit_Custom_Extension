import chisel3._
import chisel3.util._

import m_definitions.MDefinitions

class MAlu extends Module {
  val io = IO(new Bundle {
    // Control Inputs
    val mux_div_rem = Input(UInt(2.W))
    val mux_aluout = Input(UInt(2.W))
    
    // Data Inputs
    val R = Input(UInt(32.W)) // remainder
    val D = Input(UInt(63.W)) // divisor
    val Z = Input(UInt(32.W)) // quotient
    val A = Input(SInt(33.W))
    val B = Input(SInt(33.W))
    
    // Control Outputs
    val sub_neg = Output(Bool())
    
    // Data Outputs
    val sub_result = Output(UInt(32.W))
    val div_rem = Output(UInt(32.W))
    val div_rem_neg = Output(UInt(32.W))
    val alu_out = Output(SInt(66.W))
  })

  // Subtractor for division
  val sub_a = Cat(0.U(31.W), io.R).asUInt  // Extend R to 63 bits
  val sub_b = io.D
  val sub_result_sign = (sub_a - sub_b).asSInt
  io.sub_result := sub_result_sign(31, 0).asUInt
  io.sub_neg := sub_result_sign(63)

  // ALU / Multiplier
  val is_sub = io.mux_aluout === 1.U // Assuming `MUX_ALUOUT_SUBTR` is 1
  val sum_a = Cat(io.A(32), io.A(14, 0), is_sub).asSInt
  val sum_b = Cat(io.B(32), io.B(14, 0), 0.U(1.W)).asSInt
  val sum = Wire(SInt(18.W))
  sum := sum_a + Mux(is_sub, ~sum_b, sum_b)
  sum := sum | 1.S // Set bit 0 to 1

  // Additional Sum/Subtraction
  val Q_LOGIC = 0.U(14.W) // Placeholder for Q_LOGIC
  val sum_Q = Mux(is_sub, Cat(2.U(2.W), Q_LOGIC), Cat(3.U(2.W), ~Q_LOGIC)).asSInt
  val sum_corrected = sum + sum_Q

  // Multiplication
  val product = io.A * io.B

  // Output Selection
  val sum_greater_Q = sum(17, 1) >= Cat(3.U(3.W), Q_LOGIC)
  val sub_custom_neg = sum(17)

  io.alu_out := MuxLookup(io.mux_aluout, product, Seq(
    0.U -> product, // MUX_ALUOUT_MULT
    1.U -> Mux(sum_greater_Q, sum_corrected(18, 1).asSInt, sum(17, 1).asSInt), // MUX_ALUOUT_ADDER
    2.U -> Mux(sub_custom_neg, sum_corrected(18, 1).asSInt, sum(17, 1).asSInt)  // MUX_ALUOUT_SUBTR
  ))

  // Division / Remainder Selection
  io.div_rem := Mux(io.mux_div_rem === 0.U, io.R, io.Z)
  io.div_rem_neg := (-io.div_rem.asSInt).asUInt
}
