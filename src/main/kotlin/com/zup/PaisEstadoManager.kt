package com.zup

import com.zup.annotation.OpenClass
import com.zup.exception.handler.ErrorHandler
import com.zup.model.toDto
import com.zup.service.PaisEstadoService
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ErrorHandler
@OpenClass
class PaisEstadoManager(@Inject val service: PaisEstadoService): PaisEstadoServiceGrpc.PaisEstadoServiceImplBase() {
    override fun criaPais(request: NovoPaisRequest, responseObserver: StreamObserver<NovoPaisResponse>) {
        val requestDto = request.toDto()

        val grpcResponse = service.salvaPais(requestDto)

        responseObserver.onNext(grpcResponse)
        responseObserver.onCompleted()
    }

    override fun criaEstado(request: NovoEstadoRequest, responseObserver: StreamObserver<NovoEstadoResponse>) {
        val requestDto = request.toDto()

        val grpcResponse = service.salvaEstado(requestDto)

        responseObserver.onNext(grpcResponse)
        responseObserver.onCompleted()
    }

    override fun detalhaPais(request: DetalhaPaisRequest, responseObserver: StreamObserver<DetalhaPaisResponse>) {
        val grpcResponse = service.DetalhaPais(request.toDto())

        responseObserver.onNext(grpcResponse)
        responseObserver.onCompleted()
    }
}