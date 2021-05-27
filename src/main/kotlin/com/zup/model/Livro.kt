package com.zup.model

import com.zup.LivroDetalheRequest
import com.zup.NovoLivroRequest
import com.zup.exception.NaoExisteException
import com.zup.repository.AutorRepository
import com.zup.repository.CategoriaRepository
import com.zup.util.toLocalDateTime
import io.micronaut.core.annotation.Introspected
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.*

@Entity
class Livro(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val titulo: String,
    val resumo: String,
    val sumario: String,
    val isbn: String,
    val dataLancamento: LocalDateTime,
    val paginas: Int,
    @field:OneToOne
    val categoria: Categoria,
    @field:ManyToOne
    val autor: Autor
)

@Introspected
data class NovoLivroDto(
    @field:NotBlank
    val titulo: String?,
    @field:NotBlank
    @field:Size(max = 500)
    val resumo: String?,
    @field:NotBlank
    val sumario: String?,
    @field:NotNull
    @field:Min(100)
    val paginas: Int?,
    @field:NotBlank
    val isbn: String?,
    @field:NotNull
    @field:FutureOrPresent
    val dataLancamento: LocalDateTime?,
    @field:NotNull
    val categoriaId: Long?,
    @field:NotNull
    val autorId: Long?
){
    fun toModel(autorRepository: AutorRepository, categoriaRepository: CategoriaRepository): Livro {
        val autor = autorRepository.findById(categoriaId!!).orElseThrow { throw NaoExisteException() }
        val categoria = categoriaRepository.findById(autorId!!).orElseThrow { throw NaoExisteException() }

        return Livro(
            titulo = titulo!!,
            resumo = resumo!!,
            sumario = sumario!!,
            isbn = isbn!!,
            dataLancamento = dataLancamento!!,
            categoria = categoria,
            autor = autor,
            paginas = paginas!!
        )
    }
}

fun NovoLivroRequest.toDto(): NovoLivroDto {
    return NovoLivroDto(
        titulo = titulo,
        resumo = resumo,
        sumario = sumario,
        isbn = isbn,
        dataLancamento = dataLancamento.toLocalDateTime(),
        categoriaId = idCategoria,
        autorId = idAutor,
        paginas = paginas
    )
}

@Introspected
data class DetalheLivroDto(
    @field:NotNull
    val id: Long?
)

fun LivroDetalheRequest.toDto(): DetalheLivroDto {
    return DetalheLivroDto(id = id)
}

