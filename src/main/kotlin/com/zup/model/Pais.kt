package com.zup.model

import com.zup.DetalhaPaisRequest
import com.zup.NovoPaisRequest
import io.micronaut.core.annotation.Introspected
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
class Pais(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @field:Column(nullable = false)
    val nome: String,
    @field:OneToMany(mappedBy = "pais", fetch = FetchType.EAGER)
    val estados: List<Estado> = listOf()
)

@Introspected
data class PaisDto(
    @field:NotBlank
    val nome: String?
) {
    fun toModel(): Pais {
        return Pais(nome = nome!!)
    }
}

fun NovoPaisRequest.toDto(): PaisDto {
    return PaisDto(nome)
}

@Introspected
data class DetalhaPaisDto(
    @field:NotNull
    val id: Long?
)

fun DetalhaPaisRequest.toDto(): DetalhaPaisDto {
    return DetalhaPaisDto(id)
}