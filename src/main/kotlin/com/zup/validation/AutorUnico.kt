package com.zup.validation

import com.zup.repository.AutorRepository
import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [AutorUnicoValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class AutorUnico(
    val message: String = "Autor precisa ser único",
    val payload: Array<KClass<Payload>> = [],
    val groups: Array<KClass<Any>> = []
)

@Singleton
class AutorUnicoValidator(@Inject val repository: AutorRepository) : ConstraintValidator<AutorUnico, String> {
    override fun isValid(
        value: String?,
        annotationMetadata: AnnotationValue<AutorUnico>,
        context: ConstraintValidatorContext
    ): Boolean {
        if (value == null)
            return false

        return !repository.existsByEmail(value)
    }
}
