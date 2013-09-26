package ly.inoueyu.wavelet

import scala.collection.mutable

/**
 * wavelet行列を表します。
 *
 * Created with IntelliJ IDEA.
 * User: inoue yuri
 * Date: 2013/09/26
 * Time: 16:00
 * To change this template use File | Settings | File Templates.
 */
class WaveletMatrix( _length: Int ) {

  val _data : Array[WaveletRow] = new Array( _length );

  def length : Int = _length
  def get( index: Int ) : WaveletRow = _data( index )

  def update( index: Int, row: WaveletRow ) = {
    _data( index ) = row
  }

  case class WaveletRow(
     bitLevel : Int
    ,vector : mutable.BitSet
  )
}