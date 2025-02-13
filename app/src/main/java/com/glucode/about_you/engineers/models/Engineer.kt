package com.glucode.about_you.engineers.models

import android.net.Uri

data class Engineer(
    val name: String,
    val role: String,
    private var _profileImageUri: Uri? = null,
    val quickStats: QuickStats,
    val questions: List<Question>,
) {
    var profileImageUri: Uri?
        get() = _profileImageUri
        set(value) {
            _profileImageUri = value
        }
}