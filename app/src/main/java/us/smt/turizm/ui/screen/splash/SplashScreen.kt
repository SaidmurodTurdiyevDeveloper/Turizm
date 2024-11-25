package us.smt.turizm.ui.screen.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel

class SplashScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<SplashViewModel>()
        LaunchedEffect(key1 = Unit) {
            viewModel.start()
        }
    }

}