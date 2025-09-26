package com.example.pokemon_21151.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokemon_21151.data.PokemonDetail
import com.example.pokemon_21151.network.RetrofitClient
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(
    pokemonName: String,
    onBack: () -> Unit
) {
    var detail by remember { mutableStateOf<PokemonDetail?>(null) }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(pokemonName) {
        scope.launch {
            runCatching { RetrofitClient.api.getPokemonDetail(pokemonName) }
                .onSuccess { detail = it }
                .onFailure { error = it.message }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("DetailFragment", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF6200EE))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            when {
                error != null -> Text("Error: $error")
                detail == null -> Text("Cargando…")
                else -> {
                    val s = detail!!.sprites
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Front"); Text("Back")
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        AsyncImage(s.front_default, "front", Modifier.size(96.dp))
                        AsyncImage(s.back_default, "back", Modifier.size(96.dp))
                    }
                    Spacer(Modifier.height(16.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Front Shiny"); Text("Back Shiny")
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        AsyncImage(s.front_shiny, "front shiny", Modifier.size(96.dp))
                        AsyncImage(s.back_shiny, "back shiny", Modifier.size(96.dp))
                    }
                }
            }
        }
    }
}
