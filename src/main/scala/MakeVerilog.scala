// File created on 18/10/2022 by Tobias Jauch (@tojauch)

package makeverilog

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

import register._


object Verilog_Gen extends App {
  emitVerilog(new MRegisters(), Array("--target-dir", "generated-src"))
}
