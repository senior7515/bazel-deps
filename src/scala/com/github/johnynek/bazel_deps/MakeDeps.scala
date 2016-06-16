package com.github.johnynek.bazel_deps

object MakeDeps {
  def main(args: Array[String]): Unit = {
    val resolver = new Resolver(List(MavenServer("central", "default", "http://central.maven.org/maven2/")))
    val aether = "org.eclipse.aether"
    val aetherVersion = "1.0.2.v20150114"
    val aethers = List(
      "aether-api",
      "aether-impl",
      "aether-connector-basic",
      "aether-transport-file",
      "aether-transport-http").map { art => s"$aether:$art:$aetherVersion" }

    val allDeps = ("org.apache.maven:maven-aether-provider:3.1.0" ::
      "com.google.guava:guava:18.0" ::
      "args4j:args4j:2.32" ::
      aethers).map(MavenCoordinate(_))

    val graph = resolver.addAll(Graph.empty, allDeps)
    println(graph.show(_.asString))
    println("###########################################################################")
    println("###########################################################################")
    println("###########################################################################")
    println(Normalizer(graph, null).get.show(_.asString))
  }
}