package com.judahben149.noteify.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.judahben149.noteify.R
import com.judahben149.noteify.databinding.FragmentNoteDetailsBinding
import com.judahben149.noteify.databinding.FragmentPrivateNoteDetailsBinding
import com.judahben149.noteify.model.DeletedNote
import com.judahben149.noteify.model.Note
import com.judahben149.noteify.model.PrivateNote
import com.judahben149.noteify.viewmodel.NoteViewModel

class PrivateNoteDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPrivateNoteDetailsBinding
    private val args by navArgs<PrivateNoteDetailsFragmentArgs>()
    private lateinit var mViewmodel: NoteViewModel

    private var isNoteFavorite: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrivateNoteDetailsBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mViewmodel = ViewModelProvider(this).get(NoteViewModel::class.java)

        binding.noteTitlePrivateNoteDetailsScreen.setText(args.privateNoteDetails.noteTitle)
        binding.noteBodyPrivateNoteDetailsScreen.setText(args.privateNoteDetails.noteBody)
        isNoteFavorite = args.privateNoteDetails.favoriteStatus

        binding.btnCancelPrivateNoteDetailsScreen.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_privateNoteDetailsFragment_to_privateNotesFragment)
        }

        binding.btnSaveNotePrivateNoteDetailsScreen.setOnClickListener {
//            updateNoteInDatabase(isNoteFavorite)
            Navigation.findNavController(binding.root).navigate(R.id.action_privateNoteDetailsFragment_to_privateNotesFragment)
        }

        super.onViewCreated(view, savedInstanceState)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.private_note_details_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_remove_from_private) {
            Snackbar.make(binding.root, "All notes have been removed from private", Snackbar.LENGTH_SHORT).show()
        } else if (item.itemId == R.id.menu_delete_private_note){
            deleteNote()
            Snackbar.make(binding.root, "Note added to trash", Snackbar.LENGTH_SHORT).show()

        }
        return super.onOptionsItemSelected(item)
    }


//    override fun onPause() {
//        //this saves the note once the fragment loses focus or is going to be destroyed. Acts for Auto-save
//        updateNoteInDatabase(isNoteFavorite)
//        super.onPause()
//    }


//    private fun updateNoteInDatabase(favoriteStatus: Boolean) {
//        val noteTitle = binding.noteTitleNoteDetailsScreen.text.toString()
//        val noteBody = binding.noteBodyNoteDetailsScreen.text.toString()
//
//        val note = Note(args.currentNote.id, noteTitle, noteBody, favoriteStatus)
//        mViewmodel.updateNote(note)
//    }

    private fun deleteNote() {
        val title = binding.noteTitlePrivateNoteDetailsScreen.text.toString()
        val body = binding.noteBodyPrivateNoteDetailsScreen.text.toString()

        val noteToDeleteFromNoteTable = PrivateNote(args.privateNoteDetails.id, title, body)
        val noteToAddToDeletedTable = DeletedNote(0, title, body)

        mViewmodel.addDeletedNote(noteToAddToDeletedTable)
        mViewmodel.deletePrivateNote(noteToDeleteFromNoteTable)
    }

}