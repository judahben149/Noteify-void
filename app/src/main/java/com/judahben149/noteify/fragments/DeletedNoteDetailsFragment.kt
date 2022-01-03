package com.judahben149.noteify.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.judahben149.noteify.R
import com.judahben149.noteify.databinding.FragmentAddPrivateNoteBinding
import com.judahben149.noteify.databinding.FragmentDeletedNoteDetailsBinding
import com.judahben149.noteify.hideKeyboard
import com.judahben149.noteify.model.DeletedNote
import com.judahben149.noteify.model.Note
import com.judahben149.noteify.model.PrivateNote
import com.judahben149.noteify.viewmodel.NoteViewModel


class DeletedNoteDetailsFragment : Fragment() {

    private var _binding: FragmentDeletedNoteDetailsBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<DeletedNoteDetailsFragmentArgs>()
    private lateinit var mViewmodel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDeletedNoteDetailsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewmodel = ViewModelProvider(this).get(NoteViewModel::class.java)

        binding.noteTitleDeletedNoteDetailsScreen.setText(args.deletedNoteDetails.noteTitle)
        binding.noteBodyDeletedNoteDetailsScreen.setText(args.deletedNoteDetails.noteBody)

        super.onViewCreated(view, savedInstanceState)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.deleted_note_details_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_restore_deleted_note) {
            restoreNote()
            Snackbar.make(binding.root, "Note restored", Snackbar.LENGTH_SHORT).show()
        } else if (item.itemId == R.id.menu_delete_note_permanently) {
//            deleteNote()
            Snackbar.make(binding.root, "Note deleted permanently", Snackbar.LENGTH_SHORT).show()

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


//    private fun updateNoteInDatabase(favoriteStatus: Boolean) {
//        val noteTitle = binding.noteTitleNoteDetailsScreen.text.toString()
//        val noteBody = binding.noteBodyNoteDetailsScreen.text.toString()
//
//        val note = Note(args.currentNote.id, noteTitle, noteBody, favoriteStatus)
//        mViewmodel.updateNote(note)
//    }

    private fun restoreNote() {
        val title = binding.noteTitleDeletedNoteDetailsScreen.text.toString()
        val body = binding.noteBodyDeletedNoteDetailsScreen.text.toString()

        val noteToDeleteFromDeletedNoteTable = DeletedNote(args.deletedNoteDetails.id, title, body, timeCreated = 0, timeUpdated = 0 , dateDeleted = 0)
        val noteToRestoreToNoteTable = Note(0, title, body, timeCreated = 0, timeUpdated = 0)

        Snackbar.make(binding.root, "Note restored", Snackbar.LENGTH_SHORT).show()

        mViewmodel.addNote(noteToRestoreToNoteTable)
        mViewmodel.deleteNotePermanently(noteToDeleteFromDeletedNoteTable)
    }
}