package us.smt.turizm.ui.screen.details

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import us.smt.turizm.R
import us.smt.turizm.domen.model.PlaceData
import us.smt.turizm.domen.model.getRandImage

class DetailsScreen(private val id: String) : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<DetailsViewmodel>()
        val state by viewModel.state.collectAsState()
        LaunchedEffect(key1 = true) {
            viewModel.onAction(DetailsIntent.LoadData(id))
        }
        DetailScreen(
            data = state,
            onAction = viewModel::onAction
        )
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailScreen(
    data: PlaceData?,
    onAction: (DetailsIntent) -> Unit
) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        Box {
            GlideImage(
                model = data?.imageLink ?: "",
                loading = placeholder(data?.image ?: getRandImage()),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(330.dp)
            )
            Text(
                text = data?.name ?: "",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
                    .offset(y = (-50).dp)
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = 300.dp
            )
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        )
                        .padding(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "📍 ${data?.address}",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    RatingBar(rating = (data?.rating ?: 0.0).toFloat())
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "About",
                        style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = data?.about ?: "",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Including Services",
                        style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = data?.service ?: "",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Distance",
                        style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = data?.distance.toString()+" km",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = data?.lat.toString()+" - "+data?.lang.toString(),
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
                    )
                    if (data?.isCanBron == true) {
                        Spacer(modifier = Modifier.height(56.dp))
                        Button(
                            onClick = {
                                Toast.makeText(context, "Bron now", Toast.LENGTH_SHORT).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                        ) {
                            Text(text = "Book Now", color = Color.White)
                        }
                    }
                }
            }
        }

        IconButton(
            onClick = { onAction(DetailsIntent.Back) },
            modifier = Modifier
                .align(Alignment.TopStart)
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(16.dp)
                .background(Color.White.copy(alpha = 0.5f), CircleShape)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Favorite",
                tint = Color.Black
            )
        }
        IconButton(
            onClick = { onAction(DetailsIntent.FavoriteChange) },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(16.dp)
                .background(Color.White.copy(alpha = 0.5f), CircleShape)
        ) {
            Icon(
                imageVector = if (data?.isFavourite == true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favorite",
                tint = if (data?.isFavourite == true) Color.Red else Color.Black
            )
        }
    }
}

@Composable
private fun RatingBar(
    rating: Float,
    modifier: Modifier = Modifier,
    maxRating: Int = 5
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        for (i in 1..maxRating) {
            val icon = when {
                i <= rating -> R.drawable.ic_star// Full star
                i - 0.5f <= rating -> R.drawable.ic_star_half
                else -> R.drawable.ic_star_empty // Empty star
            }

            Icon(
                painter = painterResource(icon),
                contentDescription = "Rating $i",
                tint = Color.Red
            )
        }
    }
}
