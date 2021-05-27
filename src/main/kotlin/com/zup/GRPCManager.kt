package com.zup

import com.zup.annotation.OpenClass
import com.zup.exception.handler.ErrorHandler
import com.zup.model.toDto
import com.zup.service.LivroService
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenClass
@ErrorHandler
class GRPCManager(@Inject val service: LivroService) : CasaDoCodigoGrpcServiceGrpc.CasaDoCodigoGrpcServiceImplBase() {
    override fun criaAutor(request: NovoAutorRequest, responseObserver: StreamObserver<NovoAutorResponse>) {
        val requestDto = request.toDto()
        val grpcResponse = service.salvarAutor(requestDto)

        responseObserver.onNext(grpcResponse)
        responseObserver.onCompleted()
    }

    override fun criaCategoria(
        request: NovaCategoriaRequest,
        responseObserver: StreamObserver<NovaCategoriaResponse>
    ) {
        val requestDto = request.toDto()
        val grpcResponse = service.salvarCategoria(requestDto)

        responseObserver.onNext(grpcResponse)
        responseObserver.onCompleted()
    }

    override fun criaLivro(request: NovoLivroRequest, responseObserver: StreamObserver<NovoLivroResponse>) {
        val requestDto = request.toDto()

        val grpcResponse = service.salvarLivro(requestDto)

        responseObserver.onNext(grpcResponse)
        responseObserver.onCompleted()
    }

    override fun listaLivros(request: ListaLivrosRequest, responseObserver: StreamObserver<ListaLivrosResponse>) {
        val grpcResponse = service.listaLivros()

        responseObserver.onNext(grpcResponse)
        responseObserver.onCompleted()
    }

    override fun detalhaLivro(request: LivroDetalheRequest, responseObserver: StreamObserver<LivroGrpc>) {
        val grpcResponse = service.detalhaLivro(request.toDto())

        responseObserver.onNext(grpcResponse)
        responseObserver.onCompleted()
    }
}