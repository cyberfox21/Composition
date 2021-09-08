package com.tatyanashkolnik.composer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tatyanashkolnik.composer.R
import com.tatyanashkolnik.composer.databinding.FragmentGameResultBinding
import com.tatyanashkolnik.composer.domain.entity.GameResult
import java.lang.RuntimeException

class GameResultFragment : Fragment() {

    private val gameResult by lazy{ args.gameResult }

    private val args by navArgs<GameResultFragmentArgs>()

    private var _binding: FragmentGameResultBinding? = null
    private val binding: FragmentGameResultBinding
        get() = _binding ?: throw RuntimeException("FragmentGameResultBinding = null")

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        parseArguments()
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        inflateViews()
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun parseArguments() {
//        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
//            gameResult = it
//        }
//    }

    private fun setupClickListeners() {
//        val callback = object : OnBackPressedCallback(true) { теперь всё делает Navigation
//            override fun handleOnBackPressed() {
//                retryGame()
//            }
//        }
//        requireActivity().onBackPressedDispatcher // хотим повесить слушатель на кнопку назад
//            .addCallback(viewLifecycleOwner,callback) // очень важно добавить viewLifecycleOwner
//                                        //чтобы когда фрагмент уничтожался, отключался слушатель
        binding.btnRetry.setOnClickListener { retryGame() }
    }

    private fun inflateViews() {
        with(binding) {
            imageView.setImageResource(getResultImageResource())
            tvRequireRightAnswerCount.text = String.format(
                resources.getString(R.string.required_score),
                gameResult.gameSettings.minCountOfRightAnswers.toString()
            )
            tvScore.text = String.format(
                resources.getString(R.string.score_answers),
                gameResult.countOfRightAnswers.toString()
            )
            tvRequireRightAnswerPercent.text = String.format(
                resources.getString(R.string.required_percentage),
                gameResult.gameSettings.minPersentOfRightAnswers.toString()
            )
            tvRightAnswerPercent.text = String.format(
                resources.getString(R.string.score_percentage),
                getRightAnswersPersent().toString()
            )
        }
    }

    private fun retryGame() {
        findNavController().popBackStack()

//        requireActivity().supportFragmentManager.popBackStack(
//            GameFragment.NAME,
//            POP_BACK_STACK_INCLUSIVE
//        )
        // 0 чтобы не удалить фрагменты не включительно

    }

    private fun getResultImageResource(): Int {
        return when (gameResult.winner) {
            true -> R.drawable.winner
            false -> R.drawable.loser
        }
    }

    private fun getRightAnswersPersent(): Int {
        val right = gameResult.countOfRightAnswers.toDouble()
        val count = gameResult.countOfQuestions
        if(count == 0) return 0
        return ((right / count) * 100).toInt()
    }

    companion object {

        const val KEY_GAME_RESULT = "game_result"

        fun newInstance(gameResult: GameResult): GameResultFragment {
            return GameResultFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT, gameResult)
                }
            }
        }

    }

}