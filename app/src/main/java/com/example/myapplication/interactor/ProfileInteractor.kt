package com.example.myapplication.interactor

import com.example.myapplication.entity.User
import com.example.myapplication.repository.ProfileRepository
import com.haroldadmin.cnradapter.NetworkResponse
import javax.inject.Inject


class ProfileInteractor @Inject constructor(
    private val profileRepository: ProfileRepository
) {

    suspend fun getProfile(): NetworkResponse<User, Unit> {
        val response = profileRepository.getProfile()
        when (response) {
            is NetworkResponse.Success -> {

            }
        }
        return response
    }
}