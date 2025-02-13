package com.glucode.about_you.engineers

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.glucode.about_you.R
import com.glucode.about_you.data.MockData
import com.glucode.about_you.databinding.FragmentEngineersBinding
import com.glucode.about_you.engineers.models.Engineer

class EngineersFragment : Fragment() {
    private var _binding: FragmentEngineersBinding? = null

    private val binding: FragmentEngineersBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEngineersBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        setUpEngineersList(MockData.engineers)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_engineers, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_years -> {
                val sortedEngineers = MockData.engineers.sortedBy { it.name }
                setUpEngineersList(sortedEngineers)
                return true
            }

            R.id.action_coffees -> {
                val sortedEngineers = MockData.engineers.sortedBy { it.quickStats.coffees }
                setUpEngineersList(sortedEngineers)
                return true
            }

            R.id.action_bugs -> {
                val sortedEngineers = MockData.engineers.sortedBy { it.quickStats.bugs }
                setUpEngineersList(sortedEngineers)
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setUpEngineersList(engineers: List<Engineer>) {
        binding.list.adapter = EngineersRecyclerViewAdapter(engineers) {
            goToAbout(it)
        }
        val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(dividerItemDecoration)
    }

    private fun goToAbout(engineer: Engineer) {
        val bundle = Bundle().apply {
            putString("name", engineer.name)
            putString("role", engineer.role)
            putString("image_uri", engineer.profileImageUri?.toString() ?: "")
            putParcelable("quick_stats", engineer.quickStats)
        }

        findNavController().navigate(R.id.action_engineersFragment_to_aboutFragment, bundle)
    }
}