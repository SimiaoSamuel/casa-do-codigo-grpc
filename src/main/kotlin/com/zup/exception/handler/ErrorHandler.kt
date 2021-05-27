package com.zup.exception.handler

import com.zup.exception.NaoExisteException
import com.zup.exception.ValorDuplicadoException
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.grpc.stub.StreamObserver
import io.grpc.stub.StreamObservers
import io.micronaut.aop.Around
import io.micronaut.aop.InterceptorBean
import io.micronaut.aop.MethodInterceptor
import io.micronaut.aop.MethodInvocationContext
import javax.inject.Singleton
import javax.validation.ConstraintViolationException

@Singleton
@InterceptorBean(ErrorHandler::class)
class ErrorInterceptor: MethodInterceptor<Any,Any> {
    override fun intercept(context: MethodInvocationContext<Any, Any>): Any {
        return try {
            context.proceed()
        } catch (e : Exception){
            val observer = context.parameterValues.filterIsInstance<StreamObserver<*>>().firstOrNull() as StreamObserver<*>
            when(e){
                is NaoExisteException -> observer.onError(Status.NOT_FOUND.withDescription(e.message).asRuntimeException())
                is ValorDuplicadoException -> observer.onError(Status.ALREADY_EXISTS.withDescription(e.message).asRuntimeException())
                is ConstraintViolationException -> observer.onError(Status.INVALID_ARGUMENT.withDescription(e.message).asRuntimeException())
                else -> observer.onError(Status.UNKNOWN.withDescription(e.message).asRuntimeException())
            }
        }
    }
}

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Around
annotation class ErrorHandler()