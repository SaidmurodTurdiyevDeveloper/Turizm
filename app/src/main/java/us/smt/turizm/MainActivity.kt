package us.smt.turizm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import us.smt.turizm.data.database.remote.FireBaseHelper
import us.smt.turizm.ui.screen.splash.SplashScreen
import us.smt.turizm.ui.theme.TurizmTheme
import us.smt.turizm.ui.utils.AppNavigator

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FireBaseHelper.inject(Firebase.firestore)
        setContent {
            TurizmTheme {
                Navigator(SplashScreen()) { navigate ->
                    LaunchedEffect(Unit) {
                        AppNavigator.navigatorState.onEach {
                            it.invoke(navigate)
                        }.launchIn(this)
                    }
                    CurrentScreen()
                }
            }
        }
    }
}