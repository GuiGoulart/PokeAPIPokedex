package com.severo.pokeapi.podex.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.severo.pokeapi.podex.databinding.ListStatusPokemonBinding
import com.severo.pokeapi.podex.model.StatsResponse
import com.severo.pokeapi.podex.util.extensions.statusColorPokemon
import com.severo.pokeapi.podex.util.extensions.statusPokemon
import com.severo.pokeapi.podex.util.extensions.statusValueMinMaxPokemon

class PokemonStatsAdapter(var context: Context) : RecyclerView.Adapter<PokemonStatsAdapter.ViewHolder>() {
    private var adapterList: List<StatsResponse> = emptyList()

    fun setList(list: List<StatsResponse>) {
        this.adapterList = emptyList()
        this.adapterList = list

        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonStatsAdapter.ViewHolder {
        return ViewHolder(
            ListStatusPokemonBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(adapterList[position])
    }

    inner class ViewHolder(
        private val binding: ListStatusPokemonBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(types: StatsResponse) {
            binding.apply {
                statusTile.text = context.resources.getString(types.statResponse.name.statusPokemon())

                statusProgressView.progress = types.base_stat.toFloat()
                statusProgressView.labelText = types.base_stat.toString().statusValueMinMaxPokemon()

                statusTile.setTextColor(ContextCompat.getColor(context, types.statResponse.name.statusColorPokemon()))
                statusProgressView.highlightView.color = ContextCompat.getColor(context, types.statResponse.name.statusColorPokemon())
            }
        }
    }

    override fun getItemCount(): Int {
        return adapterList.size
    }
}