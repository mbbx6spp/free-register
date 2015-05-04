package freeregistry

import cats.Functor
import cats.free.Free
import cats.{Id, ~>}

trait FreeRegistryTypes extends FreeRegistryTypeclasses {
  trait RegistryQueryResult

  sealed trait ServiceRegistryF[+Next]
  object ServiceRegistryF {
    case class RegisterInstance[S, I, Next](service: S, instance: I, next: Next) extends ServiceRegistryF[Next]
    case class UnregisterInstance[S, I, Next](service: S, instance: I, next: Next) extends ServiceRegistryF[Next]
    case class QueryServiceRegistry[S, Next](service: S, onResult: RegistryQueryResult => Next) extends ServiceRegistryF[Next]

    // smart costructors
    def register[Service, Instance](s: Service, i: Instance)(implicit f: Functor[ServiceRegistryF]): ServiceRegistry[Unit] =
      Free.liftF(RegisterInstance(s, i, ()))

    def unregister[Service, Instance](s: Service, i: Instance)(implicit f: Functor[ServiceRegistryF]): ServiceRegistry[Unit] =
      Free.liftF(UnregisterInstance(s, i, ()))

    def query[Service](s: Service)(implicit f: Functor[ServiceRegistryF]): ServiceRegistry[RegistryQueryResult] =
      Free.liftF(QueryServiceRegistry(s, identity))

    def modify[Service, Instance](s: Service, i: Instance)(implicit f: Functor[ServiceRegistryF]): ServiceRegistry[Unit] =
      sys.error("todo")
  }

  type ServiceRegistry[F] = Free[ServiceRegistryF, F]
}
