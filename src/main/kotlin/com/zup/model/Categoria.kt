package com.zup.model

import com.zup.NovaCategoriaRequest
import com.zup.validation.CategoriaUnica
import io.micronaut.core.annotation.Introspected
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity
class Categoria(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val nome: String
)

@Introspected
data class NovaCategoriaDto(
    @field:NotBlank
    val nome: String?
){
    fun toModel(): Categoria {
        return Categoria(nome = nome!!)
    }
}

fun NovaCategoriaRequest.toDto(): NovaCategoriaDto {
    return NovaCategoriaDto(nome = nome)
}
