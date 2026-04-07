package com.haurafathinah0084.dozymate.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.haurafathinah0084.dozymate.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipsScreen(navController: NavHostController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.tips))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(Icons.Default.ArrowBack,
                            contentDescription = "kembali")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Image(
                painter = painterResource(R.drawable.sleep_images),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = stringResource(R.string.tips_app),
                style = MaterialTheme.typography.bodyLarge
            )

            HorizontalDivider()

            Text(
                text = stringResource(R.string.manfaat),
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = stringResource(R.string.manfaat_tidur),
                style = MaterialTheme.typography.bodyMedium
            )

            HorizontalDivider()

            Text(
                text = stringResource(R.string.tips_baik),
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = stringResource(R.string.tips_tips),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}