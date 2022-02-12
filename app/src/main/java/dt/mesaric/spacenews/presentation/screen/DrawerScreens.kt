package dt.mesaric.spacenews.presentation.screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import coil.annotation.ExperimentalCoilApi
import dt.mesaric.spacenews.R
import dt.mesaric.spacenews.presentation.map.GoogleMap
import dt.mesaric.spacenews.presentation.navigation.MainNav


sealed class DrawerScreens(
    val route: String,
    val title: String,
    val screenToLoad: @Composable () -> Unit
) {
    companion object {
        private const val homeRoute: String = "home"
        private const val homeTitle: String = "Home"
        private const val mapRoute: String = "map"
        private const val mapTitle: String = "Map"
        private const val aboutRoute: String = "about"
        private const val aboutTitle: String = "About"
    }
    @ExperimentalAnimationApi
    @ExperimentalCoilApi
    @ExperimentalMaterialApi
    object Home : DrawerScreens(homeRoute, homeTitle, {
        MainNav()
    })

    @ExperimentalMaterialApi
    object Map : DrawerScreens(mapRoute, mapTitle, {
        MapScreen()
    })

    @ExperimentalMaterialApi
    object AboutUs : DrawerScreens(aboutRoute, aboutTitle, {
        AboutUsScreen()
    })
}

@ExperimentalAnimationApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
val screens = listOf(
    DrawerScreens.Home,
    DrawerScreens.Map,
    DrawerScreens.AboutUs
)


@ExperimentalMaterialApi
@Composable
fun AboutUsScreen() {
    Column(
        content = {
            Text(text = stringResource(R.string.abouts_us_msg))
        }, modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
}

@ExperimentalMaterialApi
@Composable
fun MapScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        GoogleMap()
    }
}



