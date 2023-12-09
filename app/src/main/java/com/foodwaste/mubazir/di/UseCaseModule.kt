package com.foodwaste.mubazir.di

import com.foodwaste.mubazir.R
import com.foodwaste.mubazir.domain.repository.UserRepository
import com.foodwaste.mubazir.domain.usecase.GetUserUseCase
import com.foodwaste.mubazir.domain.usecase.SignInUseCase
import com.foodwaste.mubazir.domain.usecase.SignUpUseCase
import com.foodwaste.mubazir.domain.usecase.ValidateEmailUseCase
import com.foodwaste.mubazir.domain.usecase.ValidateNameUseCase
import com.foodwaste.mubazir.domain.usecase.ValidatePasswordUseCase
import com.foodwaste.mubazir.domain.usecase.ValidatePhoneUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideValidateNameUseCase(): ValidateNameUseCase {
        return ValidateNameUseCase(
            errorNameFormatMessage = R.string.error_name_format,
            errorBlankMessage = R.string.error_name_is_required,
            errorMinMessage = R.string.error_name_min_char,
            errorMaxMessage = R.string.error_name_max_char
        )
    }

    @Provides
    @ViewModelScoped
    fun providateValidateEmailUseCase(): ValidateEmailUseCase {
        return ValidateEmailUseCase(
            errorBlankEmail = R.string.error_email_is_required,
            errorIncorrectMessage = R.string.error_invalid_email
        )
    }

    @Provides
    @ViewModelScoped
    fun provideValidatePhoneUseCase() : ValidatePhoneUseCase {
        return ValidatePhoneUseCase(
            errorFormatMessage = R.string.error_phone_format,
            errorBlankMessage = R.string.error_phone_is_required,
        )
    }

    @Provides
    @ViewModelScoped
    fun provideValidatePasswordUseCase(): ValidatePasswordUseCase {
        return ValidatePasswordUseCase(
            errorBlankMessage = R.string.error_password_is_required,
            errorMinMessage = R.string.error_password_min_char,
            errorMaxMessage = R.string.error_password_max_char,
            errorNotMatch = R.string.error_password_not_match
        )
    }

    @Provides
    @ViewModelScoped
    fun provideSignInUseCase(userRepository: UserRepository): SignInUseCase {
        return SignInUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSignUpUseCase(userRepository: UserRepository): SignUpUseCase {
        return SignUpUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetUserUseCase(userRepository: UserRepository): GetUserUseCase {
        return GetUserUseCase(userRepository)
    }

}