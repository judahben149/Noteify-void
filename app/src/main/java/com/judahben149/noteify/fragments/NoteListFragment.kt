package com.judahben149.noteify.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.judahben149.noteify.NoteLookup
import com.judahben149.noteify.adapters.NoteListRecyclerViewAdapter
import com.judahben149.noteify.R
import com.judahben149.noteify.adapters.NoteListAdapterSwipeGestures
import com.judahben149.noteify.databinding.FragmentNoteListBinding
import com.judahben149.noteify.model.Note
import com.judahben149.noteify.viewmodel.NoteViewModel

class NoteListFragment : Fragment() { //, androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentNoteListBinding
    private lateinit var mViewModel: NoteViewModel
    val adapter = NoteListRecyclerViewAdapter()
    private var tracker: SelectionTracker<Long>? = null

    private val noteObject = emptyList<Note>()
//    val numberOfNotes = noteObject.size


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteListBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val rvList = binding.rvList
        rvList.adapter = adapter


        //tracker for the recycler view multi-select functionality
        tracker = SelectionTracker.Builder<Long>(
            "selection-1",
            rvList,
            StableIdKeyProvider(rvList),
            NoteLookup(rvList),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()

        //then associate the tracker with your recycler view adapter
        adapter.setTracker(tracker)

        val layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL,
            false
        ).apply {
            rvList.layoutManager = this
        }

        setUpViewModelAndObserver()
        recyclerViewDivider(rvList, layoutManager)
        setUpSwipeGestures(rvList)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                if (query != null) {
                    searchDatabase(query)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) {
                    searchDatabase(query)
                }
                return true
            }
        })

        binding.fabAddNoteButton.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_noteListFragment_to_addNoteFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }



    private fun setUpViewModelAndObserver() {
        //instantiate viewmodel and set up observer
        mViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        mViewModel.readAllNotes.observe(viewLifecycleOwner, Observer { note ->
            adapter.setData(note)
        })
    }


    private fun recyclerViewDivider(rvList: RecyclerView, layoutManager: LinearLayoutManager) {
        //this adds the divider line in between each item
        DividerItemDecoration(requireContext(), layoutManager.orientation)
            .apply {
                rvList.addItemDecoration(this)
            }
    }


    private fun setUpSwipeGestures(rvList: RecyclerView) {
        //this code implements the swipe action for deleting a note
        val swipeGestures = object : NoteListAdapterSwipeGestures(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when (direction) {

                    ItemTouchHelper.LEFT -> {
                        //This is to get the object of the note item which the deleteNote method from the view model needs
                        val deletedItemPosition =
                            adapter.passItemPositionDuringSwipe(viewHolder.absoluteAdapterPosition)
                        mViewModel.deleteNote(deletedItemPosition)
                        Snackbar.make(binding.root, "Note deleted", Snackbar.LENGTH_LONG).apply {
                            setAction(R.string.undo) { _ ->
                                undoDelete(deletedItemPosition)
                            }
                            show()
                        }
                    }
//                    ItemTouchHelper.RIGHT -> {
//
//                    }

                }
            }
        }

        val touchHelper = ItemTouchHelper(swipeGestures)
        touchHelper.attachToRecyclerView(rvList)
    }


    //function to search for a particular string either in the title or body of the note
    fun searchDatabase(query: String) {
        val searchQuery = "%$query%"

        mViewModel.searchDatabase(searchQuery).observe(this, { list ->
            list.let {
                adapter.setData(it)
            }

        })
    }


    private fun deleteAllNotes() {
        val builder = AlertDialog.Builder(requireContext())

        builder.apply {

            setPositiveButton("Yes") { _, _ ->
                mViewModel.deleteAllNotes()
                Snackbar.make(binding.root, "Successfully deleted all notes", Snackbar.LENGTH_LONG)
                    .show()

            }
            setNegativeButton("No") { _, _ ->

            }

            setTitle("Delete all notes")
            setMessage("Are you sure you want to delete all notes?")
            setIcon(R.drawable.ic_delete)
            create()
            show()
        }
    }

    private fun undoDelete(position: Note) {
        mViewModel.addNote(position)
        adapter.notifyDataSetChanged()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_deleteAllNotes) {
            if (adapter.itemCount > 0) {
                deleteAllNotes()
            } else {
                Snackbar.make(binding.root, "There is no note to delete", Snackbar.LENGTH_SHORT)
                    .show()
            }

        }
        return super.onOptionsItemSelected(item)
    }

}