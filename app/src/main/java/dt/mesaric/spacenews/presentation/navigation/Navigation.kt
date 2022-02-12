package dt.mesaric.spacenews.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import dt.mesaric.spacenews.presentation.screen.DrawerScreens
import dt.mesaric.spacenews.presentation.screen.screens

import dt.mesaric.spacenews.presentation.ui.theme.LocalSpacing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val TITLE = "algebra.hr"
private val HEIGHT_MODIFIER = 200.dp
private val FONT_TITLE = 20.sp
private val FONT_TEXT = 16.sp

@ExperimentalAnimationApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun Navigation() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val currentScreen = remember { mutableStateOf(screens[0]) }
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            Drawer(
                selectedScreen = currentScreen.value,
                onMenuSelected = { drawerScreen: DrawerScreens ->
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                    currentScreen.value = drawerScreen
                })
        },
        content = {
            currentScreen.value.screenToLoad()
        },
        topBar = {
            TopAppBarLayout(currentScreen.value, scope, scaffoldState)
        },
    )
}

@Composable
fun TopAppBarLayout(
    currentScreen: DrawerScreens,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState
) {
    TopAppBar(title = { Text(currentScreen.title) }, navigationIcon = {
        IconButton(onClick = {
            scope.launch {
                scaffoldState.drawerState.open()
            }
        }) {
            Icon(Icons.Filled.Menu, "")
        }
    }, backgroundColor = Color.Black, contentColor = Color.White)
}

@Composable
fun DrawerHeader() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(HEIGHT_MODIFIER)
        .background(Color.DarkGray)
        .padding(LocalSpacing.current.medium), content = {
        Text(TITLE,color = Color.White,fontSize = FONT_TITLE)
    },verticalArrangement = Arrangement.Bottom)
}

@ExperimentalAnimationApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    selectedScreen: DrawerScreens,
    onMenuSelected: ((drawerScreen: DrawerScreens) -> Unit)? = null
) {
    Column(
        modifier
            .fillMaxSize()
    ) {
        DrawerHeader()
        screens.forEach { screen ->
            Row(
                content = {
                    Text(
                        text = screen.title,
                        fontSize = FONT_TEXT,
                        fontWeight = FontWeight.Bold,
                        color = if (screen.route == selectedScreen.route) Color.White else Color.Black
                    )
                }, modifier = Modifier
                    .padding(horizontal = LocalSpacing.current.medium, vertical = LocalSpacing.current.small)
                    .background(
                        color = if (screen.route == selectedScreen.route) Color.DarkGray else Color.White,
                        shape = RoundedCornerShape(LocalSpacing.current.extraSmall)
                    )
                    .fillMaxWidth()
                    .clickable(onClick = {
                        onMenuSelected?.invoke(screen)
                    })
                    .padding(vertical = LocalSpacing.current.small, horizontal = LocalSpacing.current.medium)
            )
        }
    }
}