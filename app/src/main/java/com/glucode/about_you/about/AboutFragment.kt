package com.glucode.about_you.about

import android.content.ActivityNotFoundException
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.glucode.about_you.R
import com.glucode.about_you.about.views.AboutInfoCardView
import com.glucode.about_you.about.views.QuestionCardView
import com.glucode.about_you.data.MockData
import com.glucode.about_you.databinding.FragmentAboutBinding
import com.glucode.about_you.engineers.models.Engineer
import com.glucode.about_you.engineers.models.QuickStats

@Suppress("DEPRECATION")
class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null
    private var selectedImageUri: Uri? = null

    private lateinit var aboutInfoView: AboutInfoCardView
    private lateinit var imagePicker: ActivityResultLauncher<PickVisualMediaRequest>

    private val binding: FragmentAboutBinding
        get() = _binding!!

    companion object {
        const val TAG: String = "AboutFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the ActivityResultLauncher
        imagePicker = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                selectedImageUri = uri
                val engineer = MockData.engineers.first { it.name == arguments?.getString("name") }
                engineer.profileImageUri = uri
                updateAboutInfoImage(engineer)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAboutInfo()
        setUpQuestions()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        imagePicker.unregister()
    }

    private fun updateAboutInfoImage(engineer: Engineer) {
        val engineerImageUri = engineer.profileImageUri ?: ""
        if (engineerImageUri != "") {
            aboutInfoView.ivProfile.setImageURI(engineer.profileImageUri)
        }
    }

    private fun setUpAboutInfo() {
        aboutInfoView = AboutInfoCardView(requireContext())

        setUpEngineerAboutInfo()
        setUpAboutInfoImage()
        setUpAboutInfoStats()

        aboutInfoView.ivProfile.setOnClickListener {
            onImageClicked()
        }

        binding.container.addView(aboutInfoView)
    }

    private fun setUpEngineerAboutInfo() {
        aboutInfoView.tvName.text = arguments?.getString("name") ?: ""
        aboutInfoView.tvRole.text = arguments?.getString("role") ?: ""
    }

    private fun setUpAboutInfoImage() {
        val engineerImageUri = arguments?.getString("image_uri") ?: ""
        if (engineerImageUri != "") {
            aboutInfoView.ivProfile.setImageURI(Uri.parse(engineerImageUri))
        }
    }

    private fun setUpAboutInfoStats() {
        @Suppress("DEPRECATION")
        val quickStats = arguments?.getParcelable<QuickStats>("quick_stats")

        aboutInfoView.qsYears.tvStatTitle.text = getString(R.string.quick_stats_years)
        aboutInfoView.qsYears.tvStatValue.text = (quickStats?.years ?: 0).toString()

        aboutInfoView.qsCoffees.tvStatTitle.text = getString(R.string.quick_stats_coffees)
        aboutInfoView.qsCoffees.tvStatValue.text = (quickStats?.coffees ?: 0).toString()

        aboutInfoView.qsBugs.tvStatTitle.text = getString(R.string.quick_stats_bugs)
        aboutInfoView.qsBugs.tvStatValue.text = (quickStats?.bugs ?: 0).toString()
    }

    private fun onImageClicked() {
        try {
            imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        } catch (e: ActivityNotFoundException) {
            Log.d(TAG, e.toString())
            Toast.makeText(context, getString(R.string.image_picker_not_found), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setUpQuestions() {
        val engineerName = arguments?.getString("name")
        val engineer = MockData.engineers.first { it.name == engineerName }

        engineer.questions.forEach { question ->
            val questionView = QuestionCardView(requireContext())
            questionView.title = question.questionText
            questionView.answers = question.answerOptions
            questionView.selection = question.answer.index

            binding.container.addView(questionView)
        }
    }
}