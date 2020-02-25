package pipline
import chisel3._
import chisel3.util.experimental.loadMemoryFromFile
class Fetch extends Module{
    val io=IO(new Bundle{
        //val pc_in = Input(SInt(32.W))
        val pcoutRegOut = Output(SInt(32.W))
        val pc4RegOut=Output(SInt(32.W))
        val pc_out_11_2_bits=Output(UInt(20.W))
        //val read_data=Output(UInt(32.W))
        val pc_out=Output(SInt(32.W))
        //mux
        
        
        //mux1 input
        val imm_gen_sb_type=Input(SInt(32.W)) 
        ////pc+4
        val branch_out=Input(UInt(1.W)) //selection

        //mux2 input
        ////mux1output
        ////pc+4
        val imm_gen_uj_type=Input(SInt(32.W))
        val next_pc_sel=Input(UInt(2.W))//selection
        val jalr_out=Input(SInt(32.W))
        //insn mem
        val InstructionMeminp=Input(UInt(32.W))
        val InstructionMemRegOut=Output(UInt(32.W))

     })
 val pc_in=Wire(SInt(32.W))
 //val io.io.pc_out=Wire(SInt(32.W))
  val reg = RegInit(0.S(32.W))
  val pc4=RegInit(0.S(32.W))
  val pcOut=RegInit(0.S(32.W))
  val InstructionMemOut=RegInit(0.U(32.W))
  val pc_4=Wire(SInt(32.W))
 reg:=pc_in
 //val pcOut=Wire(SInt(32.W))
    io.pc_out:=reg
    //val pc4=Wire(SInt(32.W))
    pc_4:=reg+4.S
   // val write_add=Wire(UInt(20.W))
    
 
    //muxb
 when(io.next_pc_sel==="b00".U)
    {
        pc_in:=pc_4

    }
 .elsewhen(io.next_pc_sel==="b01".U)
    {//muxa
     
        when(io.branch_out===1.U)
        {
            pc_in:=io.imm_gen_sb_type
        }
        .otherwise
        {
            pc_in:=pc_4
        }

    }
 .elsewhen(io.next_pc_sel==="b10".U)
    {
        pc_in:=io.imm_gen_uj_type

    }
 .otherwise
    {
        pc_in:=io.jalr_out

    }
    //input in register
    InstructionMemOut:=io.InstructionMeminp
    //input from register
 io.pc_out_11_2_bits:=io.pc_out(11,2)
 pc4:=pc_4
 io.pc4RegOut:=pc4
 pcOut:=io.pc_out
 io.pcoutRegOut:=pcOut
 io.InstructionMemRegOut:=InstructionMemOut

}
//  write_add:=pcOut(21,2)

//     val memory = Mem(1024, UInt(32.W))
//     loadMemoryFromFile(memory, "/home/tuba/Desktop/file.txt")
//     io.read_data := memory.read(write_add)
