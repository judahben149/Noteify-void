package com.judahben149.noteify.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.judahben149.noteify.R
import com.judahben149.noteify.adapters.DeletedNotesListAdapter
import com.judahben149.noteify.databinding.FragmentDeletedNotesListBinding
import com.judahben149.noteify.viewmodel.NoteViewModel

class DeletedNotesListFragment : Fragment() {

    private var _binding: FragmentDeletedNotesListBinding? = null
    private val binding get() = _binding!!
    private val adapter = DeletedNotesListAdapter()
    private lateinit var rvList: RecyclerView
    private lateinit var mViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDeletedNotesListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvList = binding.rvDeletedNotesList
        rvList.adapter = adapter

        setupRecyclerViewLayout()
        setUpViewModel()
        setUpObservers()
        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.deleted_notes_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_restoreAllNotes -> {
                Snackbar.make(binding.root, "Restoring all notes", Snackbar.LENGTH_SHORT).show()
            }
            R.id.menu_deleteAllNotes -> {
                deleteNotesForever()
            }
        }
        return super.onOptionsItemSelected(item)
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
        mViewModel.readAllDeletedNotes.observe(viewLifecycleOwner, Observer { note ->
            adapter.setData(note)
        })
    }

    private fun deleteNotesForever() {
        val builder = AlertDialog.Builder(requireContext())

        builder.apply {

            setPositiveButton("Delete forever") { _, _ ->
                mViewModel.deleteAllDeletedNotes()
                Snackbar.make(binding.root, "Trash has been emptied", Snackbar.LENGTH_LONG)
                    .show()
            }
            setNegativeButton("Cancel") { _, _ ->
            }

            setTitle("Delete notes permanently")
            setMessage("Are you sure you want to empty the trash? Notes will be deleted forever.")
            setIcon(R.drawable.ic_delete)
            create()
            show()
        }
    }

}