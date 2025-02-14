package com.glucode.about_you.engineers

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.glucode.about_you.R
import com.glucode.about_you.databinding.FragmentEngineersBinding
import com.glucode.about_you.engineers.models.Engineer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EngineersFragment : Fragment() {
    private var _binding: FragmentEngineersBinding? = null

    private val viewModel by viewModels<EngineersViewModel>()
    private val binding: FragmentEngineersBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEngineersBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        //Reactively get latest state from the Engineers Flow
        lifecycleScope.launch {
            viewModel.engineers.collectLatest { engineers ->
                setUpEngineersList(engineers)
            }
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_engineers, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_years -> viewModel.sortEngineersByYears()
            R.id.action_coffees -> viewModel.sortEngineersByCoffees()
            R.id.action_bugs -> viewModel.sortEngineersByBugs()
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
            putString("image_uri", engineer.defaultImageName?.toString() ?: "")
            putParcelable("quick_stats", engineer.quickStats)
        }

        findNavController().navigate(R.id.action_engineersFragment_to_aboutFragment, bundle)
    }
}