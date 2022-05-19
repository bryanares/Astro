package com.brian.astro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.brian.astro.data.SpaceViewModel
import com.brian.astro.databinding.FragmentBookMarkBinding

class BookMarkFragment : Fragment() {

    private var _binding: FragmentBookMarkBinding? = null
    private val binding get() = _binding!!

    private lateinit var mSpaceViewModel: SpaceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBookMarkBinding.inflate(inflater,container, false)
        val view = binding.root

        val adapter = BookMarkAdapter()
        val recyclerView = binding.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //viewmodel
        mSpaceViewModel = ViewModelProvider(this).get(SpaceViewModel::class.java)
        mSpaceViewModel.readAllData.observe(viewLifecycleOwner, Observer {   space ->
            adapter.setData(space)
        })
        return view
    }
}