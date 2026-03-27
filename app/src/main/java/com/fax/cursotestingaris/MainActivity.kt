package com.fax.cursotestingaris

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.fax.cursotestingaris.core.presentation.navigation.NavGraph
import com.fax.cursotestingaris.ui.theme.CursoTestingArisTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CursoTestingArisTheme {
                NavGraph()
            }
        }
    }
}
