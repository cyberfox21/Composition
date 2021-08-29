package com.tatyanashkolnik.composer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tatyanashkolnik.composer.R
import com.tatyanashkolnik.composer.databinding.FragmentChooseLevelBinding
import com.tatyanashkolnik.composer.domain.entity.Level
import java.lang.RuntimeException

class ChooseLevelFragment : Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            btnTestLvl.setOnClickListener { launchGameFragment(Level.TEST) }
            btnEasyLvl.setOnClickListener { launchGameFragment(Level.EASY) }
            btnNormalLvl.setOnClickListener { launchGameFragment(Level.NORMAL) }
            btnHardLvl.setOnClickListener { launchGameFragment(Level.HARD) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchGameFragment(level: Level) {
        requireActivity().supportFragmentManager.beginTransaction()
            .addToBackStack(GameFragment.NAME) // не текущий фрагмент, а тот, который запускаем
            .replace(R.id.main_container, GameFragment.newInstance(level))
            .commit()
    }

    companion object {

        const val NAME = "ChooseLevelFragment"

        fun newInstance(): ChooseLevelFragment {
            return ChooseLevelFragment()
        }

    }

}