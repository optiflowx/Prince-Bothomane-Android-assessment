package com.glucode.about_you.engineers

import androidx.lifecycle.ViewModel
import com.glucode.about_you.data.MockData
import com.glucode.about_you.engineers.models.Engineer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class EngineersViewModel : ViewModel() {
    private val engineersData = MockData.engineers

    private var _engineers = MutableStateFlow<List<Engineer>>(engineersData)

    val engineers get() = _engineers.asStateFlow()

    fun sortEngineersByYears(): Boolean {
        sortEngineers(engineersData.sortedBy { it.quickStats.years }.toMutableList())
        return true
    }

    fun sortEngineersByCoffees(): Boolean {
        sortEngineers(engineersData.sortedBy { it.quickStats.coffees }.toMutableList())
        return true
    }

    fun sortEngineersByBugs(): Boolean {
        sortEngineers(engineersData.sortedBy { it.quickStats.bugs }.toMutableList())
        return true
    }

    private fun sortEngineers(sortedEngineers: List<Engineer>) {
        _engineers.value = sortedEngineers
    }
}