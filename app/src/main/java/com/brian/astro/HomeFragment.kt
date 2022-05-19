package com.brian.astro

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.brian.astro.data.Space
import com.brian.astro.data.SpaceViewModel
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

    private lateinit var mSpaceViewModel: SpaceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        mSpaceViewModel = ViewModelProvider(this).get(SpaceViewModel::class.java)

        astroAdapter = AstroAdapter(mutableListOf(), this)
        astroAdapter.setOnItemClickListener(object : AstroAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                insertDataToDatabase()
            }

        })
        return view

    }
    private fun setupRecyclerview() = binding.rvAstro.apply {

        adapter = astroAdapter
        layoutManager = LinearLayoutManager(this.context)
    }

    private fun insertDataToDatabase() {
        val title = R.id.astroTitle.toString()
        val description = R.id.astroDescription.toString()

        if(inputCheck(title, description)){
            //create space object
            val space = Space(0, title, description)
            //add data to database
            mSpaceViewModel.addSpace(space)
            //notify user of astro save
            Toast.makeText(requireContext(), "Astro Saved!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(title: String, description: String): Boolean{
        return  !(TextUtils.isEmpty(title) && TextUtils.isEmpty(description))
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