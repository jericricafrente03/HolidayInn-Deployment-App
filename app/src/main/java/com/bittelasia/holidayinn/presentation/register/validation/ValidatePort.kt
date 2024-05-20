package com.bittelasia.holidayinn.presentation.register.validation

class ValidatePort {
    fun getPortErrorIdOrNull(input: String) : ValidationResult {
        if (input.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "The Port can't be blank"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}