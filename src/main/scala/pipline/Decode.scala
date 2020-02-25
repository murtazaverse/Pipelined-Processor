package pipline
import chisel3._
import chisel3.util.Cat
import chisel3.util.Fill
class Decode extends Module{
    val io=IO(new Bundle{
        val  pcOut=Input(SInt(32.W))
        val pcRegOut=Output(SInt(32.W))

       val pc4Out=Input(SInt(32.W))
       val pc4RegOut=Output(SInt(32.W))

        val InstructionMem=Input(UInt(32.W))
        //val pc=Input(SInt(32.W))
        //val writedata=Input(SInt(32.W))
        //controlOutput
       // val opcode=Input(UInt(7.W))
        val memwriteRegOut=Output(UInt(1.W))
        val branchAndOut=Output(UInt(1.W))//branch and output
        val memreadRegOut=Output(UInt(1.W))
        val regwriteRegOut=Output(UInt(1.W))
        val memtoregRegOut=Output(UInt(1.W))
        val aluopRegOut=Output(UInt(3.W))
        val operand_a_selRegOut=Output(UInt(2.W))
        val operand_b_selRegOut=Output(UInt(1.W))
       // val extend_selOut=Output(UInt(2.W))
        val next_pc_selOut=Output(UInt(2.W))
        //regfileOutput
        val readdata1RegOut=Output(SInt(32.W))
        val readdata2RegOut=Output(SInt(32.W))
        //immgenOutput
       //   val s_immOut=Output(SInt(32.W))
        val sb_immOut=Output(SInt(32.W))
       //   val u_immOut=Output(SInt(32.W))
         val uj_immOut=Output(SInt(32.W))
       //   val i_immOut=Output(SInt(32.W))
        //mux top
        val imm_muxRegOut=Output(SInt(32.W))
        //insn mem output
       // val InstructionMem_out_19_15_bitsRegOut=Output(SInt(5.W))
        val InstructionMem_out_11_7_bitsRegOut=Output(UInt(5.W))
        val InstructionMem_out_14_12_bitsRegOut=Output(UInt(3.W))
        val InstructionMem_out_30_bitsRegOut=Output(UInt(1.W))
        val InstructionMem_out_19_15_bitsRegOut=Output(UInt(5.W))
        val InstructionMem_out_24_20_bitsRegOut=Output(UInt(5.W))
        //regfile
        val writereg=Input(UInt(5.W))
        val writedata=Input(SInt(32.W))
        //val regwrite=Input(UInt(1.W))
       //   val readdata1=Input(SInt(32.W))
       //   val readdata2=Input(SInt(32.W))
       val regwrite_regfile=Input(UInt(1.W))
       
      })
      
      
      val branch_logic = Module(new Branchlogic())
      

    
    
      //controlOutput
      // val opcode=Input(UInt(7.W))
      val memwrite=Wire(UInt(1.W))
      val control_branch=Wire(UInt(1.W))
      val memread=Wire(UInt(1.W))
      val regwrite=Wire(UInt(1.W))
      val memtoreg=Wire(UInt(1.W))
      val aluop=Wire(UInt(3.W))
      val operand_a_sel=Wire(UInt(2.W))
      val operand_b_sel=Wire(UInt(1.W))
      val extend_sel=Wire(UInt(2.W))
      val next_pc_sel=Wire(UInt(2.W))
      //regfileOutput
      val readdata1=Wire(SInt(32.W))
       val readdata2=Wire(SInt(32.W))
      //immgenOutput
      val s_imm=Wire(SInt(32.W))
      val sb_imm=Wire(SInt(32.W))
      val u_imm=Wire(SInt(32.W))
      val uj_imm=Wire(SInt(32.W))
      val i_imm=Wire(SInt(32.W))
      //imm mux
      val imm_mux=Wire(SInt(32.W))
      //insn mem outputs
      //val InstructionMem_out_19_15_bits=Wire(SInt(5.W))
      val InstructionMem_out_11_7_bits=Wire(UInt(5.W))
      val InstructionMem_out_14_12_bits=Wire(UInt(3.W))
      val InstructionMem_out_30_bits=Wire(UInt(1.W))
        

    
     when(io.InstructionMem(6,0)==="b0110011".U){
     memwrite:=0.U
      control_branch:=0.U
      memread:=0.U
      regwrite:=1.U
      memtoreg:=0.U
      aluop:="b000".U
      operand_a_sel:="b00".U
      operand_b_sel:="b0".U
      extend_sel:="b00".U
      next_pc_sel:="b00".U
}
.elsewhen(io.InstructionMem(6,0)==="b0000011".U){
      memwrite:=0.U
      control_branch:=0.U
      memread:=1.U
      regwrite:=1.U
      memtoreg:=1.U
      aluop:="b100".U
      operand_a_sel:="b00".U
      operand_b_sel:="b1".U
      extend_sel:="b00".U
      next_pc_sel:="b00".U
}
.elsewhen(io.InstructionMem(6,0)==="b0100011".U){
      memwrite:=1.U
      control_branch:=0.U
      memread:=0.U
      regwrite:=0.U
      memtoreg:=0.U
      aluop:="b101".U
      operand_a_sel:="b00".U
      operand_b_sel:="b1".U
      extend_sel:="b10".U
      next_pc_sel:="b00".U
}
.elsewhen(io.InstructionMem(6,0)==="b1100011".U){
      memwrite:=0.U
      control_branch:=1.U
      memread:=0.U
      regwrite:=0.U
      memtoreg:=0.U
      aluop:="b010".U
      operand_a_sel:="b00".U
      operand_b_sel:="b0".U
      extend_sel:="b00".U
      next_pc_sel:="b01".U
}
.elsewhen(io.InstructionMem(6,0)==="b0010011".U){
      memwrite:=0.U
      control_branch:=0.U
      memread:=0.U
      regwrite:=1.U
      memtoreg:=0.U
      aluop:="b001".U
      operand_a_sel:="b00".U
      operand_b_sel:="b1".U
      extend_sel:="b00".U
      next_pc_sel:="b00".U
}
.elsewhen(io.InstructionMem(6,0)==="b0000011".U){
      memwrite:=0.U
      control_branch:=0.U
      memread:=0.U
      regwrite:=1.U
      memtoreg:=0.U
      aluop:="b011".U
      operand_a_sel:="b10".U
      operand_b_sel:="b0".U
      extend_sel:="b00".U
      next_pc_sel:="b11".U
}
.elsewhen(io.InstructionMem(6,0)==="b1101111".U){
     memwrite:=0.U
     control_branch:=0.U
     memread:=0.U
     regwrite:=1.U
     memtoreg:=0.U
     aluop:="b011".U
     operand_a_sel:="b10".U
     operand_b_sel:="b0".U
     extend_sel:="b00".U
     next_pc_sel:="b10".U
}
.elsewhen(io.InstructionMem(6,0)==="b0110111".U){
     memwrite:=0.U
     control_branch:=0.U
     memread:=0.U
     regwrite:=1.U
     memtoreg:=0.U
     aluop:="b110".U
     operand_a_sel:="b11".U
     operand_b_sel:="b1".U
     extend_sel:="b01".U
     next_pc_sel:="b00".U
}
.otherwise{
     memwrite:=0.U
     control_branch:=0.U
     memread:=0.U
     regwrite:=0.U
     memtoreg:=0.U
     aluop:="b000".U
     operand_a_sel:="b00".U
     operand_b_sel:="b0".U
     extend_sel:="b00".U
     next_pc_sel:="b00".U
}
 val reg = RegInit(VecInit(Seq.fill(32)(0.S(32.W))))
    val readreg1=Wire(UInt(5.W))
    val readreg2=Wire(UInt(5.W))
    readreg1:=io.InstructionMem(19,15)
    readreg2:=io.InstructionMem(24,20)
    reg(0) := 0.S
    readdata1 := reg(readreg1) 
    readdata2 := reg(readreg2) 
    //val io.InstructionMem(11,7) :=io.InstructionMem(11,7) 
    when(io.regwrite_regfile === 1.U)
     {
        when(io.writereg === "b00000".U) {
            reg(io.writereg) := 0.S    
        }  
        .otherwise {
            reg(io.writereg) := io.writedata
        }
     }
     //s_imm
    val s_lbits=io.InstructionMem(11,7)
    val s_ubits=io.InstructionMem(31,25)
    val s_12bits=Cat(s_ubits,s_lbits)
    val s_32bits=Cat(Fill(20,s_12bits(11)),s_12bits)
    s_imm:=s_32bits.asSInt
    //sb_imm
    val sb_lbits=io.InstructionMem(11,8)
    val sb_11=io.InstructionMem(7)
    val sb_ubits=io.InstructionMem(30,25)
    val sb_12=io.InstructionMem(31)
    val sb_12bits=Cat(sb_12,sb_11,sb_ubits,sb_lbits,0.U)
    val sb_32bits=Cat(Fill(20,(sb_12bits(11))), sb_12bits).asSInt
    sb_imm:=sb_32bits+io.pcOut
    //u_imm
    val u_20bits=io.InstructionMem(31,12)
    val u_32bits=Cat(Fill(12,u_20bits(19)),u_20bits).asSInt
    u_imm:=u_32bits << 12.U 
    //uj_imm
    val uj_20=io.InstructionMem(31)
    val uj_ubits=io.InstructionMem(30,21)
    val uj_11=io.InstructionMem(20)
    val uj_lbits=io.InstructionMem(19,12)
    val uj_20bits=Cat(uj_20,uj_lbits,uj_11,uj_ubits,0.U)
    val uj_32bits=Cat(Fill(12,uj_20bits(19)),uj_20bits).asSInt
    uj_imm:=uj_32bits+io.pcOut
    //i_imm
    val i_20bits=io.InstructionMem(31,20)
    val i_32bits=Cat(Fill(20,i_20bits(11)),i_20bits)
    i_imm:=i_32bits.asSInt
      ////
      when(extend_sel==="b00".U)
        {
            imm_mux:=i_imm
        }
        .elsewhen(extend_sel==="b01".U)
        {
            imm_mux:=s_imm
        }
       .otherwise
        {
            imm_mux:=u_imm
        }
      ////

      val pcOutReg=RegInit(0.S(32.W))
      val pc4Reg=RegInit(0.S(32.W))
      
      val memwriteOut=RegInit(0.U(1.W))
      //val control_branchOut=RegInit(0.U(1.W))
      val memreadOut=RegInit(0.U(1.W))
      val regwriteOut=RegInit(0.U(1.W))
      val memtoregOut=RegInit(0.U(1.W))
      val aluopOut=RegInit(0.U(3.W))
      val operand_a_selOut=RegInit(0.U(2.W))
      val operand_b_selOut=RegInit(0.U(1.W))
      //val extend_selOut=RegInit(0.U(2.W))
      //val next_pc_selOut=RegInit(0.U(2.W))
      //regfileOutput
      val readdata1Out=RegInit(0.S(32.W))
      val readdata2Out=RegInit(0.S(32.W))
      //immgenOutput
      //val s_immOut=RegInit(0.S(32.W))
      //val sb_immOut=RegInit(0.S(32.W))
      val imm_muxOut=RegInit(0.S(32.W))
     // val u_immOut=RegInit(0.S(32.W))
      //val uj_immOut=RegInit(0.S(32.W))
      //val i_immOut=RegInit(0.S(32.W))
     // val InstructionMem_out_19_15_bitsOut=RegInit(0.S(5.W))
      val InstructionMem_out_11_7_bitsOut=RegInit(0.U(5.W))
      val InstructionMem_out_14_12_bitsOut=RegInit(0.U(3.W))
      val InstructionMem_out_30_bitsOut=RegInit(0.U(1.W))
      val InstructionMem_out_19_15_bitsOut=RegInit(0.U(5.W))
      val InstructionMem_out_24_20_bitsOut=RegInit(0.U(5.W))


      pcOutReg:=io.pcOut
      io.pcRegOut:=pcOutReg
      pc4Reg:=io.pc4Out
      io.pc4RegOut:=pc4Reg

     // InstructionMem_out_19_15_bits:=InstructionMem(19,15)
     InstructionMem_out_11_7_bits:=io.InstructionMem(11,7)
      //InstructionMem_out_11_7_bits:=io.InstructionMem(24,20)
      InstructionMem_out_14_12_bits:=io.InstructionMem(14,12)
      InstructionMem_out_30_bits:=io.InstructionMem(30)

     // InstructionMem_out_19_15_bitsOut:=InstructionMem_out_19_15_bits
      InstructionMem_out_11_7_bitsOut:=InstructionMem_out_11_7_bits
      InstructionMem_out_14_12_bitsOut:=InstructionMem_out_14_12_bits
      InstructionMem_out_30_bitsOut:=InstructionMem_out_30_bits
      InstructionMem_out_19_15_bitsOut := io.InstructionMem(19,15)
      InstructionMem_out_24_20_bitsOut := io.InstructionMem(24,20)

      memwriteOut:=memwrite
      //io.control_branchOut:=control_branch
      memreadOut:=memread
      regwriteOut:=regwrite
      memtoregOut:=memtoreg
      aluopOut:=aluop
      operand_a_selOut:=operand_a_sel
      operand_b_selOut:=operand_b_sel
      //io.extend_selOut:=io.extend_sel
      io.next_pc_selOut:=next_pc_sel
      readdata1Out:=readdata1
      readdata2Out:=readdata2
      //immgenOutput
     // io.s_immOut:=io.s_imm
      io.sb_immOut:=sb_imm
      //io.u_immOut:=io.u_imm
      io.uj_immOut:=uj_imm
      //io.i_immOut:=io.i_imm
      imm_muxOut:=imm_mux
      //
      io.memwriteRegOut:=memwriteOut
     // val control_branchOut
      //io.control_branchRegOut:=control_branchOut
      io.memreadRegOut:=memreadOut
      io.regwriteRegOut:=regwriteOut
      io.memtoregRegOut:=memtoregOut
      io.aluopRegOut:=aluopOut
      io.operand_a_selRegOut:=operand_a_selOut
      io.operand_b_selRegOut:=operand_b_selOut
      //extend_selRegOut:=extend_selOut
      // next_pc_selRegOut:next_pc_selOut
      io.readdata1RegOut:=readdata1Out
      io.readdata2RegOut:=readdata2Out
      //immgenOutput
      //s_immRegOut:=s_immOut
     // io.sb_immRegOut:=sb_immOut
     // u_immRegOut:=u_immOut
    //  uj_immRegOut:=uj_immOut
    //  i_immRegOut:=i_immOut
    io.imm_muxRegOut:=imm_muxOut
     // io.InstructionMem_out_19_15_bitsRegOut:=InstructionMem_out_19_15_bitsOut
      io.InstructionMem_out_11_7_bitsRegOut:=InstructionMem_out_11_7_bitsOut
      io.InstructionMem_out_14_12_bitsRegOut:=InstructionMem_out_14_12_bitsOut
      io.InstructionMem_out_30_bitsRegOut:=InstructionMem_out_30_bitsOut
      io.InstructionMem_out_19_15_bitsRegOut:=InstructionMem_out_19_15_bitsOut
      io.InstructionMem_out_24_20_bitsRegOut:=InstructionMem_out_24_20_bitsOut

      io.branchAndOut:=(branch_logic.io.branch_out) & (control_branch)
      branch_logic.io.readdata1:=readdata1
      branch_logic.io.readdata2:=readdata2
      branch_logic.io.insn_mem_14_12:=io.InstructionMem(14,12)

        
}