name := "Wavelet"

organization := "ly.inoueyu"

version := "0.0.1"

scalaVersion := "2.10.2"
//scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
//   "org.specs2"                    %  "specs2_2.10"            % "2.2"
   "ch.qos.logback"                 %  "logback-core"           % "latest.integration"
  ,"ch.qos.logback"                 %  "logback-classic"        % "latest.integration"
  ,"commons-codec"                  %  "commons-codec"          % "1.6"
  ,"org.apache.httpcomponents"      %  "httpclient"             % "4.2+"
  ,"org.apache.httpcomponents"      %  "httpcore"               % "4.2+"
  ,"com.fasterxml.jackson.core"     %  "jackson-annotations"    % "2.2+"
  ,"com.fasterxml.jackson.core"     %  "jackson-core"           % "2.2+"
  ,"com.fasterxml.jackson.core"     %  "jackson-databind"       % "2.2+"
  ,"com.fasterxml.jackson.datatype" %  "jackson-datatype-joda"  % "2.2+"
  ,"com.fasterxml.jackson.datatype" %  "jackson-datatype-joda"  % "2.2+"
  ,"joda-time"                      %  "joda-time"              % "2.2+"
  ,"org.json"                       %  "json"                   % "20090211"
  ,"org.jvnet.mimepull"             %  "mimepull"               % "1.9+"
  ,"com.typesafe"                   %  "config"                 % "1.0.2"
  ,"io.spray"                       %% "spray-json"             % "1.2.5"
  ,"io.spray"                       %  "spray-util"             % "1.2-M8"
  ,"io.spray"                       %  "spray-http"             % "1.2-M8"
  ,"io.spray"                       %  "spray-httpx"            % "1.2-M8"
  ,"io.spray"                       %  "spray-can"              % "1.2-M8"
  ,"io.spray"                       %  "spray-io"               % "1.2-M8"
  ,"com.typesafe.akka"              %% "akka-slf4j"             % "2.2.0-RC1"
  ,"com.typesafe.akka"              %% "akka-actor"             % "2.2.0-RC1"
  ,"com.basho.riak"                 %  "riak-client"            % "1.4+"
)

initialCommands := "import ly.inoueyu.wavelet._"
