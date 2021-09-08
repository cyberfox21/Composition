package com.tatyanashkolnik.composer.presentation

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tatyanashkolnik.composer.databinding.FragmentGameBinding
import com.tatyanashkolnik.composer.domain.entity.GameResult
import com.tatyanashkolnik.composer.domain.entity.Question

class GameFragment : Fragment() {

//    private val args = GameFragmentArgs.fromBundle(requireArguments()) 1 способ

    private val args by navArgs<GameFragmentArgs>() // 2 способ

    private val gameViewModelFactory by lazy {
        GameViewModelFactory(args.level, requireActivity().application)
    }

    private val gameViewModel by lazy { // ленивая инициализация
        ViewModelProvider(                            // инициализируется при первом обращении
            this,
            gameViewModelFactory
        )[GameViewModel::class.java]
    }

    private val tvOptions by lazy { // если бы мы оставили знак присваивания, то
        mutableListOf<TextView>().apply {  // то этой переменной будет присвоено значение сразу же
            add(binding.tvOption1) // и мы обратимся к view до вызова onViewCreated, будет крэш
            add(binding.tvOption2) // поэтому by lazy
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners()

        observeViewModel()

        gameViewModel.startGame()

    }

    private fun setClickListeners() {
        for (option in tvOptions) {
            option.setOnClickListener {
                gameViewModel.chooseAnswer(option.text.toString().toInt())
            }
        }
    }

    private fun observeViewModel() {
        // устанавливаем время таймера
        gameViewModel.formattedTime.observe(viewLifecycleOwner) {
            binding.tvTime.text = it
        }

        // устанавливаем данные задания: сумму, видимое число и варианты ответы
        gameViewModel.question.observe(viewLifecycleOwner, object : Observer<Question> {
            override fun onChanged(t: Question?) {
                t?.let {
                    with(binding) {
                        tvSum.text = it.sum.toString()
                        tvVisibleNumber.text = it.visibleNumber.toString()
                        for (i in 0 until tvOptions.size) {
                            tvOptions[i].text = it.options[i].toString()
                        }
                    }
                }
            }
        })

        // устанавливваем текст строки прогресса
        gameViewModel.progressAnswers.observe(viewLifecycleOwner) { str ->
            str?.let { binding.tvProgress.text = it }
        }

        // меняем цвет строки прогресса
        gameViewModel.enoughCount.observe(viewLifecycleOwner, object : Observer<Boolean> {
            override fun onChanged(t: Boolean?) {
                t?.let {
                    with(binding) {
                        val color = calculateColor(it)
                        tvProgress.setTextColor(color)
                    }
                }
            }

        })

        // устанавливаем прогресс в ProgressBar
        gameViewModel.percentOfRightAnswers.observe(viewLifecycleOwner, object : Observer<Int> {
            override fun onChanged(t: Int?) {
                t?.let { binding.progressBar.setProgress(it, true) }
            }
        })

        // устанавливаем secondary progress в ProgressBar
        gameViewModel.minPercent.observe(viewLifecycleOwner, object : Observer<Int> {
            override fun onChanged(t: Int?) {
                t?.let { binding.progressBar.secondaryProgress = it }
            }
        })

        // меняем цвет ProgressBar
        gameViewModel.enoughPercent.observe(viewLifecycleOwner, object : Observer<Boolean> {
            override fun onChanged(t: Boolean?) {
                t?.let {
                    val color = calculateColor(it)
                    binding.progressBar.progressTintList = ColorStateList.valueOf(color)
                }
            }
        })

        // генерируем GameResult и открываем GameFragment
        gameViewModel.gameResult.observe(viewLifecycleOwner, object : Observer<GameResult> {
            override fun onChanged(t: GameResult?) {
                t?.let { launchGameResultFragment(it) }
            }

        })
    }

    private fun calculateColor(expression: Boolean): Int {
        return when (expression) {
            true -> Color.GREEN
            false -> Color.RED
        }
    }

    private fun launchGameResultFragment(gameResult: GameResult) {
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameResultFragment(
                gameResult
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}