package com.example.scaffold

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scaffold.ui.theme.ScaffoldTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScaffoldTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    //save each state surviving configuration changes (such as rotations) and process death.
    var shouldShowOnBoarding by rememberSaveable { mutableStateOf(true) }

    if (shouldShowOnBoarding) {
        OnBoardingScreen(onExploreClicked = { shouldShowOnBoarding = false })
    }
    else {
        MyLayout()
    }
}

@Composable
fun OnBoardingScreen(onExploreClicked:()-> Unit) {

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Welcome to my world!")
            Button(
                onClick = onExploreClicked ,
                modifier = Modifier.padding(vertical = 24.dp)
            ) {
                Text(text = "Explore")
            }
        }
    }
}

@Composable
fun MyLayout() {
    var like by remember {
        mutableStateOf(false)
    }

    val likeState: Color by animateColorAsState(
        targetValue = if (like) Color.Red else Color.LightGray
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "About Me")
                },
                actions = {
                IconButton(onClick = { like = !like }) {
                    Icon(
                        Icons.Filled.Favorite,
                        tint = likeState,
                        contentDescription = null)
                }
                }
            )
        }
    ) { innerPadding ->
        PhotographCard(Modifier.padding(innerPadding))
    }
}

@Composable
fun PhotographCard(modifier: Modifier = Modifier) {
    // We keep track if the message is expanded or not in this
    // variable
    var isExpanded by remember { mutableStateOf(false) }
    // surfaceColor will be updated gradually from one color to the other

    val surfaceColor: Color by animateColorAsState(
        targetValue =
        if (isExpanded) MaterialTheme.colors.primary else
            MaterialTheme.colors.surface
    )
    val image =
        if (isExpanded) (painterResource(id = R.drawable.img_2))
        else {
            (painterResource(id = R.drawable.myimg))
        }

    Column(
        modifier = modifier
            .padding(6.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = image,
            contentDescription = "My image",
            modifier
                .size(150.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = modifier.height(4.dp))
        Text(text = "Joy Kangangi", fontWeight = FontWeight.Bold, fontSize = 24.sp)

        /** LocalContentAlpha is defining opacity level of its children
        //CompositionLocalProvider.
        // It allows us to pass data implicitly through the composition tree.
         **/
        CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.medium
        ) {
            Text(
                text = "3 minutes ago",
                style = MaterialTheme.typography.body2
            )
        }
        Surface(
            /**
             * animateContentSize will change the Surface size gradually
            surfaceColor color will be changing gradually from primary
            to surface*/
            modifier = modifier
                .animateContentSize()
                .clip(RoundedCornerShape(3.dp)),
            color = surfaceColor
        ) {
            Row(
                Modifier
                    .padding(4.dp)
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    ),
            ) {
                Text(
                    text = message,
                    // If the message is expanded, we display all its content
                    // otherwise we only display the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.wrapContentWidth()
                )
                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        imageVector =
                        if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = if (isExpanded) {
                            stringResource(id = R.string.show_less)
                        } else {
                            stringResource(id = R.string.show_more)
                        }
                    )
                }
            }
        }
    }
}

val message = """
 A song I've recently learnt & loved
 I was glad 
 when you showed me how to pray
 I knew how to bring my requests to you
 There is a God in heaven
 who hears prayers
 It's not in vain kneeling down in prayer
 It's not in vain calling unto His name
""".trimIndent()

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ScaffoldTheme {
       MyApp()
    }
}