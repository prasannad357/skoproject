package com.prasanna.skoproject.presentation.user_list

import android.graphics.fonts.FontStyle
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.prasanna.skoproject.presentation.user_list.component.UserCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(viewModel: UserListViewModel = hiltViewModel()){
    val lazyItems = viewModel.userList.collectAsLazyPagingItems()

    Scaffold(topBar = { Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center){
        Text(text = "Users", fontSize = 28.sp)
    } }) {

        LazyColumn(modifier = Modifier.padding(it)
            .fillMaxSize()) {
            items(lazyItems.itemCount){
                val user = lazyItems[it]
                Column(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    if(user != null){
                        UserCard(modifier = Modifier, user = user)
                    }
                }
            }
            lazyItems.apply {
                when{
                    loadState.refresh is LoadState.Error -> {
                        //loading icon
                        Log.d("UserListScreen", "Refresh error: " +
                                "${(loadState.refresh as LoadState.Error).error}")
                    }

                    loadState.append is LoadState.Error -> {
                        Log.d("UserListScreen", "Refresh error: " +
                                "${(loadState.append as LoadState.Error).error}")
                    }

                }
            }
        }

    }


}