import sbt._
import Keys._

package freeregistry {
  object Parent {
    def version       = "0.1.0"
    def organization  = "mbbx6spp"
    def name          = "freeregistry"
    def scalaVersion  = "2.11.6"
    def scalacOptions = Seq(
      "-feature",
      "-unchecked",
      "-deprecation",
      //"-Xfatal-warnings",
      "-Xlint",
      "-encoding",
      "utf8"
    )
    def license       = ("BSD", url("https://github.com/mbbx6spp/free-registry/blob/master/LICENSE"))
  }

  object Versions {
    def cats          = "0.1.0-SNAPSHOT"
  }

  object Build extends Build {
    /* default options at parent level */
    lazy val defaultSettings =
      Defaults.defaultSettings ++
        Seq(
          version       := Parent.version,
          organization  := Parent.organization,
          scalaVersion  := Parent.scalaVersion,
          scalacOptions := Parent.scalacOptions,
          licenses      += Parent.license,
          publishTo     := Some("Systemz Bintray Repo" at "https://dl.bintray.com/mbbx6spp/freeregistry"),
          resolvers     ++= Seq(
            "bintray/non" at "http://dl.bintray.com/non/maven",
            Resolver.sonatypeRepo("releases")
          )
        )

    /* aggregate/subproject spec */
    lazy val parent = Project("freeregistry",
      file("."),
      settings = defaultSettings
    ).aggregate(core, consul, examples)

    lazy val core = Project("freeregistry-core",
      file("core"),
      settings = defaultSettings
    )

    lazy val consul = Project("freeregistry-consul",
      file("consul"),
      settings = defaultSettings
    ).dependsOn(core)

    lazy val examples = Project("freeregistry-examples",
      file("examples"),
      settings = defaultSettings
    ).dependsOn(core, consul)
  }
}
