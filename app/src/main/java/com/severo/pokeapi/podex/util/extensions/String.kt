package com.severo.pokeapi.podex.util.extensions

import com.severo.pokeapi.podex.R

fun String.extractId() = this.substringAfter("pokemon").replace("/", "").toInt()

fun String.getPicUrl(): String {
    val id = this.extractId()
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png"
}

fun String.typeColorPokemon(): Int {
    return when (this) {
        "fighting" -> R.color.fighting
        "flying" -> R.color.flying
        "poison" -> R.color.poison
        "ground" -> R.color.ground
        "rock" -> R.color.rock
        "bug" -> R.color.bug
        "ghost" -> R.color.ghost
        "steel" -> R.color.steel
        "fire" -> R.color.fire
        "water" -> R.color.water
        "grass" -> R.color.grass
        "electric" -> R.color.electric
        "psychic" -> R.color.psychic
        "ice" -> R.color.ice
        "dragon" -> R.color.dragon
        "fairy" -> R.color.fairy
        "dark" -> R.color.dark
        else -> R.color.white
    }
}

fun String.statusPokemon(): Int {
    return when (this) {
        "hp" -> R.string.hp
        "attack" -> R.string.attack
        "defense" -> R.string.defense
        "special-attack" -> R.string.special_attack
        "special-defense" -> R.string.special_defense
        "speed" -> R.string.speed
        else -> R.string.others
    }
}

fun String.statusColorPokemon(): Int {
    return when (this) {
        "hp" -> R.color.hp
        "attack" -> R.color.attack
        "defense" -> R.color.defense
        "special-attack" -> R.color.special_attack
        "special-defense" -> R.color.special_defense
        "speed" -> R.color.speed
        else -> R.color.others
    }
}

fun String.statusValueMinMaxPokemon(): String {
    return "$this/300"
}