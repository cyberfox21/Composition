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

class GameResultFragment : Fragment() {

    private val args by navArgs<GameResultFragmentArgs>()

    private var _binding: FragmentGameResultBinding? = null
    private val binding: FragmentGameResultBinding
        get() = _binding ?: throw RuntimeException("FragmentGameResultBinding = null")

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

    private fun setupClickListeners() {
        binding.btnRetry.setOnClickListener { retryGame() }
    }

    private fun inflateViews() {
        with(binding) {
            imageView.setImageResource(getResultImageResource())
            tvRequireRightAnswerCount.text = String.format(
                resources.getString(R.string.required_score),
                args.gameResult.gameSettings.minCountOfRightAnswers.toString()
            )
            tvScore.text = String.format(
                resources.getString(R.string.score_answers),
                args.gameResult.countOfRightAnswers.toString()
            )
            tvRequireRightAnswerPercent.text = String.format(
                resources.getString(R.string.required_percentage),
                args.gameResult.gameSettings.minPersentOfRightAnswers.toString()
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
        return when (args.gameResult.winner) {
            true -> R.drawable.winner
            false -> R.drawable.loser
        }
    }

    private fun getRightAnswersPersent(): Int {
        val right = args.gameResult.countOfRightAnswers.toDouble()
        val count = args.gameResult.countOfQuestions
        if (count == 0) return 0
        return ((right / count) * 100).toInt()
    }

}