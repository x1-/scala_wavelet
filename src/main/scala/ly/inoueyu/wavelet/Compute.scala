package ly.inoueyu.wavelet

import scala.collection.BitSet

/**
 * Created with IntelliJ IDEA.
 * User: a12884
 * Date: 2013/09/28
 * Time: 14:01
 * To change this template use File | Settings | File Templates.
 */
trait Compute {

  def intValue( bits: BitSet ) : Int = {
    var v: Int = 0
    for (n <- 31 to 0 by -1) {
      v |= ( if ( bits(n) ) 1 else 0 ) << n
    }
    return v
  }
}
