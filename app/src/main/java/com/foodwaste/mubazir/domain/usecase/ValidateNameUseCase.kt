package com.foodwaste.mubazir.domain.usecase

import androidx.annotation.StringRes

class ValidateNameUseCase(
    /**
     * [Int] reference to string resource.
     * You can use string resource to inject these values
     * */
    @StringRes private val errorNameFormatMessage: Int,
    @StringRes private val errorBlankMessage: Int,
    @StringRes private val errorMinMessage: Int,
    @StringRes private val errorMaxMessage: Int,
) {

    operator fun invoke(name: String): Int? {
        val nameRegex = "[a-zA-Z]+\\s[a-zA-Z]+".toRegex()
        if(!name.matches(nameRegex)) return errorNameFormatMessage

        if (name.isBlank()) return errorBlankMessage

        if (name.length < 3) return errorMinMessage

        if (name.length > 30) return errorMaxMessage

        return null
    }
}