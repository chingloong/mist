package io.hydrosphere.mist

import akka.actor.{ActorSystem, Props}
import io.hydrosphere.mist.master.JsonFormatSupport
import io.hydrosphere.mist.worker.JobRunnerNode
import spray.json.pimpString

private[mist] object JobEntryPoint extends App with Logger with JsonFormatSupport{

  if (args.length < 4) {
    logger.error("`path` `className` `name` arguments are required")
    System.exit(1)
  }

  implicit val system = ActorSystem("mist", MistConfig.Akka.Worker.settings)

  val contextNode =
    if (args.length == 5) {
      val json = args(4).toString.parseJson
      system.actorOf(Props(new JobRunnerNode(args(0), args(1), args(2), args(3), json.convertTo[Map[String, Any]])), name = "JobStarter")
    } else if (args.length == 4) {
      system.actorOf(Props(new JobRunnerNode(args(0), args(1), args(2), args(3), Map().empty)), name = "JobStarter")
    }
}