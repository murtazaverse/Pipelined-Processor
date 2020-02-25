package pipline
import chisel3._
import chisel3.util.experimental.loadMemoryFromFile
class InstructionMemory extends Module{
    val io=IO(new Bundle{
        val write_add=Input(UInt(20.W))
        val read_data=Output(UInt(32.W))
        
    })
    val memory = Mem(1024, UInt(32.W))
    loadMemoryFromFile(memory, "/home/murtaza/Desktop/mem1.txt")
    io.read_data := memory.read(io.write_add)
}