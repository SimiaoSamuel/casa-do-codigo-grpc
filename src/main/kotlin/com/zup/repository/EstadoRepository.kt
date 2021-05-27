package com.zup.repository

import com.zup.model.Estado
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface EstadoRepository: JpaRepository<Estado,Long> {
    fun existsByNome(nome: String): Boolean
}