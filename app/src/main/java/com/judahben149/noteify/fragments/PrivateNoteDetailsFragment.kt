package com.judahben149.noteify.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.judahben149.noteify.R
import com.judahben149.noteify.databinding.FragmentAddPrivateNoteBinding
import com.judahben149.noteify.databinding.FragmentNoteDetailsBinding
import com.judahben149.noteify.databinding.FragmentPrivateNoteDetailsBinding
import com.judahben149.noteify.hideKeyboard
import com.judahben149.noteify.model.DeletedNote
import com.judahben149.noteify.model.Note
import com.judahben149.noteify.model.PrivateNote
import com.judahben149.noteify.viewmodel.NoteViewModel

class PrivateNoteDetailsFragment : Fragment() {

    private var _binding: FragmentPrivateNoteDetailsBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<PrivateNoteDetailsFragmentArgs>()
    private lateinit var mViewmodel: NoteViewModel

    private var isNoteFavorite: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPrivateNoteDetailsBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mViewmodel = ViewModelProvider(this).get(NoteViewModel::class.java)

        binding.noteTitlePrivateNoteDetailsScreen.setText(args.privateNoteDetails.noteTitle)
        binding.noteBodyPrivateNoteDetailsScreen.setText(args.privateNoteDetails.noteBody)
        isNoteFavorite = args.privateNoteDetails.favoriteStatus

        binding.btnCancelPrivateNoteDetailsScreen.setOnClickListener {
            hideKeyboard()
            navigateToListFragment()
        }

        binding.btnSaveNotePrivateNoteDetailsScreen.setOnClickListener {
//            updateNoteInDatabase(isNoteFavorite)
            hideKeyboard()
            navigateToListFragment()
        }

        super.onViewCreated(view, savedInstanceState)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.private_note_details_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_remove_from_private) {
            removeNoteFromPrivate()
            navigateToListFragment()
            Snackbar.make(binding.root, "Note removed from private", Snackbar.LENGTH_SHORT).show()
        } else if (item.itemId == R.id.menu_delete_private_note){
            deleteNote()
            navigateToListFragment()
            Snackbar.make(binding.root, "Note added to trash", Snackbar.LENGTH_SHORT).show()

        }
        return super.onOptionsItemSelected(item)
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


//    override fun onPause() {
//        //this saves the note once the fragment loses focus or is going to be destroyed. Acts for Auto-save
//        updateNoteInDatabase(isNoteFavorite)
//        super.onPause()
//    }


    private fun removeNoteFromPrivate() {
        val noteTitle = binding.noteTitlePrivateNoteDetailsScreen.text.toString()
        val noteBody = binding.noteBodyPrivateNoteDetailsScreen.text.toString()

        val noteToRemoveFromPrivateNotes = PrivateNote(args.privateNoteDetails.id, noteTitle, noteBody)
        val noteToAddToNotes = Note(0, noteTitle, noteBody, timeCreated = 0, timeUpdated = 0)

        mViewmodel.addNote(noteToAddToNotes)
        mViewmodel.deletePrivateNote(noteToRemoveFromPrivateNotes)
    }

    private fun deleteNote() {
        val title = binding.noteTitlePrivateNoteDetailsScreen.text.toString()
        val body = binding.noteBodyPrivateNoteDetailsScreen.text.toString()

        val noteToDeleteFromNoteTable = PrivateNote(args.privateNoteDetails.id, title, body)
        val noteToAddToDeletedTable = DeletedNote(0, title, body, dateDeleted = 0)

        mViewmodel.addDeletedNote(noteToAddToDeletedTable)
        mViewmodel.deletePrivateNote(noteToDeleteFromNoteTable)
    }

    private fun navigateToListFragment() {
        Navigation.findNavController(binding.root).navigate(R.id.action_privateNoteDetailsFragment_to_privateNotesFragment)
    }

}