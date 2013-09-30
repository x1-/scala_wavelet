package ly.inoueyu.wavelet

import scala.collection.mutable
import org.slf4j.LoggerFactory

/**
 * Created with IntelliJ IDEA.
 * User: a12884
 * Date: 2013/09/27
 * Time: 17:33
 * To change this template use File | Settings | File Templates.
 */
class PopularNode( matrix : WaveletMatrix ) extends Compute {
  val log = LoggerFactory.getLogger( this.getClass )

  def get( level : Int = 0 ) : Array[Int] = {
    val res = new Array[Int](level+1)

    var bits = new mutable.BitSet()
    val diffs = mutable.Map[Int, Int]()

    log.debug( "level:{}", level )
    log.debug( "res.length:{}", res.length )

    for ( n <- 0 to matrix.length - 1 ) {
      val row = matrix.get( n )
      if ( row.zero < row.size / 2 )
        bits += n

      diffs += n -> ( row.size - row.zero - row.zero )
    }
    res(0) = intValue( bits )
    log.debug( "before sort, node:{}", res(0) )

    val sorted = diffs.toSeq.sortBy( _._2 abs )

    log.debug( "diffs size:{}", diffs.size )

    for ( n <- 1 to level if n < diffs.size ) {
      val index = n - 1
      val x = sorted( index )._1

      log.debug( "0-1 diff:{}, level:{}", sorted( index )._2, sorted( index )._1 )

      val newB = bits.clone()

      newB( x ) match {
        case true => newB -= x
        case false => newB += x
      }
      log.debug( "add level:{}", n )
      res(n) = intValue( newB )
      log.debug( "after sort, node:{}", res(n) )
    }
    res
  }
}

object PopularNode {
  def apply( matrix : WaveletMatrix ) = new PopularNode( matrix )
}
