package com.tatyanashkolnik.composer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tatyanashkolnik.composer.R
import com.tatyanashkolnik.composer.databinding.FragmentGameBinding
import com.tatyanashkolnik.composer.domain.entity.GameResult
import com.tatyanashkolnik.composer.domain.entity.GameSettings
import com.tatyanashkolnik.composer.domain.entity.Level
import java.lang.RuntimeException

class GameFragment : Fragment() {

    private lateinit var level: Level

    private lateinit var gameViewModel: GameViewModel

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding = null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameViewModel = ViewModelProvider(requireActivity()).get(GameViewModel::class.java)

        val gameSettings = gameViewModel.getGameSettings()

        inflateViews(gameSettings)
        observeViewModel()

//        val testGameResult = GameResult(
//            true,
//            2,
//            2,
//            GameSettings(
//                0,
//                1,
//                2,
//                3
//            )
//        )
//        binding.tvSum.setOnClickListener { launchGameResultFragment(testGameResult) }
    }


    private fun inflateViews(gameSettings: GameSettings) {
        with(binding) {
            tvProgress.text =
                gameSettings.minCountOfRightAnswers.toString().format(R.string.progress_answers)
        }
    }

    private fun observeViewModel() {
        TODO("Not yet implemented")
    }


    private fun parseArgs() {
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }

    private fun launchGameResultFragment(gameResult: GameResult) {
        val fragment = GameResultFragment.newInstance(gameResult)
        requireActivity().supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.main_container, fragment)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        const val NAME = "GameFragment"
        const val KEY_LEVEL = "level"

        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_LEVEL, level)// enum неявно реализует интерфейс Serializable
                }
            }
        }

    }
}