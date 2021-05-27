package com.zup.service

import com.zup.DetalhaPaisResponse
import com.zup.EstadoGrpc
import com.zup.NovoEstadoResponse
import com.zup.NovoPaisResponse
import com.zup.annotation.OpenClass
import com.zup.exception.NaoExisteException
import com.zup.exception.ValorDuplicadoException
import com.zup.model.DetalhaPaisDto
import com.zup.model.EstadoDto
import com.zup.model.PaisDto
import com.zup.repository.EstadoRepository
import com.zup.repository.PaisRepository
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Singleton
@OpenClass
class PaisEstadoService(
    @Inject private val paisRepository: PaisRepository,
    @Inject private val estadoRepository: EstadoRepository
) {

    @Transactional
    fun salvaPais(@Valid pais: PaisDto): NovoPaisResponse {
        if(paisRepository.existsByNome(pais.nome!!))
            throw ValorDuplicadoException()
        val model = pais.toModel()
        val paisSalvo = paisRepository.save(model)

        return NovoPaisResponse.newBuilder().setId(paisSalvo.id).build()
    }

    @Transactional
    fun salvaEstado(@Valid estado: EstadoDto): NovoEstadoResponse {
        if(estadoRepository.existsByNome(estado.nome!!))
            throw ValorDuplicadoException()
        val model = estado.toModel(paisRepository)
        val estadoSalvo = estadoRepository.save(model)

        return NovoEstadoResponse.newBuilder().setId(estadoSalvo.id).build()
    }

    fun DetalhaPais(@Valid pais: DetalhaPaisDto): DetalhaPaisResponse {
        val model = paisRepository.findById(pais.id!!).orElseThrow { throw NaoExisteException() }

        val estados = model.estados.map {
            EstadoGrpc.newBuilder()
                .setNome(it.nome)
                .build()
        }

        return DetalhaPaisResponse.newBuilder()
            .setId(model.id)
            .setNome(model.nome)
            .addAllEstados(estados)
            .build()
    }
}