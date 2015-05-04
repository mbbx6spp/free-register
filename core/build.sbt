libraryDependencies ++= {
  val catsV = freeregistry.Versions.cats
  Seq(
    "org.spire-math" %% "cats-core" % catsV,
    "org.spire-math" %% "cats-free" % catsV
  )
}
