package ly.inoueyu.wavelet

import scala.util.Random

/**
 * ノード間のリレーションを表します。
 * 
 * Created with IntelliJ IDEA.
 * User: inoue yuri
 * Date: 2013/09/26
 * Time: 15:27
 */
class Relations {
  val __data : Map[Long, List[Long]] = Map(
     ( 1, List( 11, 0, 15 ) )
    ,( 2, List( 6, 5, 2, 7 ) )
    ,( 3, List( 12, 11, 0 ) )
    ,( 4, List( 12 ) )
    ,( 5, List( 12, 13 ) )
    ,( 6, List( 4, 6, 13 ) )
    ,( 7, List( 1, 11 ) )
    ,( 8, List( 6, 1, 7, 10 ) )
    ,( 9, List( 2, 7 ) )
    ,( 10, List( 14, 11 ) )
    ,( 11, List( 1, 7, 5, 4, 14 ) )
    ,( 12, List( 6 ) )
  )
//  val _data : Array[Relation] = Array(
//     Relation( 0, null )
//    ,Relation( 1, Array[Long]( 11, 0, 15 ) )
//    ,Relation( 2, Array[Long]( 6, 5, 2, 7 ) )
//    ,Relation( 3, Array[Long]( 12, 11, 0 ) )
//    ,Relation( 4, Array[Long]( 12 ) )
//    ,Relation( 5, Array[Long]( 12, 13 ) )
//    ,Relation( 6, Array[Long]( 4, 6, 13 ) )
//    ,Relation( 7, Array[Long]( 1, 11 ) )
//    ,Relation( 8, Array[Long]( 6, 1, 7, 10 ) )
//    ,Relation( 9, Array[Long]( 2, 7 ) )
//    ,Relation( 10, Array[Long]( 14, 11 ) )
//    ,Relation( 11, Array[Long]( 1, 7, 5, 4, 14 ) )
//    ,Relation( 12, Array[Long]( 6 ) )
//  )

  val _data = getBillions

  def get( index: Int ) : Relation = _data( index )
  def getAll : Array[Relation] = _data
  def getBillions : Array[Relation] = {
    val rels = new Array[Relation](100)

    rels( 0 ) = Relation( 0, null )

    val r = new Random

    for ( n <- 1 to 99 ) {
      val ends = new Array[Long]( 1000 )
      for ( m <- 0 to 999 ) {
        ends( m ) = Random.nextInt( 10000-1 )
      }
      rels(n) = Relation( n, ends )
    }
    rels
  }

  case class Relation(
    start : Long,
    end : Array[Long]
  )
}
