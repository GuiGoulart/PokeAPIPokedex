package com.severo.pokeapi.podex.ui.adapter

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.severo.pokeapi.podex.R
import com.severo.pokeapi.podex.databinding.ListItemPokemonBinding
import com.severo.pokeapi.podex.model.PokemonResultResponse
import com.severo.pokeapi.podex.ui.adapter.listener.FavoriteListener
import com.severo.pokeapi.podex.util.extensions.getPicUrl

class FavoriteAdapter(var context: Context, var favoriteListener: FavoriteListener) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    private var adapterList: MutableList<PokemonResultResponse>? = null

    fun setList(list: List<PokemonResultResponse>) {
        this.adapterList = list.toMutableList()
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        this.adapterList?.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        return ViewHolder(
            ListItemPokemonBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind((adapterList?.get(position) ?: emptyList<PokemonResultResponse>()) as PokemonResultResponse, position)
    }

    inner class ViewHolder(
        private val binding: ListItemPokemonBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        var dominantColor: Int = 0
        private var picture: String? = ""

        fun bind(pokemonFavoite: PokemonResultResponse, position: Int) {
            binding.apply {
                listItemLottieAnimation.visibility = View.VISIBLE
                listItempokemonItemTitle.text =
                    pokemonFavoite.name?.replaceFirstChar(Char::titlecase) ?: context.getString(R.string.no_name)
                loadImage(this, pokemonFavoite )

                listItemPokemonCardView.setOnClickListener {
                    favoriteListener.clickDetails(pokemonFavoite, dominantColor, picture)
                }

                listItemLottieAnimation.setOnClickListener {
                    listItemLottieAnimation.speed = 1f
                    listItemLottieAnimation.playAnimation()
                    favoriteListener.clickDelete(pokemonFavoite, position)
                }
            }
        }

        private fun loadImage(binding: ListItemPokemonBinding, pokemonResultResponse: PokemonResultResponse) {
            picture = pokemonResultResponse.url?.getPicUrl()

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

    override fun getItemCount(): Int {
        return adapterList?.size ?: 0
    }
}