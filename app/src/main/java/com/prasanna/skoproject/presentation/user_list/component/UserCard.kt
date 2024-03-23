package com.prasanna.skoproject.presentation.user_list.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.prasanna.skoproject.domain.model.User


@Composable
fun UserCard(modifier: Modifier, user: User){
    Row(modifier = modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.secondaryContainer,
        shape = RoundedCornerShape(16.dp)
        ).padding(16.dp)){
        user.avatar?.let { Image(painter = rememberAsyncImagePainter(model = user.avatar),
            contentDescription = "user_image", modifier = Modifier.size(80.dp).clip(
                CircleShape
            ), contentScale = ContentScale.Crop) }
        Spacer(modifier = Modifier.width(8.dp))
        Column() {
            if(user.first_name.isNotBlank()
                && user.last_name.isNotBlank()
                ){
                Text("${user.first_name} ${user.last_name}")
            }
            if(user.email.isNotBlank()){
                Text(text = "email id: ${user.email}")
            }
        }

    }
}