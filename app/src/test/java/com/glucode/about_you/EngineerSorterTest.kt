package com.glucode.about_you

import com.glucode.about_you.engineers.models.Engineer
import com.glucode.about_you.mockdata.MockData
import org.junit.Assert.assertTrue
import org.junit.Test

class EngineerSorterTest {
    private val engineers: List<Engineer> = MockData.engineers

    @Test
    fun `sort engineers by years of experience ascending`() {
        val sortedEngineers = engineers.sortedBy { it.quickStats.years }

        assertTrue(isSortedByYears(sortedEngineers))
    }

    @Test
    fun `sort engineers by coffees consumed ascending`() {
        val sortedEngineers = engineers.sortedBy { it.quickStats.coffees }

        assertTrue(isSortedByCoffees(sortedEngineers))
    }

    @Test
    fun `sort engineers by bugs fixed ascending`() {
        val sortedEngineers = engineers.sortedBy { it.quickStats.bugs }

        assertTrue(isSortedByBugs(sortedEngineers))
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