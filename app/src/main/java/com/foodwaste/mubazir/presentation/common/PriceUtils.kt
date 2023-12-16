package com.foodwaste.mubazir.presentation.common


object PriceUtils {
    fun toRupiah(price: Int?): String {
        if(price == null) return ""
        if(price == 0) return "Free"
        val formattedPrice = StringBuilder("Rp ")

        val priceString = price.toString()
        val length = priceString.length

        for (i in 0 until length) {
            formattedPrice.append(priceString[i])

            val remainingDigits = length - i - 1
            if (remainingDigits > 0 && remainingDigits % 3 == 0) {
                formattedPrice.append('.')
            }
        }

        return formattedPrice.toString()
    }
}