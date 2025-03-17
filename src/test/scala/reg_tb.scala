package register
import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
import m_definitions.MDefinitions._

class RegistersTest extends AnyFlatSpec with ChiselScalatestTester {

  "register" should "work" in {
    test(new MRegisters).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>

      // Initialize signals
      dut.clock.setTimeout(100)
      dut.io.mux_A.poke(0.U)
      dut.io.mux_B.poke(0.U)
      dut.io.mux_R.poke(0.U)
      dut.io.mux_D.poke(0.U)
      dut.io.mux_Z.poke(0.U)
      dut.io.rs1.poke(0.U)
      dut.io.rs2.poke(0.U)
      dut.io.sub_result.poke(0.U)
      dut.io.product.poke(0.U)
      dut.io.resetn.poke(1.B)
      dut.clock.step(1)
      dut.io.resetn.poke(0.B)
      dut.clock.step(1)
      dut.io.resetn.poke(1.B)
      dut.clock.step(1)
      
      // Test R
      dut.io.mux_R.poke(MUX_R_A.U)
      dut.io.rs1.poke(789.U)
      dut.clock.step(1)
      dut.io.R.expect(789.U)
      
      dut.io.mux_R.poke(MUX_R_A_NEG.U)
      dut.io.rs1.poke((-7890).S.asUInt)
      dut.clock.step(1)
      dut.io.R.expect((-(-7890).S).asUInt)
      
      dut.io.mux_R.poke(MUX_R_KEEP.U)
      dut.clock.step(1)
      dut.io.R.expect((-(-7890).S).asUInt)
      
      // Test D
      dut.io.mux_D.poke(MUX_D_B.U)
      dut.io.rs2.poke(456.U)
      dut.clock.step(1)
      dut.io.D.expect((456.U << 31).asUInt)
      
      dut.io.mux_D.poke(MUX_D_KEEP.U)
      dut.clock.step(1)
      dut.io.D.expect((456.U << 31).asUInt)
      
      dut.io.mux_D.poke(MUX_D_B_NEG.U)
      dut.io.rs2.poke((-4567).S.asUInt)
      dut.clock.step(1)
      dut.io.D.expect((-(-4567).S << 31).asUInt)
      
      dut.io.mux_D.poke(MUX_D_SHR.U)
      dut.clock.step(1)
      dut.io.D.expect((-(-4567).S << 30).asUInt)
      
      // Test Z
      dut.io.mux_Z.poke(MUX_Z_ZERO.U)
      dut.clock.step(1)
      dut.io.Z.expect(0.U)
      
      dut.io.mux_Z.poke(MUX_Z_SHL_ADD.U)
      dut.io.sub_result.poke(123.U)
      dut.clock.step(1)
      dut.io.Z.expect((1.U << 1) + 1.U)

    }
  } 
}
