package com.tatyanashkolnik.composer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import com.tatyanashkolnik.composer.databinding.FragmentGameResultBinding
import com.tatyanashkolnik.composer.domain.entity.GameResult
import java.lang.RuntimeException

class GameResultFragment : Fragment() {

    private lateinit var gameResult: GameResult

    private var _binding: FragmentGameResultBinding? = null
    private val binding: FragmentGameResultBinding
        get() = _binding ?: throw RuntimeException("FragmentGameResultBinding = null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher // хотим повесить слушатель на кнопку назад
            .addCallback(
                viewLifecycleOwner,
                object : OnBackPressedCallback(true) { // очень важно добавить
                    override fun handleOnBackPressed() {  // viewLifecycleOwner чтобы когда фрагмент
                        retryGame()                       // уничтожался, отключался слушатель
                    }
                })
        binding.btnRetry.setOnClickListener { retryGame() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArguments() {
        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
            gameResult = it
        }
    }

    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(
            GameFragment.NAME,
            POP_BACK_STACK_INCLUSIVE
        )
        // 0 чтобы не удалить фрагменты не включительно
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