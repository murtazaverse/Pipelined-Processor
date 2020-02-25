package pipline
import chisel3._
import chisel3.util.Cat
class Branchlogic extends Module{
    val io=IO(new Bundle{
        val insn_mem_14_12=Input(UInt(3.W))
        val readdata1=Input(SInt(32.W))
        val readdata2=Input(SInt(32.W))
        val branch_out=Output(UInt(1.W))
    })
    //beq
    when(io.insn_mem_14_12==="b000".U)
    {
        when(io.readdata1 === io.readdata2) 
        {
            io.branch_out := 1.U
        } 
        .otherwise 
        {
            io.branch_out := 0.U
        }
    }
    //blt
    .elsewhen(io.insn_mem_14_12==="b100".U)
    {
        when(io.readdata1 < io.readdata2)
        {
         io.branch_out := 1.U
        } 
        .otherwise 
        {
         io.branch_out := 0.U
        }
    }
    //bge
    .elsewhen(io.insn_mem_14_12==="b101".U)
    {
        when(io.readdata1>=io.readdata2)
        {
          io.branch_out := 1.U
        }
        .otherwise
        {
          io.branch_out := 0.U
        }
    }
    //bne
    .elsewhen(io.insn_mem_14_12==="b001".U)
    {
        when(io.readdata1=/=io.readdata2)
        {
         io.branch_out := 1.U
        }
        .otherwise 
        {
         io.branch_out := 0.U
        }
    }
    .otherwise
    {
        io.branch_out:=0.U
    }
    
    }








        