package com.severo.pokeapi.podex.ui.activity

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
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
import com.severo.pokeapi.podex.util.Resource
import org.koin.android.ext.android.inject

class DetailPokemonActivity : BaseAppCompatActivity() {

    private lateinit var binding: ActivityDetailPokemonBinding

    private val detailsPokemonViewModel: DetailsPokemonViewModel by inject()
    private val adapterType: PokemonTypeAdapter by lazy { PokemonTypeAdapter(this) }
    private val adapterStatus: PokemonStatsAdapter by lazy { PokemonStatsAdapter(this) }

    private var favorite: Boolean = false

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
        setColorTextDominant()
        setupListeners()
        setupAdapters()
        setupObservers()
    }
    private fun initExtra(){
        intent.extras?.let {
            dominantColor = it.getInt("dominantColor", 0)
            picture = it.getString("picture", "")
            pokemonResultResponse = it.getSerializable("pokemonResult") as PokemonResultResponse

            detailsPokemonViewModel.getPokemonDetail(pokemonResultResponse.url)
            binding.detailsPokemonTitle.text = pokemonResultResponse.name.replaceFirstChar(Char::titlecase)
        }
    }

    private fun setColorTextDominant(){
        binding.detailsPokemonTitle.setTextColor(dominantColor)
        binding.detailsPokemonWeight.setTextColor(dominantColor)
        binding.detailsPokemonHeight.setTextColor(dominantColor)
        binding.detailsPokemonWeightTitle.setTextColor(dominantColor)
        binding.detailsPokemonHeightTitle.setTextColor(dominantColor)
        binding.detailsPokemonBasePerformance.setTextColor(dominantColor)
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
                            setImageDeminantColor()
                            binding.detailsPokemonHeight.text = it.height.toString()
                            binding.detailsPokemonWeight.text = it.weight.toString()
                            adapterType.setList(it.types)
                            adapterStatus.setList(it.stats)

                            binding.detailsPokemonFavorite?.setOnClickListener { _ ->
                                favorite = !favorite
                                if(favorite){
                                    detailsPokemonViewModel.postBeeceptor(it)
                                    binding.detailsPokemonFavorite?.setImageResource(R.drawable.ic_baseline_favorite_24)
                                } else {
                                    binding.detailsPokemonFavorite?.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                                }
                            }
                        }
                        dismissProgressDialog()
                    }
                    Resource.Status.LOADING -> {
                        showProgressDialog()
                    }
                    Resource.Status.ERROR -> {
                        dismissProgressDialog()
                    }
                }
            }
        }
    }

    private fun setImageDeminantColor(){
        Glide.with(this)
            .load(intent.extras?.getString("picture", ""))
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