package com.zup.service

import com.zup.*
import com.zup.annotation.OpenClass
import com.zup.exception.NaoExisteException
import com.zup.exception.ValorDuplicadoException
import com.zup.model.*
import com.zup.repository.AutorRepository
import com.zup.repository.CategoriaRepository
import com.zup.repository.LivroRepository
import com.zup.util.toTimestamp
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@OpenClass
@Singleton
class LivroService(
    @Inject private val autorRepository: AutorRepository,
    @Inject private val categoriaRepository: CategoriaRepository,
    @Inject private val livroRepository: LivroRepository
) {

    @Transactional
    fun salvarAutor(@Valid autor: AutorRequestDto): NovoAutorResponse? {
        if(autorRepository.existsByEmail(autor.email!!))
            throw ValorDuplicadoException()

        val autorSalvo = autorRepository.save(autor.toModel())

        return NovoAutorResponse.newBuilder().setId(autorSalvo.id).build()
    }

    @Transactional
    fun salvarCategoria(@Valid categoria: NovaCategoriaDto): NovaCategoriaResponse {
        if(categoriaRepository.existsByNome(categoria.nome!!))
            throw ValorDuplicadoException()

        val categoriaSalva = categoriaRepository.save(categoria.toModel())

        return NovaCategoriaResponse.newBuilder().setId(categoriaSalva.id).build()
    }

    @Transactional
    fun salvarLivro(@Valid livro: NovoLivroDto): NovoLivroResponse {
        if (livroRepository.existsByTitulo(livro.titulo!!))
            throw ValorDuplicadoException()
        val livroSalvo = livroRepository.save(livro.toModel(autorRepository,categoriaRepository))

        return NovoLivroResponse.newBuilder().setId(livroSalvo.id).build()
    }

    fun listaLivros(): ListaLivrosResponse {
        val listaDeLivros = livroRepository.findAll().map {
            LivroGrpc.newBuilder()
                .setDataLancamento(it.dataLancamento.toTimestamp())
                .setId(it.id)
                .setIdAutor(it.autor.id)
                .setIsbn(it.isbn)
                .setPaginas(it.paginas)
                .setResumo(it.resumo)
                .setIdCategoria(it.categoria.id)
                .setTitulo(it.titulo)
                .setSumario(it.sumario)
                .build()
        }

        return ListaLivrosResponse.newBuilder().addAllListaDeLivros(listaDeLivros).build()
    }

    fun detalhaLivro(@Valid idlivro: DetalheLivroDto): LivroGrpc? {
        val livro = livroRepository.findById(idlivro.id!!)
            .orElseThrow { throw NaoExisteException() }

        return LivroGrpc.newBuilder()
            .setDataLancamento(livro.dataLancamento.toTimestamp())
            .setId(livro.id)
            .setIdAutor(livro.autor.id)
            .setIsbn(livro.isbn)
            .setPaginas(livro.paginas)
            .setResumo(livro.resumo)
            .setIdCategoria(livro.categoria.id)
            .setTitulo(livro.titulo)
            .setSumario(livro.sumario)
            .build()
    }
}