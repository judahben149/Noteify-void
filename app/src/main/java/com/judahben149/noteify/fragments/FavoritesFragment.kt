package com.judahben149.noteify.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.judahben149.noteify.R
import com.judahben149.noteify.adapters.FavoriteNotesAdapter
import com.judahben149.noteify.databinding.FragmentFavoritesBinding
import com.judahben149.noteify.viewmodel.NoteViewModel


class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private val adapter = FavoriteNotesAdapter()
    private lateinit var mViewModel: NoteViewModel
    private lateinit var rvList: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        rvList = binding.rvFavoritesNotesList
        rvList.adapter = adapter

        setupRecyclerViewLayout() //this has a function which sets up divider

        mViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        mViewModel.readAllFavoriteNote.observe(viewLifecycleOwner, Observer { note ->
            adapter.setData(note)
        })


        return binding.root
    }

    private fun setupRecyclerViewLayout() {
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false).apply {
            rvList.layoutManager = this
        }
        recyclerViewDivider(rvList, layoutManager)
    }


    private fun recyclerViewDivider(rvList: RecyclerView, layoutManager: LinearLayoutManager) {
        //this adds the divider line in between each item
        DividerItemDecoration(requireContext(), layoutManager.orientation)
            .apply {
                rvList.addItemDecoration(this)
            }
    }

}