package us.smt.turizm.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import us.smt.turizm.R

val Gilroy = FontFamily(
    Font(R.font.gilroy_thin, FontWeight.Thin),
    Font(R.font.gilroy_thin_italic, FontWeight.Thin, FontStyle.Italic),
    Font(R.font.gilroy_ultra_light, FontWeight.ExtraLight),
    Font(R.font.gilroy_ultra_light_italic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(R.font.gilroy_light, FontWeight.Light),
    Font(R.font.gilroy_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.gilroy_regular, FontWeight.Normal),
    Font(R.font.gilroy_regular_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.gilroy_medium, FontWeight.Medium),
    Font(R.font.gilroy_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.gilroy_semi_bold, FontWeight.SemiBold),
    Font(R.font.gilroy_semi_bold_italic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.gilroy_bold, FontWeight.Bold),
    Font(R.font.gilroy_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.gilroy_extra_bold, FontWeight.ExtraBold),
    Font(R.font.gilroy_extra_bold_italic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.gilroy_black, FontWeight.Black),
    Font(R.font.gilroy_black_italic, FontWeight.Black, FontStyle.Italic),
//    Font(R.font.gilroy_heavy, FontWeight(1000)),
//    Font(R.font.gilroy_heavy_italic, FontWeight(1000), FontStyle.Italic)
)

// Typography structure based on TypographyTokens
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = Gilroy,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 57.sp
    ),
    displayMedium = TextStyle(
        fontFamily = Gilroy,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp
    ),
    displaySmall = TextStyle(
        fontFamily = Gilroy,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = Gilroy,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Gilroy,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = Gilroy,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Gilroy,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Gilroy,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    ),
    titleSmall = TextStyle(
        fontFamily = Gilroy,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Gilroy,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Gilroy,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Gilroy,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Gilroy,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Gilroy,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Gilroy,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp
    )
)
