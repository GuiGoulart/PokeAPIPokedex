package com.severo.pokeapi.podex.util.extensions

import com.severo.pokeapi.podex.R

fun String.extractId() = this.substringAfter("pokemon").replace("/", "").toInt()

fun String.getPicUrl(): String {
    val id = this.extractId()
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png"
}

fun String.typeColorPokemon(): Int {
    return when (this) {
        "bug" -> R.color.bug
        "dark" -> R.color.dark
        "dragon" -> R.color.dragon
        "electric" -> R.color.electric
        "fairy" -> R.color.fairy
        "fighting" -> R.color.fighting
        "fire" -> R.color.fire
        "flying" -> R.color.flying
        "ghost" -> R.color.ghost
        "grass" -> R.color.grass
        "ground" -> R.color.ground
        "ice" -> R.color.ice
        "normal" -> R.color.normal
        "poison" -> R.color.poison
        "psychic" -> R.color.psychic
        "rock" -> R.color.rock
        "steel" -> R.color.steel
        "water" -> R.color.water
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