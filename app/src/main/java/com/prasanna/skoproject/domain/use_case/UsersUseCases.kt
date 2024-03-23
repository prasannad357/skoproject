package com.prasanna.skoproject.domain.use_case

import javax.inject.Inject

data class UsersUseCases @Inject constructor (
    val getUserList: GetUserList
)