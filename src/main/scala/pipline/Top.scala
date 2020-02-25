package pipline
import chisel3._
class Top extends Module{
     val io=IO(new Bundle{
          val top_output=Output(SInt(32.W))
          val top_pc=Output(SInt(32.W))

     })
     val fetch = Module(new Fetch())
     val decode = Module(new Decode())
     val execute = Module(new Execute())
     val memory = Module(new Memory())
     val InsnMem = Module(new InstructionMemory())
     val datamemory = Module(new DataMemory())
     val writeback = Module(new Writeback())
     val forwarding = Module(new Forwarding())
     //fetch
     decode.io.pcOut:=fetch.io.pcoutRegOut
     decode.io.pc4Out:=fetch.io.pc4RegOut
     InsnMem.io.write_add:=fetch.io.pc_out_11_2_bits
     decode.io.InstructionMem:=fetch.io.InstructionMemRegOut
     fetch.io.InstructionMeminp:=InsnMem.io.read_data
     //decode
     execute.io.pc:=decode.io.pcRegOut
     execute.io.pc_4:=decode.io.pc4RegOut
     execute.io.memwrite:=decode.io.memwriteRegOut
     execute.io.memread:=decode.io.memreadRegOut
     execute.io.regwrite:=decode.io.regwriteRegOut
     execute.io.memtoreg:=decode.io.memtoregRegOut
     execute.io.aluop:=decode.io.aluopRegOut
     execute.io.operand_a_sel:=decode.io.operand_a_selRegOut
     execute.io.operand_b_sel:=decode.io.operand_b_selRegOut
     fetch.io.next_pc_sel:=decode.io.next_pc_selOut
     execute.io.readdata1:=decode.io.readdata1RegOut
     execute.io.readdata2:=decode.io.readdata2RegOut
     fetch.io.imm_gen_sb_type:=decode.io.sb_immOut
     fetch.io.imm_gen_uj_type:=decode.io.uj_immOut
     execute.io.imm_mux:=decode.io.imm_muxRegOut
     execute.io.InstructionMem_out_11_7_bits:=decode.io.InstructionMem_out_11_7_bitsRegOut
     execute.io.InstructionMem_out_14_12_bits:=decode.io.InstructionMem_out_14_12_bitsRegOut
     execute.io.InstructionMem_out_30_bits:=decode.io.InstructionMem_out_30_bitsRegOut

     //////////////////////////////////////////////////////////////////////////////////////////////////////////////

     forwarding.io.EX_MEM_REGRD := execute.io.InstructionMem_out_11_7_bitsRegOut
     forwarding.io.MEM_WB_REGRD := memory.io.InstructionMem_out_11_7_bitRegOut
     forwarding.io.ID_EX_REGRS1 := decode.io.InstructionMem_out_19_15_bitsRegOut
     forwarding.io.ID_EX_REGRS2 := decode.io.InstructionMem_out_24_20_bitsRegOut
     forwarding.io.EX_MEM_REGWR := execute.io.regwriteRegOut
     forwarding.io.MEM_WB_REGWR := memory.io.regwriteRegOut
     


     when(execute.io.operand_a_sel_out === 1.U)
     {
          execute.io.a := execute.io.pc_out
     }
     .elsewhen(execute.io.operand_a_sel_out === 2.U)
     {
          execute.io.a := execute.io.pc_4_out
     }
     .otherwise
     {
          when(forwarding.io.fwd_opA_mux === "b01".U)
          {
               execute.io.a := execute.io.alu_resultRegOut
               //execute.io.operand_1_in := execute.io.alu_resultRegOut
          }
          .elsewhen(forwarding.io.fwd_opA_mux === "b10".U)
          {
               execute.io.a := writeback.io.writedata_mux
               //execute.io.operand_1_in := writeback.io.writedata_mux
          }
          .otherwise
          {
               execute.io.a := execute.io.readdata1_out
               //execute.io.operand_1_in := execute.io.readdata1_out
          }
     }

     when(execute.io.operand_b_sel_out === 0.U)
     {
          when(forwarding.io.fwd_opB_mux === "b01".U)
          {
               execute.io.b := execute.io.alu_resultRegOut
               //execute.io.operand_2_in := execute.io.alu_resultRegOut
          }
          .elsewhen(forwarding.io.fwd_opB_mux === "b10".U)
          {
               execute.io.b := writeback.io.writedata_mux
               //execute.io.operand_2_in := writeback.io.writedata_mux
          }
          .otherwise
          {
               execute.io.b := execute.io.readdata2_out
               //execute.io.operand_2_in := execute.io.readdata2_out
          }
          //execute.io.operand_2_in := execute.io.readdata2_out                    
     }
     .otherwise
     {
          execute.io.b := execute.io.imm_mux_out
     }

     //////////////////////////////////////////////////////////////////////////////////////////////////////////////

     //execute
     memory.io.memwrite:=execute.io.memwriteRegOut
     memory.io.memread:=execute.io.memreadRegOut
     memory.io.regwrite:=execute.io.regwriteRegOut
     memory.io.memtoreg:=execute.io.memtoregRegOut
     memory.io.alu_result:=execute.io.alu_resultRegOut
     memory.io.InstructionMem_out_11_7_bits:=execute.io.InstructionMem_out_11_7_bitsRegOut
     memory.io.readdata2:=execute.io.operand2_RegOut
     fetch.io.jalr_out:=execute.io.jalr_out
     //branches and
     fetch.io.branch_out:=decode.io.branchAndOut
     //memory
     writeback.io.regwrite:=memory.io.regwriteRegOut
     writeback.io.alu_result:=memory.io.alu_resultRegOut
     writeback.io.dataread:=memory.io.datareadRegOut
     writeback.io.memtoreg:=memory.io.memtoregRegOut
     writeback.io.InstructionMem_out_11_7_bit:=memory.io.InstructionMem_out_11_7_bitRegOut
     //:=memory.io.datareadRegOut//
     //:=memory.io.alu_resultRegOut//
     //decode.io.regwrite_regfile:=memory.io.regwriteRegOut
    // :=memory.io.memtoregRegOut//
     //decode.io.writereg:=memory.io.InstructionMem_out_11_7_bitRegOut
     datamemory.io.address:=memory.io.alu_resultOut(9,2)
     datamemory.io.datawrite:=memory.io.readdata2Out
     datamemory.io.memread:=memory.io.memreadOut
     datamemory.io.memwrite:=memory.io.memwriteOut
     memory.io.datareadinp:=datamemory.io.dataread
     //writeback
     decode.io.regwrite_regfile:=writeback.io.regwriteOut
     decode.io.writedata:=writeback.io.writedata_mux
     decode.io.writereg:=writeback.io.InstructionMem_out_11_7_bitOut

     io.top_output:=decode.io.readdata2RegOut
     io.top_pc:=fetch.io.pc_out
    
     }