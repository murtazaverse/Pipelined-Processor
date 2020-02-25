package pipline
import chisel3.iotesters.PeekPokeTester
class Forwarding_t(c:Forwarding) extends PeekPokeTester(c){
    step(1)
}