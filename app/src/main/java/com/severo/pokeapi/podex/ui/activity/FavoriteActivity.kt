package com.severo.pokeapi.podex.ui.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.severo.pokeapi.podex.data.base.BaseAppCompatActivity
import com.severo.pokeapi.podex.databinding.ActivityFavoriteBinding
import com.severo.pokeapi.podex.data.model.PokemonResultModel
import com.severo.pokeapi.podex.ui.adapter.FavoriteAdapter
import com.severo.pokeapi.podex.ui.viewModel.FavoriteViewModel
import com.severo.pokeapi.podex.util.DOMINANT_COLOR
import com.severo.pokeapi.podex.util.PICTURE
import com.severo.pokeapi.podex.util.POKEMON_RESULT
import com.severo.pokeapi.podex.util.Resource
import org.koin.android.ext.android.inject

class FavoriteActivity : BaseAppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    private val favoriteViewModel: FavoriteViewModel by inject()

    private val favoriteAdapter: FavoriteAdapter by lazy {
        FavoriteAdapter(this, ::onDetailsClick, ::onDeleteClick, ::onEmptySize)
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
                        data?.let { listPokemonResult ->
                            if(listPokemonResult.isNotEmpty()){
                                binding.favoritePokemonImageError.visibility = View.GONE
                                binding.favoritePokemonTextError.visibility = View.GONE
                                binding.favoritePokemonList.visibility = View.VISIBLE
                                favoriteAdapter.setList(listPokemonResult)
                            }else{
                                binding.favoritePokemonImageError.visibility = View.VISIBLE
                                binding.favoritePokemonTextError.visibility = View.VISIBLE
                                binding.favoritePokemonList.visibility = View.GONE
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

    private fun onDetailsClick(
        pokemonResultModel: PokemonResultModel,
        dominantColor: Int,
        picture: String?
    ) {
        favoriteViewModel.onItemDetailClick(
            pokemonResultModel,
            dominantColor,
            picture
        )
    }

    private fun onDeleteClick(
        pokemonResultModel: PokemonResultModel,
        positionFavorite: Int
    ) {
        favoriteViewModel.onItemRemoveClick(pokemonResultModel, positionFavorite)
    }

    private fun onEmptySize() {
        binding.favoritePokemonImageError.visibility = View.VISIBLE
        binding.favoritePokemonTextError.visibility = View.VISIBLE
        binding.favoritePokemonList.visibility = View.GONE
    }
}