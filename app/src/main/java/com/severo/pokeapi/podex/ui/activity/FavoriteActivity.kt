package com.severo.pokeapi.podex.ui.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import com.severo.pokeapi.podex.data.base.BaseAppCompatActivity
import com.severo.pokeapi.podex.databinding.ActivityFavoriteBinding
import com.severo.pokeapi.podex.model.PokemonResultResponse
import com.severo.pokeapi.podex.ui.adapter.FavoriteAdapter
import com.severo.pokeapi.podex.ui.adapter.listener.FavoriteListener
import com.severo.pokeapi.podex.ui.viewModel.FavoriteViewModel
import com.severo.pokeapi.podex.util.DOMINANT_COLOR
import com.severo.pokeapi.podex.util.PICTURE
import com.severo.pokeapi.podex.util.POKEMON_RESULT
import com.severo.pokeapi.podex.util.Resource
import org.koin.android.ext.android.inject

class FavoriteActivity : BaseAppCompatActivity(), FavoriteListener {

    private lateinit var binding: ActivityFavoriteBinding

    private val favoriteViewModel: FavoriteViewModel by inject()

    private val favoriteAdapter: FavoriteAdapter by lazy {
        FavoriteAdapter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

    }

    private fun init() {
        setupAdapters()
        setupObservers()
        setupListeners()
        initListPokemonViewModel()
    }

    override fun onResume() {
        super.onResume()
        initListPokemonViewModel()
    }

    private fun initListPokemonViewModel() {
        favoriteViewModel.setupInit()
    }

    private fun setupAdapters() {
        binding.favoritePokemonList.adapter = favoriteAdapter
    }

    private fun setupListeners() {
        binding.favoritePokemonBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupObservers() = favoriteViewModel.run {
        pokemonFavoriteResultLiveData.observe(this@FavoriteActivity) { singleLiveEvent ->
            singleLiveEvent.getContentIfNotHandled()?.let { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        val data = resource.data
                        data?.let {
                            favoriteAdapter.setList(it)
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

        navigateToDetails.observe(this@FavoriteActivity) { singleLiveEvent ->
            singleLiveEvent.getContentIfNotHandled()?.let { triple ->
                val intent = Intent(this@FavoriteActivity, DetailPokemonActivity::class.java).apply {
                    putExtra(POKEMON_RESULT, triple.first)
                    putExtra(DOMINANT_COLOR, triple.second)
                    putExtra(PICTURE, triple.third)
                }
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this@FavoriteActivity).toBundle())
            }
        }

        removePokemonFavorite.observe(this@FavoriteActivity) { singleLiveEvent ->
            singleLiveEvent.getContentIfNotHandled()?.let { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        val data = resource.data
                        data?.let {
                            favoriteAdapter.removeItem(it)
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

    override fun clickDetails(
        pokemonResultResponse: PokemonResultResponse,
        dominantColor: Int,
        picture: String?
    ) {
        favoriteViewModel.onItemDetailClick(
            pokemonResultResponse,
            dominantColor,
            picture
        )
    }

    override fun clickDelete(
        pokemonResultResponse: PokemonResultResponse,
        positionFavorite: Int
    ) {
        favoriteViewModel.onItemRemoveClick(pokemonResultResponse, positionFavorite)
    }
}