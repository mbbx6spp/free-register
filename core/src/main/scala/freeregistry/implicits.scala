package freeregistry

import cats.Functor

trait FreeRegistryImplicits extends FreeRegistryTypes {

  implicit val functor: Functor[ServiceRegistryF] = new Functor[ServiceRegistryF] {
    import ServiceRegistryF._

    def map[A, B](sr: ServiceRegistryF[A])(f: A => B): ServiceRegistryF[B] = sr match {
      case RegisterInstance(s, i, n)              => RegisterInstance(s, i, f(n))
      case UnregisterInstance(s, i, n)            => UnregisterInstance(s, i, f(n))
      case q: QueryServiceRegistry[s, A]  =>
        QueryServiceRegistry[s, B](q.service, q.onResult andThen f)
    }
  }
}
