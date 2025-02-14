package com.glucode.about_you.about

import androidx.lifecycle.ViewModel
import com.glucode.about_you.data.MockData
import com.glucode.about_you.engineers.models.Engineer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AboutViewModel : ViewModel() {
    private val engineersData = MockData.engineers

    private var _engineer = MutableStateFlow<Engineer?>(null)

    val engineer
        get() = _engineer.asStateFlow()

    fun updateEngineerImage(imageName: String) {
        _engineer.value = engineer.value?.copy(defaultImageName = imageName)

        engineersData.first { it.name == _engineer.value?.name }.also {
            val index = engineersData.indexOf(it)

            if (index != -1) {
                MockData.engineers[index] = _engineer.value!!
            }
        }
    }

    fun setEngineer(name: String?) {
        _engineer.value = engineersData.first { it.name == name }
    }
}