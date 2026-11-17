package com.portfolio.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.portfolio.app.data.InMemoryProfileRepository
import com.portfolio.app.data.ProfileRepository
import com.portfolio.app.ui.screen.ProfileScreen
import com.portfolio.app.ui.theme.PortfolioTheme

class MainActivity : ComponentActivity() {

    // Swap this for a DI-provided instance if the app grows beyond one screen.
    private val profileRepository: ProfileRepository = InMemoryProfileRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PortfolioTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ProfileScreen(profile = profileRepository.getProfile())
                }
            }
        }
    }
}
