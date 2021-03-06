package io.hydrosphere.mist.core

import org.mockito.stubbing.OngoingStubbing

import scala.concurrent.Future
import scala.reflect.ClassTag

trait MockitoSugar extends org.scalatest.mockito.MockitoSugar {

  def any[T <: AnyRef](implicit classTag: ClassTag[T]): T = {
    org.mockito.Matchers.any(classTag.runtimeClass.asInstanceOf[Class[T]])
  }

  def when[T](call: T): org.mockito.stubbing.OngoingStubbing[T] = {
    org.mockito.Mockito.when(call)
  }

  implicit class FutureStubbing[A](stubbing: OngoingStubbing[Future[A]]) {

    def thenSuccess(a: A): OngoingStubbing[Future[A]] =
      stubbing.thenReturn(Future.successful(a))

    def thenFailure(e: Throwable): OngoingStubbing[Future[A]] =
      stubbing.thenReturn(Future.failed(e))
  }

}
