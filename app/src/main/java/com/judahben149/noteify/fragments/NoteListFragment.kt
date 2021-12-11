package com.judahben149.noteify.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.judahben149.noteify.adapters.NoteListRecyclerViewAdapter
import com.judahben149.noteify.R
import com.judahben149.noteify.adapters.NoteListAdapterSwipeGestures
import com.judahben149.noteify.databinding.FragmentNoteListBinding
import com.judahben149.noteify.model.Note
import com.judahben149.noteify.viewmodel.NoteViewModel

class NoteListFragment: Fragment(), androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentNoteListBinding
    private lateinit var mViewModel: NoteViewModel
    val adapter = NoteListRecyclerViewAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteListBinding.inflate(inflater, container, false)

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


//        //this adds the divider line in between each item
//        DividerItemDecoration(requireContext(), layoutManager.orientation)
//            .apply {
//                rvList.addItemDecoration(this)
//            }


        //this code implements the swipe action for deleting a note
        val swipeGestures = object : NoteListAdapterSwipeGestures(requireContext()){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when(direction) {

                    ItemTouchHelper.LEFT -> {
                        //This is to get the object of the note item which the deleteNote method from the view model needs
                        val deletedItemPosition = adapter.passItemPositionDuringSwipe(viewHolder.absoluteAdapterPosition)
                        mViewModel.deleteNote(deletedItemPosition)
                        Snackbar.make(binding.root, "Note deleted", Snackbar.LENGTH_LONG).apply {
                            setAction(R.string.undo) { _, ->
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

        setHasOptionsMenu(true)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.fabAddNoteButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_noteListFragment_to_addNoteFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }




    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.actionbar_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? androidx.appcompat.widget.SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

        //return true
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
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


    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"

        mViewModel.searchDatabase(searchQuery).observe(this, { list ->
            list.let {
                adapter.setData(it)
            }

        })
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_deleteAllNotes) {
            if (adapter.itemCount > 0) {
                deleteAllNotes()
            } else {
                Snackbar.make(binding.root, "There is no note to delete", Snackbar.LENGTH_SHORT).show()
            }

        }

        return super.onOptionsItemSelected(item)
    }


    private fun deleteAllNotes() {
        val builder = AlertDialog.Builder(requireContext())

        builder.apply {
            setPositiveButton("Yes") {_,_ ->
                mViewModel.deleteAllNotes()
                Snackbar.make(binding.root, "Successfully deleted all notes", Snackbar.LENGTH_LONG).show()

            }

            setNegativeButton("No") {_,_ ->

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
    }



    private fun snackBarDeleteAction(){

    }
}