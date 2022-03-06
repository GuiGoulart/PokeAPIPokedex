package com.severo.pokeapi.podex.ui.activity

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.severo.pokeapi.podex.R
import com.severo.pokeapi.podex.data.base.BaseAppCompatActivity
import com.severo.pokeapi.podex.databinding.ActivityDetailPokemonBinding
import com.severo.pokeapi.podex.model.PokemonResultResponse
import com.severo.pokeapi.podex.ui.adapter.PokemonStatsAdapter
import com.severo.pokeapi.podex.ui.adapter.PokemonTypeAdapter
import com.severo.pokeapi.podex.ui.viewModel.DetailsPokemonViewModel
import com.severo.pokeapi.podex.util.DOMINANT_COLOR
import com.severo.pokeapi.podex.util.PICTURE
import com.severo.pokeapi.podex.util.POKEMON_RESULT
import com.severo.pokeapi.podex.util.Resource
import org.koin.android.ext.android.inject

class DetailPokemonActivity : BaseAppCompatActivity() {

    private lateinit var binding: ActivityDetailPokemonBinding

    private val detailsPokemonViewModel: DetailsPokemonViewModel by inject()
    private val adapterType: PokemonTypeAdapter by lazy { PokemonTypeAdapter(this) }
    private val adapterStatus: PokemonStatsAdapter by lazy { PokemonStatsAdapter(this) }

    private var dominantColor: Int = 0
    private var picture: String = ""
    private var pokemonResultResponse: PokemonResultResponse = PokemonResultResponse("", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPokemonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

    }

    private fun init(){
        initExtra()
        setupListeners()
        setupAdapters()
        setupObservers()
    }
    private fun initExtra(){
        intent.extras?.let {
            dominantColor = it.getInt(DOMINANT_COLOR, 0)
            picture = it.getString(PICTURE, "")
            pokemonResultResponse = it.getSerializable(POKEMON_RESULT) as PokemonResultResponse

            pokemonResultResponse.url?.let { url -> detailsPokemonViewModel.getPokemonDetail(url) }
            binding.detailsPokemonTitle.text =
                pokemonResultResponse.name?.replaceFirstChar(Char::titlecase) ?: this.getString(R.string.no_name)
        }
    }

    private fun setupAdapters() {
        binding.detailsPokemonTypeList.adapter = adapterType
        binding.detailsPokemonStatsList.adapter = adapterStatus
    }

    private fun setupListeners() {
        binding.detailsPokemonBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupObservers() {
        detailsPokemonViewModel.pokemonDetailResultLiveData.observe(this) { singleLiveEvent ->
            singleLiveEvent.getContentIfNotHandled()?.let { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        val data = resource.data
                        data?.let {

                            binding.detailsPokemonImageError?.visibility = View.GONE
                            binding.detailsPokemonTextError?.visibility = View.GONE
                            binding.detailsPokemonConstraintDetail?.visibility = View.VISIBLE

                            setImageDeminantColor()

                            binding.detailsPokemonHeight.text = it.height.toString()
                            binding.detailsPokemonWeight.text = it.weight.toString()

                            it.types?.let { types ->
                                adapterType.setList(types)
                            }

                            it.stats?.let { stats ->
                                adapterStatus.setList(stats)
                            }

                            binding.detailsPokemonFavorite.setOnClickListener { _ ->
                                detailsPokemonViewModel.onClickFavoritePokemon(it)
                            }
                        }
                        dismissProgressDialog()
                    }
                    Resource.Status.LOADING -> {
                        showProgressDialog()
                    }
                    Resource.Status.ERROR -> {
                        binding.detailsPokemonImageError?.visibility = View.VISIBLE
                        binding.detailsPokemonTextError?.visibility = View.VISIBLE
                        binding.detailsPokemonConstraintDetail?.visibility = View.GONE
                        dismissProgressDialog()
                    }
                }
            }
        }

        detailsPokemonViewModel.onClickPokemonDetailLiveData.observe(this) { singleLiveEvent ->
            singleLiveEvent.getContentIfNotHandled()?.let { favoritePokemon ->
                if (favoritePokemon) {
                    binding.detailsPokemonFavorite.speed = 1f
                    binding.detailsPokemonFavorite.playAnimation()
                } else {
                    binding.detailsPokemonFavorite.progress = 0F
                }
            }
        }
    }

    private fun setImageDeminantColor(){
        Glide.with(this)
            .load(picture)
            .transition(DrawableTransitionOptions.withCrossFade())
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
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
                        it?.let {
                            binding.detailsPokemonCardView.setCardBackgroundColor(dominantColor)
                        }
                    }
                    return false
                }
            }).into(binding.detailsPokemonImage)
    }
}