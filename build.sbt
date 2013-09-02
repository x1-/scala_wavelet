name := "Wavelet"

organization := "ly.inoueyu"

version := "0.0.1"

//scalaVersion := "2.10.2"
scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2" % "1.12.4" % "test"
)

initialCommands := "import ly.inoueyu.wavelet._"
