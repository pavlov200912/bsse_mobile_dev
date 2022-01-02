package com.example.myapplication.data.net

import com.example.myapplication.data.net.request.CreateProfileRequest
import com.example.myapplication.data.net.request.RefreshAuthTokensRequest
import com.example.myapplication.data.net.request.SignInWithEmailRequest
import com.example.myapplication.data.net.response.VerificationTokenResponse
import com.example.myapplication.data.net.response.error.*
import com.example.myapplication.entity.AuthTokens
import com.example.myapplication.entity.Post
import com.example.myapplication.entity.User
import com.haroldadmin.cnradapter.NetworkResponse

class MockApi : Api {
    private val users = mutableListOf<User>()

    private val token = AuthTokens(
        accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dnZWRJbkFzIjoiYWRtaW4iLCJpYXQiOjE0MjI3Nzk2MzgsImV4cCI6MTY0MDg3MTc3MX0.gzSraSYS8EXBxLN_oWnFSRgCzcmJmMjLiuyu5CSpyHI",
        refreshToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dnZWRJbkFzIjoiYWRtaW4iLCJpYXQiOjE0MjI3Nzk2MzgsImV4cCI6MTY0MDg3MTc3MX0.gzSraSYS8EXBxLN_oWnFSRgCzcmJmMjLiuyu5CSpyHI",
        accessTokenExpiration = 1640871771000,
        refreshTokenExpiration = 1640871771000,
    )

    override suspend fun getUsers(): NetworkResponse<List<User>, Unit> {
        return NetworkResponse.Success(emptyList(), code=200)
        return NetworkResponse.Success(
            listOf(
                User(
                    avatarUrl = "https://memepedia.ru/wp-content/uploads/2020/10/big-floppa-meme.png",
                    userName = "Shlepa",
                    groupName = "MCS",
                    id = 1,
                    firstName = "Shlepa",
                    lastName = "Pelmenev"
                )
            ),
            code = 200
        )
    }

    override suspend fun signInWithEmail(request: SignInWithEmailRequest): NetworkResponse<AuthTokens, SignInWithEmailErrorResponse> {
        return NetworkResponse.Success(
            token,
            code = 200
        )
    }

    override suspend fun sendRegistrationVerificationCode(email: String): NetworkResponse<Unit, SendRegistrationVerificationCodeErrorResponse> {
        return NetworkResponse.Success(Unit, code = 200)
    }

    override suspend fun verifyRegistrationCode(
        code: String,
        email: String
    ): NetworkResponse<VerificationTokenResponse, VerifyRegistrationCodeErrorResponse> {
        if (code == "123456") {
            return NetworkResponse.Success(VerificationTokenResponse(code), code = 200)
        }
        return NetworkResponse.ServerError(
            VerifyRegistrationCodeErrorResponse(listOf(Error("wrong code!"))),
            code = 400
        )
    }

    override suspend fun refreshAuthTokens(request: RefreshAuthTokensRequest): NetworkResponse<AuthTokens, RefreshAuthTokensErrorResponse> {
        TODO("Not yet implemented")
    }


    override suspend fun createProfile(request: CreateProfileRequest): NetworkResponse<AuthTokens, CreateProfileErrorResponse> {
        users.add(
            User(
                1,
                "Ivan",
                "Olejnik",
                "olen",
                "https://pixnio.com/free-images/2021/03/30/2021-03-30-11-06-07-1800x1200.jpg",
                "mkn",
            )
        )
        return NetworkResponse.Success(token, code = 200)
    }

    override suspend fun getPosts(): NetworkResponse<List<Post>, Unit> {
        return NetworkResponse.Success(
            listOf(
                Post(
                    id = 1,
                    linkUrl = "https://developer.android.com/training/dependency-injection/hilt-android",
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Cawood_sword_-_hilt_-_YORYM_2007_3086.JPG/1200px-Cawood_sword_-_hilt_-_YORYM_2007_3086.JPG",
                    title = "Android hilt",
                    text = "Dependency injection is very important",
                    createdAt = "",
                    updatedAt = ""
                ),
                Post(
                    id = 2,
                    linkUrl = null,
                    imageUrl = "https://static01.nyt.com/images/2021/09/14/science/07CAT-STRIPES/07CAT-STRIPES-mediumSquareAt3X-v2.jpg",
                    title = "Cat",
                    text = "Just cat",
                    createdAt = "",
                    updatedAt = ""
                ),
                Post(
                    id = 3,
                    title = "Hello world",
                    text = "Some text with post",
                    createdAt = "",
                    updatedAt = "",
                    linkUrl = null,
                    imageUrl = null
                )
            ),
            code = 200
        )
    }

    override suspend fun getProfile(): NetworkResponse<User, Unit> {
        return NetworkResponse.Success(
            User(
                avatarUrl = "https://memepedia.ru/wp-content/uploads/2020/10/big-floppa-meme.png",
                userName = "Shlepa",
                groupName = "MCS",
                id = 1,
                firstName = "Shlepa",
                lastName = "Pelmenev"
            ),
            code = 200,
        )
    }
}