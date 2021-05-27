package com.zup.model

import com.zup.NovoAutorRequest
import com.zup.validation.AutorUnico
import io.micronaut.core.annotation.Introspected
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
class Autor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    @field:Column(nullable = false)
    val email: String,
    @field:Column(nullable = false)
    val nome: String,
    @field:Column(nullable = false, length = 400)
    val descricao: String,
    @field:Column(nullable = false)
    @field:CreationTimestamp
    val criadoEm: LocalDateTime = LocalDateTime.now()
)

@Introspected
data class AutorRequestDto(
    @field:NotBlank
    @field:Email
    val email: String?,
    @field:NotBlank
    val nome: String?,
    @field:NotBlank
    @field:Size(max = 400)
    val descricao: String?
) {
    fun toModel(): Autor {
        return Autor(
            nome = this.nome!!,
            email = this.email!!,
            descricao = this.descricao!!
        )
    }
}

fun NovoAutorRequest.toDto(): AutorRequestDto {
    return AutorRequestDto(
        email = this.email,
        nome = this.nome,
        descricao = this.descricao
    )
}