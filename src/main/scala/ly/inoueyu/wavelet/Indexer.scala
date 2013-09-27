package ly.inoueyu.wavelet

import scala.collection.immutable._
import scala.collection.mutable
import org.slf4j.LoggerFactory
import akka.actor.ActorLogging
import scala.collection.mutable.ListBuffer

/**
 * waveletインデックスを作成します。
 *
 * Created with IntelliJ IDEA.
 * User: a12884
 * Date: 2013/09/26
 * Time: 16:33
 */
class Indexer( length: Int ) {

  val log = LoggerFactory.getLogger( this.getClass )

  def generate( rels: Relations ) : WaveletMatrix = {

    // 各ノードをBitSetに変換します。
    val bits = flatten( rels.getAll.length-1, rels, Array.empty )

    val matrix = new WaveletMatrix( length )
    var pre_zero = new ListBuffer[Int]()
    var pre_one = new ListBuffer[Int]()

    for ( n <- 0 to bits.length-1 )
      { pre_zero += n }

    for ( n <- 0 to length-1 ) {

      log.debug( "generate:bit_level:{}", n )

      val order : List[Int] = pre_zero.toList ++ pre_one.toList
      pre_one.clear()
      pre_zero.clear()

      val cols = new mutable.BitSet()

      for ( m <- 0 to bits.length-1 ) {
        val o = order( m )
        if ( bits( o )( n ) ) {
          cols += m
          pre_one += o
        } else {
          pre_zero += o
        }
      }

      log.debug( "generate:bit_set:{}", cols )

      matrix.update( n, matrix.WaveletRow( n, cols ) )
    }
    matrix
  }

  private def flatten( index: Int, rels: Relations, previous : Array[BitSet] ) : Array[BitSet] = {
    log.debug( "flatten:index:{}", index )
    val converted = index match {
      case i if ( i < 0 )  => Array.empty
      case i if ( i == 0 ) => long2BitSet( rels.get( index ).end )
      case _ => flatten( index-1, rels, long2BitSet( rels.get( index ).end ) )
    }
    converted ++ previous
  }

  private def long2BitSet( nodes: Array[Long] ) : Array[BitSet] = {
    nodes match {
      case a:Array[Long] =>
        log.debug( "long2BitSet:what is size of nodes?:{}", (nodes.length) )
        nodes.map {
          n => BitSet.fromBitMaskNoCopy( Array( n ) )
        }
      case _ =>
        log.debug( "long2BitSet:nodes is null." )
        Array.empty
    }
  }
}
