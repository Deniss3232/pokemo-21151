package com.example.pokemon_21151.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PokemonListScreen(
    onPokemonClick: (String) -> Unit = {}
) {
    val vm: PokemonListViewModel = viewModel()
    val state by vm.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { vm.load() }

    when {
        state.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        state.error != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Ups: ${state.error}")
        }
        else -> LazyColumn(Modifier.fillMaxSize().padding(8.dp)) {
            items(items = state.items, key = { it }) { name: String ->
                ListItem(headlineContent = { Text(name) }, modifier = Modifier.fillMaxWidth())
                HorizontalDivider()
            }
        }
    }
}
