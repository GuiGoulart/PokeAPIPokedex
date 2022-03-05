package com.severo.pokeapi.podex.ui.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.lifecycleScope
import com.severo.pokeapi.podex.data.base.BaseAppCompatActivity
import com.severo.pokeapi.podex.databinding.ActivityHomeBinding
import com.severo.pokeapi.podex.model.PokemonResultResponse
import com.severo.pokeapi.podex.ui.adapter.PokemonAdapter
import com.severo.pokeapi.podex.ui.adapter.listener.PokemonListener
import com.severo.pokeapi.podex.ui.viewModel.HomeViewModel
import com.severo.pokeapi.podex.util.Resource
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
        setupAdapters()
        setupListeners()
        setupObservers()
    }

    private fun setupAdapters() {
        binding.pokemonList.adapter = adapter
    }

    private fun setupListeners() {
        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                homeViewModel.getPokemons(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun setupObservers() {
        homeViewModel.pokemonResultLiveData.observe(this) { singleLiveEvent ->
            singleLiveEvent.getContentIfNotHandled()?.let { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        val data = resource.data
                        data?.let {
                            this.lifecycleScope.launch {
                                adapter.submitData(it)
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

    override fun clickDetails(
        pokemonResultResponse: PokemonResultResponse,
        dominantColor: Int,
        picture: String?
    ) {
        val intent = Intent(this, DetailPokemonActivity::class.java)
        intent.putExtra("pokemonResult", pokemonResultResponse)
        intent.putExtra("dominantColor", dominantColor)
        intent.putExtra("picture", picture)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }
}