name := "twelve-squares"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.0" % Test

testOptions in Test += Tests.Argument(TestFrameworks.ScalaCheck, "-maxSize", "5", "-minSuccessfulTests", "33", "-workers", "1", "-verbosity", "1")
