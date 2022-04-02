package com.example.scaffold

/*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scaffold.ui.theme.ScaffoldTheme
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
                    MyLayout()
                }
            }
        }
    }
}

@Composable
fun MyLayout() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "About Me")
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        PhotographCard(Modifier.padding(innerPadding))
    }
}

val message = """
     A song I've recently learnt & loved
     I was glad when you showed me how to pray,
     I knew how to bring my requests to you,
     There is a God in heaven who hears prayers,
     It's not in vain kneeling down in prayer
     It's not in vain calling unto His name
""".trimIndent()

@Composable
fun PhotographCard(modifier: Modifier = Modifier) {
    val new = remember { mutableStateOf(true) }
    //a CoroutineScope that follows this composable's lifecycle
     val coroutineScope = rememberCoroutineScope()

    // We keep track if the message is expanded or not in this
    // variable
    var isExpanded by remember { mutableStateOf(false) }
    // surfaceColor will be updated gradually from one color to the other

    val surfaceColor: Color by animateColorAsState(
        targetValue =
        if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    )
    val image =
        if (isExpanded) (painterResource(id = R.drawable.img_2))
        else {
            (painterResource(id = R.drawable.myimg))
        }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Image(
            painter = image,
            contentDescription = "My image",
            modifier.size(150.dp)
                .clip(CircleShape)
            /*
             .clickable(onClick = {
                coroutineScope.launch {
                    delay(2000)
                    new.value = !new.value
                }
            })
           */
        )
        coroutineScope.launch {
            delay(2000)
            new.value = !new.value
            //display a placeholder before the actual image is loaded from the network(e.g)
            coroutineScope.cancel()
            // otherwise the coroutine will work infinitely and display images as animation
            // at first it is stable but get messy giving a bad UI.
        }
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
            shape = MaterialTheme.shapes.medium,
            elevation = 4.dp,
            // surfaceColor color will be changing gradually from primary to surface
            modifier = modifier
                .clickable { isExpanded = !isExpanded }
                // animateContentSize will change the Surface size gradually
                .animateContentSize()
                .padding(1.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = surfaceColor
        ) {
            Text(
                text = message,
                modifier = modifier.padding(all = 4.dp),
                // If the message is expanded, we display all its content
                // otherwise we only display the first line
                maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ScaffoldTheme {
        MyLayout()
    }
}
*/