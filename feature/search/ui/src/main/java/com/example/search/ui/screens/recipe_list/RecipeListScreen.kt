package com.example.search.ui.screens.recipe_list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.common.utils.UiText

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipeListScreen(
    modifier: Modifier = Modifier,
    viewModel: RecipeListViewModel,
    onClick: (String) -> Unit
){
    val uiState = viewModel.uiState.collectAsState()
    val query = rememberSaveable {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            TextField(
                value = query.value,
                placeholder = { Text(text = "Search here...", style = MaterialTheme.typography.bodySmall)},
                onValueChange = {
                query.value = it
                viewModel.onEvent(RecipeList.Event.SearchRecipe(query.value))
            }, colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
            ),
                modifier = Modifier.fillMaxWidth())
        }
    ) {

        if (uiState.value.isLoading){
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }

        if (uiState.value.error !is UiText.Idle){
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(text = uiState.value.error.getString())
            }
        }

        uiState.value.data?.let { list ->

            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ){
                items(list.size) {
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                            .clickable { onClick.invoke(list[it].idMeal) },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        AsyncImage(
                            model = list[it],
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(text = list[it].strMeal, style = MaterialTheme.typography.bodyLarge)

                        Spacer(modifier = Modifier.height(12.dp))

                        if (list[it].strTags.isNotEmpty()){

                            FlowRow {
                                list[it].strTags.split(",")
                                Box(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .background(
                                            color = Color.White,
                                            shape = RoundedCornerShape(12.dp)
                                        ).clip(RoundedCornerShape(12.dp))
                                        .border(width = 1.dp, color = Color.Red, shape = RoundedCornerShape(12.dp)),
                                    contentAlignment = Alignment.Center
                                ){
                                    Text(
                                        text = list[it].toString(),
                                        style = MaterialTheme.typography.bodySmall,
                                        modifier = Modifier
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                        }
                    }
                }
            }


        }


    }
}