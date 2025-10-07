package com.example.pokemon_21151.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon_21151.network.PokemonDetailResponse
import com.example.pokemon_21151.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DetailUiState(
    val isLoading: Boolean = false,
    val detail: PokemonDetailResponse? = null,
    val error: String? = null
)

class PokemonDetailViewModel(
    private val repo: PokemonRepository = PokemonRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    /** Llama esto desde la pantalla con el nombre o id */
    fun load(name: String) {
        val n = name.trim()
        if (n.isEmpty()) {
            _uiState.value = DetailUiState(error = "Parámetro 'name' vacío")
            return
        }
        _uiState.value = DetailUiState(isLoading = true)
        viewModelScope.launch {
            val result = repo.getDetail(n)
            _uiState.value = result.fold(
                onSuccess = { d -> DetailUiState(isLoading = false, detail = d) },
                onFailure = { e ->
                    DetailUiState(isLoading = false, error = e.message ?: "Error desconocido")
                }
            )
        }
    }
}
