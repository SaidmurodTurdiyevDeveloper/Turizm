package us.smt.turizm.ui.screen.home_tab

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.lifecycle.LifecycleEffectOnce
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import us.smt.turizm.R
import us.smt.turizm.domen.model.PlaceData

object HomeTab : Tab {
    private fun readResolve(): Any = HomeTab
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 0u,
            title = "Home",
            icon = rememberVectorPainter(Icons.Default.Home)
        )

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val viewModel: HomeViewModel = getViewModel()
        val state by viewModel.state.collectAsState()
        LifecycleEffectOnce {
            viewModel.onAction(HomeIntent.Init)
        }
        PlaceExplorerScreen(
            state = state,
            onAction = viewModel::onAction
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlaceExplorerScreen(
    state: HomeState,
    onAction: (HomeIntent) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(
            top = 24.dp,
            bottom = 24.dp
        )
    ) {
        stickyHeader {
            HeaderSection()
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Welcome to Uzbekistan in Asia!",
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            SearchBar {
                onAction(HomeIntent.OpenSearch)
            }
        }
        item {

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Popular Places",
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.popular) {
                    PlaceLargeCardItem(
                        it,
                        onDetailClick = {
                            onAction(HomeIntent.OpenDetails(it.id))
                        },
                        onFavoriteClick = { onAction(HomeIntent.ChangeFavourite(it.id)) }
                    )
                }

            }
        }
        item {

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Nearest Places",
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(state.near) {
            PlaceCard(
                it,
                onClick = { onAction(HomeIntent.OpenDetails(it.id)) },
                onFavoriteClick = { onAction(HomeIntent.ChangeFavourite(it.id)) }
            )
        }
    }


}

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(bounded = true)
                ) {

                }
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Asia Uzbekistan",
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 18.sp)
            )
        }
        Card(elevation = CardDefaults.cardElevation(8.dp)) {
            Image(
                painter = painterResource(R.drawable.profil2),
                contentDescription = "User Profile",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }

    }
}

@Composable
fun SearchBar(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                MaterialTheme.colorScheme.primaryContainer
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true),
                onClick = onClick
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Search for a place",
            style = MaterialTheme.typography.bodyLarge
        )
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PlaceCard(
    placeData: PlaceData,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            GlideImage(
                model = placeData.imageLink,
                contentDescription = null,
                loading = placeholder(placeData.image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = placeData.name,
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                )
                Text(
                    text = placeData.address,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp, color = Color.Gray)
                )
                Text(
                    text = placeData.distance.toString(),
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp, color = Color.Gray)
                )
            }

            // Favorite Button
            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier
                    .align(Alignment.Top)
                    .padding(end = 8.dp)
                    .size(24.dp)
                    .background(Color.White, CircleShape)
            ) {
                Icon(
                    imageVector = if (placeData.isFavourite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (placeData.isFavourite) Color.Red else Color.Gray
                )
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PlaceLargeCardItem(
    data: PlaceData,
    onDetailClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = onDetailClick
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .width(300.dp)
        ) {
            GlideImage(
                model = data.imageLink,
                loading = placeholder(data.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Favorite Button
            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .background(Color.White, shape = CircleShape)
            ) {
                Icon(
                    imageVector = if (data.isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (data.isFavourite) Color.Red else Color.Gray
                )
            }
            // Park Info
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .background(
                        color = Color(0xAA000000),
                        shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                    )
                    .padding(12.dp)
            ) {
                Text(
                    text = data.name,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp)
                )
                Text(
                    text = data.address,
                    color = Color.LightGray,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
                )
                Text(
                    text = data.distance.toString(),
                    color = Color.LightGray,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
                )
            }
        }
    }
}






