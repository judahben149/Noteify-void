package com.judahben149.noteify.fragments

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.judahben149.noteify.R
import com.judahben149.noteify.databinding.FragmentNoteDetailsBinding
import com.judahben149.noteify.hideKeyboard
import com.judahben149.noteify.model.DeletedNote
import com.judahben149.noteify.model.Note
import com.judahben149.noteify.viewmodel.NoteViewModel
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

class NoteDetailsFragment: Fragment() {

    private var _binding: FragmentNoteDetailsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<NoteDetailsFragmentArgs>()
    private lateinit var mViewmodel: NoteViewModel

    private var isNoteFavorite: Boolean = false
    var timeCreated: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteDetailsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)


//        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                hideKeyboard()
//                updateNoteInDatabase(isNoteFavorite)
//                navigateToListFragment()
//            }
//        })
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mViewmodel = ViewModelProvider(this).get(NoteViewModel::class.java)
        timeCreated = PrettyTime().format(Date(args.currentNote.timeCreated))

        binding.noteTitleNoteDetailsScreen.setText(args.currentNote.noteTitle)
        binding.noteBodyNoteDetailsScreen.setText(args.currentNote.noteBody)
        binding.dateCreatedNoteDetailsScreen.text = "Created: " + timeCreated

        isNoteFavorite = args.currentNote.favoriteStatus

        binding.btnCancelNoteDetailsScreen.setOnClickListener {
            hideKeyboard()
            navigateToListFragment()
        }

        binding.btnSaveNoteNoteDetailsScreen.setOnClickListener {
            updateNoteInDatabase(isNoteFavorite)
            hideKeyboard()
            navigateToListFragment()
        }

        super.onViewCreated(view, savedInstanceState)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_details_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.addToFavorites) {
                Snackbar.make(binding.root, "Note added to favorites", Snackbar.LENGTH_SHORT).show()
                isNoteFavorite = true
            } else if (item.itemId == R.id.deleteNote){
                deleteNote()
                navigateToListFragment()
                Snackbar.make(binding.root, "Note added to trash", Snackbar.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onPause() {
        //this saves the note once the fragment loses focus or is going to be destroyed. Acts for Auto-save
        hideKeyboard()
        updateNoteInDatabase(isNoteFavorite)
        super.onPause()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun updateNoteInDatabase(favoriteStatus: Boolean) {
        val noteTitle = binding.noteTitleNoteDetailsScreen.text.toString()
        val noteBody = binding.noteBodyNoteDetailsScreen.text.toString()
        val timeUpdated = System.currentTimeMillis()

        val note = Note(args.currentNote.id, noteTitle, noteBody, favoriteStatus, timeUpdated = timeUpdated, timeCreated = args.currentNote.timeCreated)
        mViewmodel.updateNote(note)
    }

    private fun deleteNote() {
        val title = binding.noteTitleNoteDetailsScreen.text.toString()
        val body = binding.noteTitleNoteDetailsScreen.text.toString()

        val noteToDeleteFromNoteTable = Note(args.currentNote.id, title, body, false, 0, 0)
        val noteToAddToDeletedTable = DeletedNote(0, title, body, args.currentNote.timeCreated, args.currentNote.timeUpdated, System.currentTimeMillis())

        mViewmodel.addDeletedNote(noteToAddToDeletedTable)
        mViewmodel.deleteNote(noteToDeleteFromNoteTable)
    }

    private fun navigateToListFragment() {
        Navigation.findNavController(binding.root).navigate(R.id.action_noteDetailsFragment_to_noteListFragment)
    }
}