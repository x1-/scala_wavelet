package ly.inoueyu.wavelet

import scala.collection.immutable._
import scala.collection.mutable
import org.slf4j.LoggerFactory
import akka.actor.ActorLogging

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
    val bits = flatten( rels.getAll.length, rels, Array.empty )
    val matrix = new WaveletMatrix( length )

    for ( n <- 0 to length-1 ) {
      log.info( "generate:n:%s", n )
      val cols = new mutable.BitSet()
      for ( m <- 0 to bits.length-1 ) {
        log.info( "generate:m:%s", m )
        if ( bits( m )( n ) )
          cols += m
      }
      matrix.update( n, matrix.WaveletRow( n, cols ) )
    }
    matrix
  }

  private def flatten( index: Int, rels: Relations, previous : Array[BitSet] ) : Array[BitSet] = {
    log.info( "flatten:index:%s", index )
    log.info( "index" )
    println( "flatten:index:%s", index )
    val converted = index match {
      case i if ( i < 0 )  => previous
      case i if ( i == 0 ) => long2BitSet( rels.get( index ).end )
      case _ => flatten( index-1, rels, long2BitSet( rels.get( index ).end ) )
    }
    previous ++ converted
  }

  private def long2BitSet( nodes: Array[Long] ) : Array[BitSet] = {
    nodes.map {
      n => BitSet.fromBitMaskNoCopy( Array( n ) )
    }
  }
}
