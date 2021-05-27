package com.zup.repository

import com.zup.model.Categoria
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface CategoriaRepository: JpaRepository<Categoria, Long> {
    fun existsByNome(nome: String): Boolean
}