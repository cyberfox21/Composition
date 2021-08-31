package com.tatyanashkolnik.composer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tatyanashkolnik.composer.R
import com.tatyanashkolnik.composer.databinding.FragmentGameBinding
import com.tatyanashkolnik.composer.domain.entity.GameResult
import com.tatyanashkolnik.composer.domain.entity.GameSettings
import com.tatyanashkolnik.composer.domain.entity.Level
import com.tatyanashkolnik.composer.domain.entity.Question
import java.lang.RuntimeException

class GameFragment : Fragment() {

    private lateinit var level: Level

    private lateinit var gameViewModel: GameViewModel

//    private lateinit var gameSettings: GameSettings
//    private lateinit var question: Question

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
        gameViewModel.level = level

        observeViewModel()

        val gameSettings = gameViewModel.getGameSettings()
        inflateGameSettings(gameSettings)

        gameViewModel.generateQuestion()

        setListeners()
    }


    private fun inflateGameSettings(gameSettings: GameSettings) {
        with(binding) {
            tvProgress.text =
                gameSettings.minCountOfRightAnswers.toString().format(R.string.progress_answers)
        }
    }

    private fun inflateQuestion(question: Question){
        with(binding) {
            tvSum.text = question.sum.toString()
            tvVisibleNumber.text = question.visibleNumber.toString()
            tvOption1.text = question.options[0].toString()
            tvOption2.text = question.options[1].toString()
            tvOption3.text = question.options[2].toString()
            tvOption4.text = question.options[3].toString()
            tvOption5.text = question.options[4].toString()
            tvOption6.text = question.options[5].toString()
        }
    }

    private fun setListeners() {
        with(binding) {
            tvOption1.setOnClickListener {
                gameViewModel.userAnswered(
                    tvOption1.text.toString().toInt()
                )
            }
            tvOption2.setOnClickListener {
                gameViewModel.userAnswered(
                    tvOption2.text.toString().toInt()
                )
            }
            tvOption3.setOnClickListener {
                gameViewModel.userAnswered(
                    tvOption3.text.toString().toInt()
                )
            }
            tvOption4.setOnClickListener {
                gameViewModel.userAnswered(
                    tvOption4.text.toString().toInt()
                )
            }
            tvOption5.setOnClickListener {
                gameViewModel.userAnswered(
                    tvOption5.text.toString().toInt()
                )
            }
            tvOption6.setOnClickListener {
                gameViewModel.userAnswered(
                    tvOption6.text.toString().toInt()
                )
            }
        }
    }

    private fun observeViewModel() {
        gameViewModel.updateQuestion.observe(viewLifecycleOwner, object : Observer<Question>{
            override fun onChanged(t: Question?) {
                t?.let { inflateQuestion(it) }
            }
        })
        gameViewModel.userProgressBar.observe(viewLifecycleOwner, object : Observer<Int> {
            override fun onChanged(t: Int?) {
                t?.let { binding.progressBar.progress = it }
            }
        })
        gameViewModel.gameResult.observe(viewLifecycleOwner, object : Observer<GameResult> {
            override fun onChanged(t: GameResult?) {
                t?.let { launchGameResultFragment(it) }
            }

        })
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