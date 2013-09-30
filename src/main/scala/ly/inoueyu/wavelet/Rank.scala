package ly.inoueyu.wavelet

import scala.collection.immutable._
import scala.collection.mutable
import org.slf4j.LoggerFactory

/**
 * Created with IntelliJ IDEA.
 * User: a12884
 * Date: 2013/09/28
 * Time: 13:59
 * To change this template use File | Settings | File Templates.
 */
class Rank( matrix : WaveletMatrix ) extends Compute {

  val log = LoggerFactory.getLogger( this.getClass )

  def getRank( target: Long, index: Int ) : Int = {

    val bit = BitSet.fromBitMaskNoCopy( Array( target ) )
    val pos = pointer( 0, matrix.length-1, index, bit )

    pos - matrix.getStart( target.toInt ) + 1
  }

  def getRank( target: Long, start: Int, end: Int ) : Int = {

    val bit = BitSet.fromBitMaskNoCopy( Array( target ) )

    val startPos = pointer( 0, matrix.length-1, start, bit )
    val excessCount = startPos - matrix.getStart( target.toInt ) + 1


    val endPos = pointer( 0, matrix.length-1, end, bit )
    val count = endPos - matrix.getStart( target.toInt ) + 1

    log.debug( "all_count:{}, excess_count:{}.", count, excessCount )

    count - excessCount
  }

  private def count( index: Int, isSame: Boolean, bit : mutable.BitSet ) : Int = {
    index match {
      case x
        if x < 0 => 0
      case _ => {
        log.debug( "index:{}, incr:{}.", index, ( bit( index ) ^ isSame ) )
        count( index-1, isSame, bit ) + ( if ( bit( index ) ^ isSame ) 0 else 1 )
      }
    }
  }

  private def pointer( current: Int, level: Int, end: Int, bit : BitSet ) : Int = {

    val index = current match {
      case `level` => end
      case _ => pointer( current+1, level, end, bit )
    }

    val reverse = level - current
    val num = count( index, bit( current ), matrix.get( reverse ).vector ) -1

    log.debug( "current:{}, num:{}.", current, num )
    log.debug( "reverse:{}, zero:{}.", reverse, matrix.get( reverse ).zero )

    if ( bit( current ) )
      num + matrix.get( reverse ).zero
    else
      num
  }
}
object Rank {
  def apply( matrix : WaveletMatrix ) = new Rank( matrix )
}
