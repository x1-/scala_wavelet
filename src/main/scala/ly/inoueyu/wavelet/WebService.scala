package ly.inoueyu.wavelet

import scala.concurrent.duration._
import akka.actor._
import akka.pattern.ask
import akka.util.Timeout
import spray.can.Http
import spray.util._
import spray.http._
import HttpMethods._
import spray.http.HttpRequest
import MediaTypes._

/**
 * Created with IntelliJ IDEA.
 * User: a12884
 * Date: 2013/09/26
 * Time: 15:16
 * To change this template use File | Settings | File Templates.
 */
class WebService extends Actor with ActorLogging {

  implicit val timeout: Timeout = 1.second // for the actor 'asks'

  def receive = {
    // when a new connection comes in we register ourselves as the connection handler
    case _: Http.Connected => sender ! Http.Register(self)

    case HttpRequest(GET, Uri.Path("/"), _, _, _) =>
      sender ! index

    case HttpRequest(GET, Uri.Path("/generate"), _, _, _) =>
      val indexer = new Indexer( 4 )
      val matrix = indexer.generate( new Relations() )
      sender ! matrixPresentation( matrix )
  }
  lazy val index = HttpResponse(
    entity = HttpEntity(`text/html`,
      <html>
        <body>
          <h1>Say hello to <i>spray-can</i>!</h1>
          <p>Defined resources:</p>
          <ul>
            <li><a href="/timeout">/timeout</a></li>
            <li><a href="/stop">/stop</a></li>
          </ul>
          <h3>Wavelet Matrix:</h3>
          <ul>
            <li><a href="/generate">/generate</a></li>
          </ul>
          <p>Riak Operations:</p>
          <ul>
            <li><a href="/create">/create</a></li>
            <li><a href="/close-riak">/close-riak</a></li>
            <li><a href="/user">/user/xxx</a></li>
          </ul>
          <p>user data post</p>
          <form action ="/user" enctype="application/x-www-form-urlencoded" method="post">
            <input type="text" name="id" value=""></input>
            <br/>
            <input type="text" name="name" value=""></input>
            <br/>
            <input type="submit">Submit</input>
          </form>
        </body>
      </html>.toString()
    )
  )
  def matrixPresentation( matrix: WaveletMatrix ) = {
    val els : IndexedSeq[String] =  for ( n <- 0 to matrix.length-1 )
      yield (
          "<tr>"
        + "<td>level:</td><td>" + matrix.get( n ).bitLevel + "</td>"
        + "<td>BitSet:</td><td>" + matrix.get( n ).vector.toString + "</td>"
        + "</tr>"
      )

    HttpResponse(
      entity = HttpEntity(`text/html`,
        <html>
          <body>
            <h2>Wavelet Matrix Index Generated!</h2>
            <table>
              els.mkString
            </table>
          </body>
        </html>.toString()
      )
    )
  }
}
