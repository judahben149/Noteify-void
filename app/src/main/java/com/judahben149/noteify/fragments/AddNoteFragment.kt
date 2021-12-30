package com.judahben149.noteify.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.judahben149.noteify.R
import com.judahben149.noteify.databinding.FragmentAddNoteBinding
import com.judahben149.noteify.hideKeyboard
import com.judahben149.noteify.model.Note
import com.judahben149.noteify.viewmodel.NoteViewModel

class AddNoteFragment : Fragment() {

    private lateinit var binding: FragmentAddNoteBinding
    private lateinit var mViewmodel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        mViewmodel = ViewModelProvider(this).get(NoteViewModel::class.java)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnCancelAddNoteScreen.setOnClickListener {
            hideKeyboard()
            Navigation.findNavController(binding.root).navigate(R.id.action_addNoteFragment_to_noteListFragment)
        }

        binding.btnSaveNoteAddNoteScreen.setOnClickListener {
            hideKeyboard()
            insertNoteToDatabase()
        }

        binding.btnAddToFavoritesAddNoteScreen.setOnClickListener {
            addNoteToFavoritesDatabase()
            hideKeyboard()
            Snackbar.make(binding.root, "Note added to favorites", Snackbar.LENGTH_SHORT).show()
        }

        super.onViewCreated(view, savedInstanceState)
    }



    private fun insertNoteToDatabase() {
        val noteTitle = binding.etNoteTitleAddNoteScreen.text.toString()
        val noteBody = binding.etNoteBodyAddNoteScreen.text.toString()

        val note = Note(0, noteTitle, noteBody)
        mViewmodel.addNote(note)

        Navigation.findNavController(binding.root).navigate(R.id.action_addNoteFragment_to_noteListFragment)
    }

    private fun addNoteToFavoritesDatabase() {
        val noteTitle = binding.etNoteTitleAddNoteScreen.text.toString()
        val noteBody = binding.etNoteBodyAddNoteScreen.text.toString()


//        mViewmodel.addFavoriteNote(note)

        Snackbar.make(binding.root, "Added to favorites", Snackbar.LENGTH_SHORT).show()
    }
}