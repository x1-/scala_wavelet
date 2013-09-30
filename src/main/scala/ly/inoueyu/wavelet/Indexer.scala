package ly.inoueyu.wavelet

import scala.collection.immutable._
import scala.collection.mutable
import org.slf4j.LoggerFactory
import scala.collection.mutable.ListBuffer

/**
 * waveletインデックスを作成します。
 *
 * Created with IntelliJ IDEA.
 * User: a12884
 * Date: 2013/09/26
 * Time: 16:33
 */
class Indexer( length: Int ) extends Compute {

  val log = LoggerFactory.getLogger( this.getClass )

  def generate( rels: Relations ) : WaveletMatrix = {

    // 各ノードをBitSetに変換します。
    val bits = flatten( rels.getAll.length-1, rels, Array.empty )

    // ビットの並び順を保持します。
    var preZero = new ListBuffer[Int]()
    var preOne = new ListBuffer[Int]()

    for ( n <- 0 to bits.length-1 )
      { preZero += n }

    val matrix = new WaveletMatrix( length )

    for ( n <- length-1 to 0 by -1) {

      log.debug( "generate:bit_level:{}", n )

      // この操作で0の左寄寄せ、1の右寄せを行っています。
      val order : List[Int] = preZero.toList ++ preOne.toList
      preOne.clear()
      preZero.clear()

      val cols = new mutable.BitSet()

      for ( m <- 0 to bits.length-1 ) {
        log.debug( "generate:bits:{}", m )
        val o = order( m )
        if ( bits( o )( n ) ) {
          cols += m
          preOne += o
        } else {
          preZero += o
        }
      }

//      log.debug( "generate:bit_set:{}", cols )

      val rowIndex = ( length - 1 - n )
      matrix.update(
        rowIndex
        ,matrix.WaveletRow( rowIndex, cols, bits.length, preZero.length ) )
    }

    val order = ( preZero.toList ++ preOne.toList ).toArray
    var current = -1

    for ( n <- 0 to order.length-1 ) {
      val value = intValue( bits( order( n ) ) )
      if ( value != current ) {
        current = value
        matrix.updateIndex( current, n )
      }
    }

    for ( n <- matrix.length-1 to 1 ) {
      val row = matrix.get( n )
      matrix.update(
         n
        ,matrix.WaveletRow( n, row.vector, row.bitLevel, matrix.get( n-1 ).zero ) )
    }
    matrix
  }

  private def flatten( index: Int, rels: Relations, previous : Array[BitSet] ) : Array[BitSet] = {
    log.debug( "flatten:index:{}", index )
//    log.debug( "flatten:rels  :{}", rels.get( index ) )
    val converted = index match {
      case i if ( i < 0 )  => Array.empty
      case i if ( i == 0 ) => long2BitSet( rels.get( index ).end )
      case _ => flatten( index-1, rels, long2BitSet( rels.get( index ).end ) )
    }
    converted ++ previous
  }

  private def long2BitSet( nodes: Array[Long] ) : Array[BitSet] = {
    if ( nodes == null ) {
      log.debug("nodes is null!")
      return Array.empty
    }
    log.debug("nodes is not null!")
    nodes match {
      case null => Array.empty
      case a:Array[Long] =>
//        log.debug( "long2BitSet:what is size of nodes?:{}", a )
        a.map {
          n => BitSet.fromBitMaskNoCopy( Array( n ) )
        }
      case _ =>
//        log.debug( "long2BitSet:nodes is null." )
        Array.empty
    }
  }
}
