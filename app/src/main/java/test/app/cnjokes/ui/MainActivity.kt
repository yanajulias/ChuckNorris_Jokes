package test.app.cnjokes.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import test.app.cnjokes.R
import test.app.cnjokes.data.JokesResponse
import test.app.cnjokes.theme.CNJokesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CNJokesTheme {
                JokesHome()
            }
        }
    }
}

@Composable
fun JokesHome(
    modifier: Modifier = Modifier,
    jokesViewModel: JokesViewModel = hiltViewModel()
) {
    val query: MutableState<String> = remember { mutableStateOf("") }

    Surface(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = "CHUCKNORRIS.IO",
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(12.dp),
                fontSize = 28.sp,
                fontFamily = FontFamily.Monospace
            )
            OutlinedTextField(
                value = query.value,
                onValueChange = {
                    query.value = it
                    jokesViewModel.getJokesList(query.value)
                },
                enabled = true,
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null
                    )
                },
                label = { Text(text = "Search some jokes...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            if (jokesViewModel.list.value.isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            if (jokesViewModel.list.value.errorMessage.isNotBlank()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = jokesViewModel.list.value.errorMessage,
                        modifier = Modifier.padding(12.dp),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center
                    )
                }
            }

            if (jokesViewModel.list.value.data.isNotEmpty()) {
                LazyColumn {
                    jokesViewModel.list.value.data.let { it ->
                        items(it) {
                            CardList(modifier, it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DefaultText(modifier: Modifier = Modifier){
    Image(painter = painterResource(R.drawable.ic_search), contentDescription = null)
}

@Composable
fun CardList(
    modifier: Modifier = Modifier,
    jokesList: JokesResponse.Result
) {
    ElevatedCard(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier.padding(12.dp),
            text = jokesList.value.orEmpty(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )

    }
}

@Preview(showSystemUi = true)
@Composable
fun GreetingPreview() {
    CNJokesTheme {
        JokesHome()
    }
}