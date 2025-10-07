package com.example.pokemon_21151.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon_21151.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ListUiState(
    val isLoading: Boolean = false,
    val items: List<String> = emptyList(),
    val error: String? = null
)

class PokemonListViewModel(
    private val repo: PokemonRepository = PokemonRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListUiState(isLoading = true))
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    init { load() }

    fun load(limit: Int = 100) {
        _uiState.value = ListUiState(isLoading = true)
        viewModelScope.launch {
            val result = repo.getPokemonList(limit)
            _uiState.value = result.fold(
                onSuccess = { names: List<String> ->
                    ListUiState(isLoading = false, items = names)
                },
                onFailure = { e: Throwable ->
                    ListUiState(isLoading = false, error = e.message ?: "Error desconocido")
                }
            )
        }
    }
}
