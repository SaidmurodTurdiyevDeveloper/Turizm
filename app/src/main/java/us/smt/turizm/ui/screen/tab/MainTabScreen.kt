package us.smt.turizm.ui.screen.tab

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import us.smt.turizm.ui.screen.favourite_tab.FavouriteTab
import us.smt.turizm.ui.screen.home_tab.HomeTab
import us.smt.turizm.ui.screen.setting_tab.TripTab

class MainTabScreen : Screen {
    @Composable
    override fun Content() {
        val list = remember {
            listOf(
                HomeTab,
                FavouriteTab,
                TripTab
            )
        }

        TabNavigator(list.first()) {
            Scaffold(
                content = { padding ->
                    Surface(modifier = Modifier.padding(padding)) {
                        CurrentTab()
                    }
                },
                bottomBar = {
                    BottomNavigation(
                        list = list
                    )
                },
                contentWindowInsets = WindowInsets(0, 0, 0, 0)
            )
        }
    }

}

@Composable
private fun BottomNavigation(list: List<Tab>) {
    Row(modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)) {
        list.forEach {
            TabNavigationItem(it)
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    Column(
        modifier = Modifier
            .weight(1f)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true)
            ) {
                tabNavigator.current = tab
            }
            .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = tab.options.icon ?: rememberVectorPainter(Icons.Default.Info),
            tint = if (tabNavigator.current.options.index == tab.options.index) MaterialTheme.colorScheme.primary else Color.Gray,
            contentDescription = tab.options.title
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = tab.options.title,
            style = MaterialTheme.typography.bodyLarge,
            color = if (tabNavigator.current.options.index == tab.options.index) MaterialTheme.colorScheme.primary else Color.Gray
        )
    }
}