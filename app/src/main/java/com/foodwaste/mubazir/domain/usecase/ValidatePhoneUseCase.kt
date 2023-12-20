package com.foodwaste.mubazir.domain.usecase

class ValidatePhoneUseCase(
/**
     * [Int] reference to string resource.
     * You can use string resource to inject these values
     * */
    private val errorFormatMessage: Int,
    private val errorBlankMessage: Int,

) {

    operator fun invoke(phone: String): Int? {
        val phoneRegex = "^[1-9]\\d{10,14}$".toRegex()
        if(!phone.matches(phoneRegex)) return errorFormatMessage

        if (phone.isBlank()) return errorBlankMessage

        return null
    }
}
