package com.tatyanashkolnik.composer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tatyanashkolnik.composer.databinding.FragmentGameResultBinding
import com.tatyanashkolnik.composer.databinding.FragmentIntroductionBinding
import java.lang.RuntimeException

class IntroductionFragment : Fragment() {

    private var _binding: FragmentIntroductionBinding? = null
    private val binding: FragmentIntroductionBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIntroductionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}