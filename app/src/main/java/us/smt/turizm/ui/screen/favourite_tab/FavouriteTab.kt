package us.smt.turizm.ui.screen.favourite_tab

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.lifecycle.LifecycleEffectOnce
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import us.smt.turizm.ui.screen.home_tab.PlaceCard

object FavouriteTab : Tab {
    private fun readResolve(): Any = FavouriteTab
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 1u,
            title = "Favourite",
            icon = rememberVectorPainter(Icons.Default.FavoriteBorder)
        )

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val viewModel: FavouriteViewModel = getViewModel()
        val state by viewModel.state.collectAsState()
        LifecycleEffectOnce {
            viewModel.onAction(FavouriteIntent.Init)
        }
        FavouritePage(
            state = state,
            onAction = viewModel::onAction
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavouritePage(
    state: FavouriteState,
    onAction: (FavouriteIntent) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 24.dp)
    ) {
        stickyHeader {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "Favourites!",
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        items(state.favourites) {
            PlaceCard(
                it,
                onClick = { onAction(FavouriteIntent.OpenDetails(it.id)) },
                onFavoriteClick = {
                    onAction(FavouriteIntent.Favourite(it.id))
                }
            )
        }
    }
}
