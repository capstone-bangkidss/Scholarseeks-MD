package com.bangkidss.scholarseeks

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    var jwt_token: String? = null,
    var user_id: String? = null,
    var id_token: String? = null,
    var user_name: String? = null,
    var user_email: String? = null,
    var user_photo: String? = null
) : Parcelable
