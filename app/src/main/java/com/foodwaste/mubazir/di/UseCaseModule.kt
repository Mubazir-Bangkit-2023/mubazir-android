package com.foodwaste.mubazir.di

import com.foodwaste.mubazir.R
import com.foodwaste.mubazir.domain.repository.FoodPostRepository
import com.foodwaste.mubazir.domain.repository.UserRepository
import com.foodwaste.mubazir.domain.usecase.BrowseUseCase
import com.foodwaste.mubazir.domain.usecase.DarkThemeUseCase
import com.foodwaste.mubazir.domain.usecase.DeletePostUseCase
import com.foodwaste.mubazir.domain.usecase.FoodClassificationUseCase
import com.foodwaste.mubazir.domain.usecase.GetCurrentLocationUseCase
import com.foodwaste.mubazir.domain.usecase.GetDetailPostUseCase
import com.foodwaste.mubazir.domain.usecase.GetFoodPostMapViewUseCase
import com.foodwaste.mubazir.domain.usecase.GetNearbyFoodIngredientsRecommendationUseCase
import com.foodwaste.mubazir.domain.usecase.GetNearbyHomeFoodRecommendationUseCase
import com.foodwaste.mubazir.domain.usecase.GetNearbyRecommendationUseCase
import com.foodwaste.mubazir.domain.usecase.GetNearbyRestaurantRecommendationUseCase
import com.foodwaste.mubazir.domain.usecase.GetStoredLocationUseCase
import com.foodwaste.mubazir.domain.usecase.GetUserPostsUseCase
import com.foodwaste.mubazir.domain.usecase.GetUserUseCase
import com.foodwaste.mubazir.domain.usecase.SetStoredLocationUseCase
import com.foodwaste.mubazir.domain.usecase.SignInUseCase
import com.foodwaste.mubazir.domain.usecase.SignOutUseCase
import com.foodwaste.mubazir.domain.usecase.SignUpUseCase
import com.foodwaste.mubazir.domain.usecase.UploadFoodPostUseCase
import com.foodwaste.mubazir.domain.usecase.ValidateEmailUseCase
import com.foodwaste.mubazir.domain.usecase.ValidateNameUseCase
import com.foodwaste.mubazir.domain.usecase.ValidatePasswordUseCase
import com.foodwaste.mubazir.domain.usecase.ValidatePhoneUseCase
import com.google.android.gms.location.FusedLocationProviderClient
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

    @Provides
    @ViewModelScoped
    fun provideSignOutUseCase(userRepository: UserRepository): SignOutUseCase {
        return SignOutUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetCurrentLocationUseCase(fusedLocationProviderClient: FusedLocationProviderClient): GetCurrentLocationUseCase {
        return GetCurrentLocationUseCase(fusedLocationProviderClient)
    }

    @Provides
    @ViewModelScoped
    fun provideSetStoredLocationUseCase(userRepository: UserRepository): SetStoredLocationUseCase {
        return SetStoredLocationUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetStoredLocationUseCase(userRepository: UserRepository): GetStoredLocationUseCase {
        return GetStoredLocationUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideBrowseUseCase(foodPostRepository: FoodPostRepository): BrowseUseCase {
        return BrowseUseCase(foodPostRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideFoodClassificationUseCase(foodPostRepository: FoodPostRepository): FoodClassificationUseCase {
        return FoodClassificationUseCase(foodPostRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetDetailPostUseCase(foodPostRepository: FoodPostRepository): GetDetailPostUseCase {
        return GetDetailPostUseCase(foodPostRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideUploadFoodPostUseCase(foodPostRepository: FoodPostRepository, userRepository: UserRepository): UploadFoodPostUseCase {
        return UploadFoodPostUseCase(foodPostRepository, userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetNearbyRecommendationUseCase(foodPostRepository: FoodPostRepository): GetNearbyRecommendationUseCase {
        return GetNearbyRecommendationUseCase(foodPostRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetNearbyRestaurantRecommendationUseCase(foodPostRepository: FoodPostRepository): GetNearbyRestaurantRecommendationUseCase {
        return GetNearbyRestaurantRecommendationUseCase(foodPostRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetNearbyHomeFoodRecommendationUseCase(foodPostRepository: FoodPostRepository): GetNearbyHomeFoodRecommendationUseCase {
        return GetNearbyHomeFoodRecommendationUseCase(foodPostRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetNearbyFoodIngredientsRecommendationUseCase(foodPostRepository: FoodPostRepository): GetNearbyFoodIngredientsRecommendationUseCase {
        return GetNearbyFoodIngredientsRecommendationUseCase(foodPostRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideDarkThemeUseCase(userRepository: UserRepository): DarkThemeUseCase {
        return DarkThemeUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetFoodPostMapViewUseCase(foodPostRepository: FoodPostRepository): GetFoodPostMapViewUseCase {
        return GetFoodPostMapViewUseCase(foodPostRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetUserPostsUseCase(userRepository: UserRepository): GetUserPostsUseCase {
        return GetUserPostsUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideDeletePostUseCase(foodPostRepository: FoodPostRepository, userRepository: UserRepository) : DeletePostUseCase {
        return DeletePostUseCase(foodPostRepository, userRepository)
    }

}