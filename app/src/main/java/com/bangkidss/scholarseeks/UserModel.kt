package com.bangkidss.scholarseeks

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    var jwt_token: String? = null,
    var user_id: String? = null,
    var google_token: String? = null
) : Parcelable
