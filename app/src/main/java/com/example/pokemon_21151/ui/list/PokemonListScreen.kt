package com.example.pokemon_21151.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokemon_21151.data.Pokemon            // <-- correcto
import com.example.pokemon_21151.network.RetrofitClient  // <-- correcto
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(
    onItemClick: (String) -> Unit
) {
    val list = remember { mutableStateOf<List<Pokemon>>(emptyList()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            runCatching { RetrofitClient.api.getPokemonList(limit = 100) }
                .onSuccess { list.value = it.results }
                .onFailure { list.value = emptyList() }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MainFragment", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6200EE)
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(list.value) { p ->
                PokemonItemCard(pokemon = p) { onItemClick(p.name) }
            }
        }
    }
}

@Composable
private fun PokemonItemCard(
    pokemon: Pokemon,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = pokemon.spriteUrlFromList(),
                contentDescription = "${pokemon.name} sprite",
                modifier = Modifier.size(48.dp)
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = pokemon.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase() else it.toString()
                },
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

/** Extrae el id de la url para armar el sprite */
private fun Pokemon.spriteUrlFromList(): String {
    val id = url.trimEnd('/').substringAfterLast('/')
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
}
