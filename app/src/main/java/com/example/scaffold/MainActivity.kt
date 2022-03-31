package com.example.scaffold

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.scaffold.ui.theme.ScaffoldTheme
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
    val coroutineScope = rememberCoroutineScope()
    val image =
        if (new.value) (painterResource(id = R.drawable.img_2))
        else
           (painterResource(id = R.drawable.myimg))

    Column(
        modifier
            .padding(start = 6.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.surface)
            .clickable(onClick = {})
    ) {
        Surface(
            modifier.size(150.dp),
            shape = CircleShape
        ) {
            Image(painter = image, contentDescription = "image")
            coroutineScope.launch {
                delay(2000)
                new.value = !new.value
            }
        }
    }
        Spacer(modifier = Modifier.height(2.dp))
        // We keep track if the message is expanded or not in this
        // variable
        var isExpanded by remember { mutableStateOf(false) }
        // surfaceColor will be updated gradually from one color to the other

        val surfaceColor: Color by animateColorAsState(targetValue =
        if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface)

        // We toggle the isExpanded variable when we click on this Column
        Column(
            modifier
                .padding(start = 6.dp)
                .clickable { isExpanded = !isExpanded }
        ) {
            Text(text = "Joy Kangangi", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))

            /** LocalContentAlpha is defining opacity level of its children
            //CompositionLocalProvider.
            // It allows us to pass data implicitly through the composition tree.
             **/

            /** LocalContentAlpha is defining opacity level of its children
            //CompositionLocalProvider.
            // It allows us to pass data implicitly through the composition tree.
             **/

            CompositionLocalProvider(
                LocalContentAlpha provides ContentAlpha.medium
            ) {
                Text(text = "3 minutes ago", style = MaterialTheme.typography.body2)
            }

            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                // surfaceColor color will be changing gradually from primary to surface
                color = surfaceColor,
                // animateContentSize will change the Surface size gradually
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = message,
                    modifier = Modifier.padding(all = 4.dp),
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