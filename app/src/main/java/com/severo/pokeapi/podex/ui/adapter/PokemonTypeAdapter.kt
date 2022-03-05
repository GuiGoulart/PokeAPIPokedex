package com.severo.pokeapi.podex.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.severo.pokeapi.podex.databinding.ListTypePokemonBinding
import com.severo.pokeapi.podex.model.TypesResponse
import com.severo.pokeapi.podex.util.extensions.typeColorPokemon

class PokemonTypeAdapter(var context: Context) : RecyclerView.Adapter<PokemonTypeAdapter.ViewHolder>() {
    private var adapterList: List<TypesResponse> = emptyList()

    fun setList(list: List<TypesResponse>) {
        this.adapterList = emptyList()
        this.adapterList = list

        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonTypeAdapter.ViewHolder {
        return ViewHolder(
            ListTypePokemonBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(adapterList[position])
    }

    inner class ViewHolder(
        private val binding: ListTypePokemonBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(typesResponse: TypesResponse) {
            binding.apply {
                typePoke.text = typesResponse.typeResponse?.name.toString()
                typeCardView.setCardBackgroundColor(
                    context.getColor(
                        typesResponse.typeResponse?.name.toString().typeColorPokemon()
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return adapterList.size
    }


}