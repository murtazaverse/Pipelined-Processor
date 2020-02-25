package pipline
import chisel3._

class Forwarding extends Module {
    val io = IO(new Bundle {
        val EX_MEM_REGRD = Input(UInt(5.W))
        val MEM_WB_REGRD = Input(UInt(5.W))
        val ID_EX_REGRS1 = Input(UInt(5.W))
        val ID_EX_REGRS2 = Input(UInt(5.W))
        val EX_MEM_REGWR = Input(UInt(1.W))
        val MEM_WB_REGWR = Input(UInt(1.W))
        val fwd_opA_mux = Output(UInt(2.W))
        val fwd_opB_mux = Output(UInt(2.W))
    })
    when(io.EX_MEM_REGWR === "b1".U && io.EX_MEM_REGRD =/= "b00000".U && (io.EX_MEM_REGRD === io.ID_EX_REGRS1)) 
    {
        io.fwd_opA_mux := "b01".U
    } 
    .elsewhen(io.MEM_WB_REGWR === "b1".U && io.MEM_WB_REGRD =/= "b00000".U && ~((io.EX_MEM_REGWR === "b1".U) && (io.EX_MEM_REGRD =/= "b00000".U) && (io.EX_MEM_REGRD === io.ID_EX_REGRS1)) && (io.MEM_WB_REGRD === io.ID_EX_REGRS1)) 
    {
        io.fwd_opA_mux := "b10".U
    }
    .otherwise
    {
        io.fwd_opA_mux:="b00".U
    }
    //fwd_opB_mux
    when(io.EX_MEM_REGWR === "b1".U && io.EX_MEM_REGRD =/= "b00000".U && (io.EX_MEM_REGRD === io.ID_EX_REGRS2)) 
    {
        io.fwd_opB_mux := "b01".U
    } 
    .elsewhen(io.MEM_WB_REGWR === "b1".U && io.MEM_WB_REGRD =/= "b00000".U && ~((io.EX_MEM_REGWR === "b1".U) && (io.EX_MEM_REGRD =/= "b00000".U)  && (io.EX_MEM_REGRD === io.ID_EX_REGRS2))  && (io.MEM_WB_REGRD === io.ID_EX_REGRS2)) 
    {
        io.fwd_opB_mux := "b10".U
    } 
    .otherwise
    {
        io.fwd_opB_mux:="b00".U
    }
    }