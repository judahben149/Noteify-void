package com.judahben149.noteify.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.judahben149.noteify.R
import com.judahben149.noteify.adapters.DeletedNotesListAdapter
import com.judahben149.noteify.adapters.PrivateNotesAdapter
import com.judahben149.noteify.databinding.FragmentDeletedNotesListBinding
import com.judahben149.noteify.databinding.FragmentPrivateNotesBinding
import com.judahben149.noteify.viewmodel.NoteViewModel

class PrivateNotesFragment : Fragment() {

    private lateinit var binding: FragmentPrivateNotesBinding
    private val adapter = PrivateNotesAdapter()
    private lateinit var rvList: RecyclerView
    private lateinit var mViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrivateNotesBinding.inflate(inflater, container, false)

        rvList = binding.rvPrivateNotes
        rvList.adapter = adapter

        setupRecyclerViewLayout()
        setUpViewModel()
        setUpObservers()
        setHasOptionsMenu(true)

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.deleted_notes_menu, menu)
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

    private fun setUpViewModel() {
        mViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
    }

    private fun setUpObservers() {
        mViewModel.readAllPrivateNotes.observe(viewLifecycleOwner, Observer { note ->
            adapter.setData(note)
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.fabAddPrivateNoteButton.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_privateNotesFragment_to_addPrivateNoteFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }

}