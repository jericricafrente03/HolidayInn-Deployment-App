package com.bittelasia.holidayinn.presentation.register.validation

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
