// See LICENSE.txt for license details.
package solutions

import Chisel.iotesters.{ Backend => TesterBackend, _ }

class AccumulatorTests(c: Accumulator, b: Option[TesterBackend] = None) extends PeekPokeTester(c, _backend=b) {
  var tot = 0
  for (t <- 0 until 16) {
    val in = rnd.nextInt(2)
    poke(c.io.in, in)
    step(1)
    if (in == 1) tot += 1
    expect(c.io.out, tot)
  }
}

class AccumulatorTester extends ChiselFlatSpec {
  behavior of "Accumulator"
  backends foreach {backend =>
    it should s"correctly accumulate randomly generated numbers in $backend" in {
      runPeekPokeTester(() => new Accumulator, backend){
        (c,b) => new AccumulatorTests(c,b)} should be (true)
    }
  }
}
