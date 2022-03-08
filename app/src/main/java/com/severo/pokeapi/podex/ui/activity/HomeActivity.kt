package com.severo.pokeapi.podex.ui.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.severo.pokeapi.podex.data.base.BaseAppCompatActivity
import com.severo.pokeapi.podex.data.model.PokemonResultModel
import com.severo.pokeapi.podex.databinding.ActivityHomeBinding
import com.severo.pokeapi.podex.ui.adapter.PokemonAdapter
import com.severo.pokeapi.podex.ui.adapter.listener.PokemonListener
import com.severo.pokeapi.podex.ui.viewModel.HomeViewModel
import com.severo.pokeapi.podex.util.DOMINANT_COLOR
import com.severo.pokeapi.podex.util.PICTURE
import com.severo.pokeapi.podex.util.POKEMON_RESULT
import com.severo.pokeapi.podex.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class HomeActivity : BaseAppCompatActivity(), PokemonListener {

    private lateinit var binding: ActivityHomeBinding

    private val homeViewModel: HomeViewModel by inject()
    private val adapter: PokemonAdapter by lazy { PokemonAdapter(this, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        initListPokemon()
        setupAdapters()
        setupListeners()
        setupObservers()
    }

    private fun initListPokemon(){
        homeViewModel.setup()
    }

    private fun setupAdapters() {
        binding.homePokemonList.adapter = adapter
    }

    private fun setupListeners() {
        binding.homeSearchView.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(s.toString().isEmpty()){
                    homeViewModel.onAfterTextChanged(null)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {}
        })

        binding.homeSearchClick.setOnClickListener {
            homeViewModel.onAfterTextChanged(binding.homeSearchView.text.toString())
        }

        binding.homePokemonFavorite.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
    }

    private fun setupObservers() = homeViewModel.run {
        pokemonResultLiveData.observe(this@HomeActivity) { singleLiveEvent ->
            singleLiveEvent.getContentIfNotHandled()?.let { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        val data = resource.data
                        data?.let {
                            this@HomeActivity.lifecycleScope.launch {
                                lifecycleScope.launch {
                                    adapter.submitData(it)
                                    adapter.loadStateFlow.collectLatest {
                                        delay(1500L)
                                        when(adapter.itemCount){
                                            0 -> {
                                                binding.homePokemonImageError.visibility = View.VISIBLE
                                                binding.homePokemonTextError.visibility = View.VISIBLE
                                                binding.homePokemonList.visibility = View.GONE
                                            }
                                            else -> {
                                                binding.homePokemonImageError.visibility = View.GONE
                                                binding.homePokemonTextError.visibility = View.GONE
                                                binding.homePokemonList.visibility = View.VISIBLE
                                            }
                                        }
                                    }
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

        navigateToDetails.observe(this@HomeActivity) { singleLiveEvent ->
            singleLiveEvent.getContentIfNotHandled()?.let { triple ->
                val intent = Intent(this@HomeActivity, DetailPokemonActivity::class.java).apply {
                    putExtra(POKEMON_RESULT, triple.first)
                    putExtra(DOMINANT_COLOR, triple.second)
                    putExtra(PICTURE, triple.third)
                }
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this@HomeActivity).toBundle())
            }
        }
    }

    override fun clickDetails(
        pokemonResultModel: PokemonResultModel,
        dominantColor: Int,
        picture: String?
    ) {
        homeViewModel.onItemDetailClick(
            pokemonResultModel,
            dominantColor,
            picture
        )
    }
}