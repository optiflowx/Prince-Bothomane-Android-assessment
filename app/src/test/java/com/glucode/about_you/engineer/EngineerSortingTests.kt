package com.glucode.about_you.engineer

import com.glucode.about_you.engineers.EngineersViewModel
import com.glucode.about_you.engineers.models.Engineer
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.ranges.until

class EngineerSortingTests {
    private lateinit var viewModel: EngineersViewModel

    @Before
    fun setup() {
        viewModel = EngineersViewModel()
    }

    @Test
    fun `sort engineers by years of experience ascending`() {
        viewModel.sortEngineersByYears()
        assertTrue(isSortedByYears(viewModel.engineers.value))
    }

    @Test
    fun `sort engineers by coffees consumed ascending`() {
        viewModel.sortEngineersByCoffees()
        assertTrue(isSortedByCoffees(viewModel.engineers.value))
    }

    @Test
    fun `sort engineers by bugs fixed ascending`() {
        viewModel.sortEngineersByBugs()
        assertTrue(isSortedByBugs(viewModel.engineers.value))
    }

    // Helper functions to check if the list is sorted correctly
    private fun isSortedByYears(engineers: List<Engineer>): Boolean {
        for (i in 0 until engineers.size - 1) {
            if (engineers[i].quickStats.years > engineers[i + 1].quickStats.years) {
                return false
            }
        }
        return true
    }

    private fun isSortedByCoffees(engineers: List<Engineer>): Boolean {
        for (i in 0 until engineers.size - 1) {
            if (engineers[i].quickStats.coffees > engineers[i + 1].quickStats.coffees) {
                return false
            }
        }
        return true
    }

    private fun isSortedByBugs(engineers: List<Engineer>): Boolean {
        for (i in 0 until engineers.size - 1) {
            if (engineers[i].quickStats.bugs > engineers[i + 1].quickStats.bugs) {
                return false
            }
        }
        return true
    }
}