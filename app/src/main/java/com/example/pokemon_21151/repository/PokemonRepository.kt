package com.example.pokemon_21151.repository

import com.example.pokemon_21151.data.PokeResponse
import com.example.pokemon_21151.data.PokemonDetail
import com.example.pokemon_21151.network.RetrofitClient

class PokemonRepository {
    private val api = RetrofitClient.api

    suspend fun getList(limit: Int): PokeResponse = api.getPokemonList(limit)
    suspend fun getDetail(name: String): PokemonDetail = api.getPokemonDetail(name)
}
