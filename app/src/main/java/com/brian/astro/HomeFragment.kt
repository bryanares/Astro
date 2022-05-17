package com.brian.astro

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brian.astro.databinding.FragmentHomeBinding
import java.io.IOException
import java.net.HttpRetryException

const val TAG = "HomeFragment"

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var astroAdapter: AstroAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }
    private fun setupRecyclerview() = binding.rvAstro.apply {
        astroAdapter = AstroAdapter()
        adapter = astroAdapter
        layoutManager = LinearLayoutManager(this.context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        lifecycleScope.launchWhenCreated {
            binding.progressBar.isVisible = true
            val response = try {
                RetrofitInstance.api.getAstros()
            } catch (e: IOException) {
                Log.e(TAG, "IO exception, check internet connection")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            } catch (e: HttpRetryException) {
                Log.e(TAG, "Http exception, unexpected response")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null) {
                astroAdapter.astros= response.body()!!
            } else {
                Log.e(TAG, "Response not successful")
            }
            binding.progressBar.isVisible = false
        }
        setupRecyclerview()
    }


}