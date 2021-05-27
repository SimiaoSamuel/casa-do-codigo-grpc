package com.zup.repository

import com.zup.model.Autor
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface AutorRepository : JpaRepository<Autor, Long> {
    fun existsByEmail(email: String) : Boolean
}