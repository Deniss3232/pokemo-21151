package com.example.pokemon_21151.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(
    pokemonName: String,
    onBack: () -> Unit
) {
    val vm: PokemonDetailViewModel = viewModel()
    val state by vm.uiState.collectAsStateWithLifecycle()

    // Carga segura: si la recomposición ocurre, solo vuelve a cargar si cambia el nombre
    LaunchedEffect(pokemonName) { vm.load(pokemonName) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
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
                state.isLoading -> Text("Cargando…")
                state.error != null -> Text("Error: ${state.error}")
                state.detail != null -> {
                    val s = state.detail!!.sprites
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
                else -> Text("Sin datos")
            }
        }
    }
}
