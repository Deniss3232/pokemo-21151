package com.example.pokemon_21151.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokemon_21151.ui.detail.PokemonDetailScreen
import com.example.pokemon_21151.ui.list.PokemonListScreen

private object Routes {
    const val MAIN = "main"
    const val DETAIL = "detail/{name}"
}

@Composable
fun AppNav() {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = Routes.MAIN) {

        // Lista
        composable(Routes.MAIN) {
            PokemonListScreen(
                onPokemonClick = { name: String ->
                    nav.navigate("detail/$name")
                }
            )
        }

        // Detalle
        composable(
            route = Routes.DETAIL,
            arguments = listOf(
                navArgument("name") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name").orEmpty()
            PokemonDetailScreen(
                pokemonName = name,
                onBack = { nav.popBackStack() }
            )
        }
    }
}
