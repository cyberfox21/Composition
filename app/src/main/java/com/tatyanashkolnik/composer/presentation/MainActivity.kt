package com.tatyanashkolnik.composer.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tatyanashkolnik.composer.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_game_result)
    }
}