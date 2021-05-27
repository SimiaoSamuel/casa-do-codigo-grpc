package com.zup.repository

import com.zup.model.Livro
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface LivroRepository : JpaRepository<Livro,Long> {
    fun existsByTitulo(titulo: String): Boolean
}