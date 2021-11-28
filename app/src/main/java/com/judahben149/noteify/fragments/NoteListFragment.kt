package com.judahben149.noteify.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.judahben149.noteify.adapters.NoteListRecyclerViewAdapter
import com.judahben149.noteify.R
import com.judahben149.noteify.databinding.FragmentNoteListBinding
import com.judahben149.noteify.viewmodel.NoteViewModel

class NoteListFragment: Fragment() {

    private lateinit var binding: FragmentNoteListBinding
    private lateinit var mViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteListBinding.inflate(inflater, container, false)

        val adapter = NoteListRecyclerViewAdapter()
        val rvList = binding.rvList

        rvList.adapter = adapter

        val layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL,
            false
        ).apply {
            rvList.layoutManager = this
        }

        mViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        mViewModel.readAllNotes.observe(viewLifecycleOwner, Observer { note ->
            adapter.setData(note)
        })

        DividerItemDecoration(requireContext(), layoutManager.orientation)
            .apply {
                rvList.addItemDecoration(this)
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.fabAddNoteButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_noteListFragment_to_addNoteFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }
}