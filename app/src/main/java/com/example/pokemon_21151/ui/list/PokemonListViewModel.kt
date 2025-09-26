package com.example.pokemon_21151.ui.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon_21151.data.Pokemon
import com.example.pokemon_21151.repository.PokemonRepository
import kotlinx.coroutines.launch

data class ListUiState(
    val isLoading: Boolean = true,
    val items: List<Pokemon> = emptyList(),
    val error: String? = null
)

class PokemonListViewModel(
    private val repo: PokemonRepository = PokemonRepository()
) : ViewModel() {

    var uiState by mutableStateOf(ListUiState())
        private set

    init {
        load()
    }

    fun load(limit: Int = 100) {
        viewModelScope.launch {
            uiState = ListUiState(isLoading = true)
            runCatching { repo.getList(limit) }
                .onSuccess { r -> uiState = ListUiState(isLoading = false, items = r.results) }
                .onFailure { e -> uiState = ListUiState(isLoading = false, error = e.message) }
        }
    }
}
