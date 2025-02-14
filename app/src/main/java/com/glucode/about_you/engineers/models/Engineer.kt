package com.glucode.about_you.engineers.models

import android.net.Uri

data class Engineer(
    val name: String,
    val role: String,
    private var _defaultImageName: Uri? = null,
    val quickStats: QuickStats,
    val questions: List<Question>,
) {
    var defaultImageName: Uri?
        get() = _defaultImageName
        set(value) {
            _defaultImageName = value
        }
}