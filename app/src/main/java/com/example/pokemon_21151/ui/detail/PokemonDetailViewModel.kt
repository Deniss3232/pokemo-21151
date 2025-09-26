package com.example.pokemon_21151.ui.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon_21151.data.PokemonDetail
import com.example.pokemon_21151.repository.PokemonRepository
import kotlinx.coroutines.launch

data class DetailUiState(
    val isLoading: Boolean = true,
    val detail: PokemonDetail? = null,
    val error: String? = null
)

class PokemonDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repo: PokemonRepository = PokemonRepository()
) : ViewModel() {

    private val name: String = checkNotNull(savedStateHandle["name"])

    var uiState = mutableStateOf(DetailUiState())
        private set

    init {
        load()
    }

    /** Carga el detalle llamando al repositorio */
    fun load() {
        viewModelScope.launch {
            uiState.value = DetailUiState(isLoading = true)
            runCatching { repo.getDetail(name) }     // <--- usa getDetail(), no fetchDetail()
                .onSuccess { d ->
                    uiState.value = DetailUiState(isLoading = false, detail = d)
                }
                .onFailure { e ->
                    uiState.value = DetailUiState(isLoading = false, error = e.message)
                }
        }
    }
}
