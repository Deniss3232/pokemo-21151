package com.example.pokemon_21151.repository

import com.example.pokemon_21151.network.PokeApi
import com.example.pokemon_21151.network.PokemonDetailResponse
import com.example.pokemon_21151.network.RetrofitClient

class PokemonRepository(
    private val api: PokeApi = RetrofitClient.api
) {

    suspend fun getPokemonList(limit: Int = 100): Result<List<String>> = try {
        val resp = api.getPokemonList(limit = limit)
        Result.success(resp.results.map { it.name })
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun getDetail(name: String): Result<PokemonDetailResponse> = try {
        val resp = api.getPokemonDetail(name)
        Result.success(resp)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
