package com.example.pokemon_21151.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// ====== MODELOS ======
data class PokemonItem(val name: String, val url: String)
data class PokemonListResponse(val results: List<PokemonItem>)

data class TypeInfo(val name: String, val url: String)
data class TypeSlot(val slot: Int, val type: TypeInfo)

data class Sprites(
    val front_default: String?,
    val back_default: String?,
    val front_shiny: String?,
    val back_shiny: String?
)

data class PokemonDetailResponse(
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: Sprites,
    val types: List<TypeSlot>
)

// ====== API ======
interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0
    ): PokemonListResponse

    @GET("pokemon/{idOrName}")
    suspend fun getPokemonDetail(
        @Path("idOrName") idOrName: String   // puede ser nombre o id
    ): PokemonDetailResponse
}
