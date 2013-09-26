package ly.inoueyu.wavelet

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http

/**
 * Created with IntelliJ IDEA.
 * User: a12884
 * Date: 2013/09/26
 * Time: 15:13
 * To change this template use File | Settings | File Templates.
 */
object Main extends App {

  implicit val system = ActorSystem()
  val handler = system.actorOf( Props[WebService], name = "handler" )
  IO(Http) ! Http.Bind( handler, interface = "localhost", port = 8080 )
}
