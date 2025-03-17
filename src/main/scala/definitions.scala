package m_definitions

import chisel3._
import chisel3.util._

object MDefinitions {
  // INSTRUCTION ANALYSIS
  // Define type to detect M operation type
  type Func3 = UInt

  // Definitions of M operations
  val MUL    : Func3 = "b000".U
  val MULH   : Func3 = "b001".U
  val MULHSU : Func3 = "b010".U
  val MULHU  : Func3 = "b011".U
  val DIV    : Func3 = "b100".U
  val DIVU   : Func3 = "b101".U
  val REM    : Func3 = "b110".U
  val REMU   : Func3 = "b111".U

  // Other fields defining the usage of M extension
  val OPCODE: UInt = "b0110011".U(7.W)
  val FUNC7: UInt = "b0000001".U(7.W)

  // For custom operations
  val Q: Int = 12289
  val Q_LOGIC: UInt = Q.U(14.W)
  val OPCODE_CUSTOM: UInt = "b0001011".U(7.W)

  // Custom function codes
  val ADDMOD: Func3 = "b000".U
  val SUBMOD: Func3 = "b001".U
  val MULQ  : Func3 = "b010".U

  // Functions to extract important parts from instruction
  def getOpcode(ir: UInt): UInt = ir(6, 0)
  def getFunc3(ir: UInt): Func3 = ir(14, 12)
  def getFunc7(ir: UInt): UInt = ir(31, 25)

  def isMult(f3: Func3): Bool = !f3(2)
  def isDiv(f3: Func3): Bool = f3(2) && !f3(1)
  def isRem(f3: Func3): Bool = f3(2) && f3(1)
  def isNegative(value: UInt): Bool = value(31)
  
  // CONTROL SIGNALS
  val MUX_R_LENGTH = 3
  val MUX_R_KEEP       = 0.U(MUX_R_LENGTH.W)
  val MUX_R_A          = 1.U(MUX_R_LENGTH.W)
  val MUX_R_A_NEG      = 2.U(MUX_R_LENGTH.W)
  val MUX_R_SUB_KEEP   = 3.U(MUX_R_LENGTH.W)
  val MUX_R_MULT_LOWER = 4.U(MUX_R_LENGTH.W)

  val MUX_D_LENGTH = 3
  val MUX_D_KEEP  = 0.U(MUX_D_LENGTH.W)
  val MUX_D_B     = 1.U(MUX_D_LENGTH.W)
  val MUX_D_B_NEG = 2.U(MUX_D_LENGTH.W)
  val MUX_D_SHR   = 3.U(MUX_D_LENGTH.W)
  val MUX_D_Q     = 4.U(MUX_D_LENGTH.W)

  val MUX_Z_LENGTH = 2
  val MUX_Z_KEEP       = 0.U(MUX_Z_LENGTH.W)
  val MUX_Z_ZERO       = 1.U(MUX_Z_LENGTH.W)
  val MUX_Z_SHL_ADD    = 2.U(MUX_Z_LENGTH.W)
  val MUX_Z_MULT_UPPER = 3.U(MUX_Z_LENGTH.W)

  val MUX_A_LENGTH = 2
  val MUX_A_R_UNSIGNED = 0.U(MUX_A_LENGTH.W)
  val MUX_A_R_SIGNED   = 1.U(MUX_A_LENGTH.W)
  val MUX_A_ZERO       = 2.U(MUX_A_LENGTH.W)
  val MUX_A_KEEP       = 3.U(MUX_A_LENGTH.W)

  val MUX_B_LENGTH = 2
  val MUX_B_D_UNSIGNED = 0.U(MUX_B_LENGTH.W)
  val MUX_B_D_SIGNED   = 1.U(MUX_B_LENGTH.W)
  val MUX_B_ZERO       = 2.U(MUX_B_LENGTH.W)
  val MUX_B_KEEP       = 3.U(MUX_B_LENGTH.W)

  val MUX_DIV_REM_LENGTH = 1
  val MUX_DIV_REM_R = 0.U(MUX_DIV_REM_LENGTH.W)
  val MUX_DIV_REM_Z = 1.U(MUX_DIV_REM_LENGTH.W)

  val MUX_OUT_LENGTH = 3
  val MUX_OUT_ZERO         = 0.U(MUX_OUT_LENGTH.W)
  val MUX_OUT_DIV_REM      = 1.U(MUX_OUT_LENGTH.W)
  val MUX_OUT_DIV_REM_NEG  = 2.U(MUX_OUT_LENGTH.W)
  val MUX_OUT_ALUOUT_LOWER = 3.U(MUX_OUT_LENGTH.W)
  val MUX_OUT_ALUOUT_UPPER = 4.U(MUX_OUT_LENGTH.W)
  val MUX_OUT_ALL1         = 5.U(MUX_OUT_LENGTH.W)
  val MUX_OUT_MINUS_1      = 6.U(MUX_OUT_LENGTH.W)

  val MUX_ALUOUT_LENGTH = 2
  val MUX_ALUOUT_MULT   = 0.U(MUX_ALUOUT_LENGTH.W)
  val MUX_ALUOUT_ADDER  = 1.U(MUX_ALUOUT_LENGTH.W)
  val MUX_ALUOUT_SUBTR  = 2.U(MUX_ALUOUT_LENGTH.W)
}
