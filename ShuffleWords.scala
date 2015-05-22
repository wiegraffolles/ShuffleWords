#!/bin/bash
sbt -Dsbt.version=0.13.8 -Dsbt.main.class=sbt.ScriptMain $0 $@
exit
!#

/***
scalaVersion := "2.11.6"
 
onLoadMessage := ""
 
scalacOptions ++= Seq(
 "-deprecation", "-unchecked", "-feature", "-Xfatal-warnings")
 
libraryDependencies ++= Seq(
 "com.lihaoyi" % "ammonite-ops_2.11" % "0.3.0",
 "com.github.scopt" %% "scopt" % "3.3.0"
)
*/

import scopt._, ammonite.ops._, scala.util.Random

def verbose(origin: Seq[String], output: Seq[String]): Unit = {
  // Print shuffled data for verification
  println("\nOutput Text:\n")
  output.foreach(x => println(x))
  // Print input and output files
  println("\nRead from " + origin(0) + " and wrote to " + origin(1))
}

def randomize(input: Seq[String]): Seq[String] = {
  val randomized = Random.shuffle(read.lines(cwd/input(0)))
  randomized
}

// Read args into a usable format
val argsIn = args.toSeq

if (argsIn.head.startsWith("-") == true && argsIn.head.length > 1) {
  val argsTrimmed = argsIn.drop(1)
  val randomized = randomize(argsTrimmed)
  write.over(cwd/argsTrimmed(1), randomized)
  if (argsIn.head.contains('v') == true) {
    verbose(argsTrimmed, randomized)
  }
}
else if (argsIn.head.startsWith("-") == false && argsIn.head.startsWith("--") == false) {
  // Read data from input file into a Seq[String] and shuffle
  val randomized = randomize(argsIn)
  // Write data to output file
  write.over(cwd/argsIn(1), randomized)
}
else
  println("Error: Flag not specified")


