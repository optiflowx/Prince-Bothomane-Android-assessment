package com.glucode.about_you.about

import android.content.ActivityNotFoundException
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.glucode.about_you.R
import com.glucode.about_you.about.views.AboutInfoCardView
import com.glucode.about_you.about.views.QuestionCardView
import com.glucode.about_you.databinding.FragmentAboutBinding
import com.glucode.about_you.engineers.models.Engineer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null

    private lateinit var imagePicker: ActivityResultLauncher<PickVisualMediaRequest>

    private val viewModel by viewModels<AboutViewModel>()
    private val binding: FragmentAboutBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.setEngineer(arguments?.getString("name"))

        imagePicker = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                viewModel.updateEngineerImage(uri.toString())
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        val aboutInfoCardView = AboutInfoCardView(requireContext())

        viewModel.engineer.value.also { engineer ->
            if (engineer != null) {
                setUpAboutInfo(engineer, aboutInfoCardView)
            }
        }

        lifecycleScope.launch {
            viewModel.engineer.collectLatest { engineer ->
                if (engineer != null) {
                    setEngineerImageUri(engineer.defaultImageName, aboutInfoCardView)
                }
            }
        }
        
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpQuestions()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        imagePicker.unregister()
    }

    internal fun setEngineerImageUri(imageName: String, aboutInfoCardView: AboutInfoCardView) {
        if (imageName.isNotBlank()) {
            aboutInfoCardView.ivProfile.setImageURI(Uri.parse(imageName))
        }
    }

    private fun setUpAboutInfo(engineer: Engineer, aboutInfoCardView: AboutInfoCardView) {
        setEngineerImageUri(engineer.defaultImageName, aboutInfoCardView)

        aboutInfoCardView.tvName.text = engineer.name
        aboutInfoCardView.tvRole.text = engineer.role

        aboutInfoCardView.qsYears.tvStatTitle.text = getString(R.string.quick_stats_years)
        aboutInfoCardView.qsYears.tvStatValue.text = (engineer.quickStats.years).toString()

        aboutInfoCardView.qsCoffees.tvStatTitle.text = getString(R.string.quick_stats_coffees)
        aboutInfoCardView.qsCoffees.tvStatValue.text = (engineer.quickStats.coffees).toString()

        aboutInfoCardView.qsBugs.tvStatTitle.text = getString(R.string.quick_stats_bugs)
        aboutInfoCardView.qsBugs.tvStatValue.text = (engineer.quickStats.bugs).toString()

        aboutInfoCardView.ivProfile.setOnClickListener { onImageClicked() }

        binding.container.addView(aboutInfoCardView)
    }

    private fun setUpQuestions() {
        viewModel.engineer.value?.questions?.forEach { question ->
            val questionView = QuestionCardView(requireContext())
            questionView.title = question.questionText
            questionView.answers = question.answerOptions
            questionView.selection = question.answer.index

            binding.container.addView(questionView)
        }
    }

    private fun onImageClicked() {
        try {
            imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, getString(R.string.image_picker_not_found), Toast.LENGTH_SHORT)
                .show()
        }
    }
}