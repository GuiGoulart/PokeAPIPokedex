package com.severo.pokeapi.podex.ui.adapter

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.severo.pokeapi.podex.R
import com.severo.pokeapi.podex.databinding.ListItemPokemonBinding
import com.severo.pokeapi.podex.data.model.PokemonResultModel
import com.severo.pokeapi.podex.ui.adapter.listener.PokemonListener
import com.severo.pokeapi.podex.util.NETWORK_VIEW_TYPE
import com.severo.pokeapi.podex.util.PRODUCT_VIEW_TYPE
import com.severo.pokeapi.podex.util.extensions.getPicUrl

class PokemonAdapter(var context: Context, var pokemonListener: PokemonListener) : PagingDataAdapter<PokemonResultModel, PokemonAdapter.ViewHolder>(PokemonDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)!!
        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemPokemonBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    inner class ViewHolder(
        private val binding: ListItemPokemonBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        var dominantColor: Int = 0
        private var picture: String? = ""

        fun bind(pokemonResultModel: PokemonResultModel) {
            binding.apply {
                listItempokemonItemTitle.text =
                    pokemonResultModel.name?.replaceFirstChar(Char::titlecase) ?: context.getString(R.string.no_name)
                loadImage(this, pokemonResultModel )

                listItemPokemonCardView.setOnClickListener {
                    pokemonListener.clickDetails(pokemonResultModel, dominantColor, picture)
                }
            }
        }

        private fun loadImage(binding: ListItemPokemonBinding, pokemonResultModel: PokemonResultModel) {
            picture = pokemonResultModel.url?.getPicUrl()

            binding.apply {
                Glide.with(root)
                    .load(picture)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            listItemPokemonProgressCircular.isVisible = false
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            val drawable = resource as BitmapDrawable
                            val bitmap = drawable.bitmap
                            Palette.Builder(bitmap).generate {
                                it?.let { palette ->
                                    dominantColor = palette.getDominantColor(
                                        ContextCompat.getColor(
                                            root.context,
                                            R.color.white
                                        )
                                    )
                                    listItemPokemonItemImage.setBackgroundColor(dominantColor)
                                }
                            }
                            listItemPokemonProgressCircular.isVisible = false
                            return false
                        }
                    }).into(listItemPokemonItemImage)
            }
        }
    }

    private class PokemonDiffCallback : DiffUtil.ItemCallback<PokemonResultModel>() {
        override fun areItemsTheSame(oldItem: PokemonResultModel, newItem: PokemonResultModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PokemonResultModel, newItem: PokemonResultModel): Boolean {
            return oldItem == newItem
        }
    }
    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount) {
            NETWORK_VIEW_TYPE
        } else {
            PRODUCT_VIEW_TYPE
        }
    }
}