package com.severo.pokeapi.podex.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.severo.pokeapi.podex.R
import com.severo.pokeapi.podex.databinding.ListStatusPokemonBinding
import com.severo.pokeapi.podex.data.model.StatsModel
import com.severo.pokeapi.podex.util.extensions.statusColorPokemon
import com.severo.pokeapi.podex.util.extensions.statusPokemon
import com.severo.pokeapi.podex.util.extensions.statusValueMinMaxPokemon

class PokemonStatsAdapter(var context: Context) : RecyclerView.Adapter<PokemonStatsAdapter.ViewHolder>() {
    private var adapterList: List<StatsModel> = emptyList()

    fun setList(list: List<StatsModel>) {
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

        fun bind(types: StatsModel) {
            binding.apply {
                statusTile.text = types.statModel?.name?.let { context.resources.getString(it.statusPokemon()) }

                statusProgressView.progress = types.base_stat?.toFloat() ?: 0F
                statusProgressView.labelText = types.base_stat.toString().statusValueMinMaxPokemon()

                statusTile.setTextColor(types.statModel?.name?.let { ContextCompat.getColor(context, it.statusColorPokemon()) } ?: context.getColor(
                    R.color.others))
                statusProgressView.highlightView.color = types.statModel?.name?.let { ContextCompat.getColor(context, it.statusColorPokemon()) } ?: context.getColor(
                    R.color.others)
            }
        }
    }

    override fun getItemCount(): Int {
        return adapterList.size
    }
}