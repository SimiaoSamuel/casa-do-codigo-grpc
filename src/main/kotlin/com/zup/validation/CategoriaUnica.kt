package com.zup.validation

import com.zup.repository.CategoriaRepository
import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented()
@Constraint(validatedBy = [])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class CategoriaUnica(
    val message: String = "Categoria precisa ser única"
)

@Singleton
class CategoriaUnicaValidator(@Inject val repository: CategoriaRepository): ConstraintValidator<CategoriaUnica, String> {
    override fun isValid(
        value: String?,
        annotationMetadata: AnnotationValue<CategoriaUnica>,
        context: ConstraintValidatorContext
    ): Boolean {
        if (value == null)
            return false

        return !repository.existsByNome(value)
    }
}