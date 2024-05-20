package com.bittelasia.holidayinn.presentation.register.validation

import java.util.regex.Matcher
import java.util.regex.Pattern

class ValidateIp {
    fun getIpErrorIdOrNull(input: String): ValidationResult {
        val ipFormat: Pattern = Pattern.compile(
            "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
                    + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
                    + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
                    + "|[1-9][0-9]|[0-9]))"
        )
        val matcher: Matcher = ipFormat.matcher(input)
        if (input.isBlank() || input.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The IP address can't be blank"
            )
        }
        if (!matcher.matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "That's not a valid IP Address"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}