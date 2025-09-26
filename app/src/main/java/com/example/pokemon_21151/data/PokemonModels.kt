package com.example.pokemon_21151.data

// ---- Lista ----
data class Pokemon(
    val name: String,
    val url: String
)

data class PokeResponse(
    val results: List<Pokemon>
)

// ---- Detalle ----
data class PokemonDetail(
    val name: String,
    val sprites: Sprites,
    val types: List<TypeSlot>
) {
    val imageUrl: String get() = sprites.front_default ?: ""
}

data class Sprites(
    val front_default: String?,
    val back_default: String?,
    val front_shiny: String?,
    val back_shiny: String?
)

data class TypeSlot(
    val slot: Int,
    val type: TypeName
)

data class TypeName(
    val name: String,
    val url: String
)
