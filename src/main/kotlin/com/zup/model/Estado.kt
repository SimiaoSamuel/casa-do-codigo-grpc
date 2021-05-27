package com.zup.model

import com.zup.NovoEstadoRequest
import com.zup.exception.NaoExisteException
import com.zup.repository.PaisRepository
import io.micronaut.core.annotation.Introspected
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
class Estado(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    val nome: String,
    @field:ManyToOne
    val pais: Pais
)

@Introspected
data class EstadoDto(
    @field:NotBlank
    val nome: String?,
    @field:NotNull
    val paisId: Long?
) {
    fun toModel(repository: PaisRepository): Estado {
        val pais = repository.findById(paisId!!).orElseThrow { throw NaoExisteException() }

        return Estado(
            nome = nome!!,
            pais = pais
        )
    }
}

fun NovoEstadoRequest.toDto(): EstadoDto {
    return EstadoDto(
        nome = nome,
        paisId = idPais
    )
}

