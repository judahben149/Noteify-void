package com.judahben149.noteify.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.judahben149.noteify.R
import com.judahben149.noteify.databinding.FragmentNoteDetailsBinding
import com.judahben149.noteify.model.FavoriteNote
import com.judahben149.noteify.model.Note
import com.judahben149.noteify.viewmodel.NoteViewModel

class NoteDetailsFragment: Fragment() {

    private lateinit var binding: FragmentNoteDetailsBinding
    private val args by navArgs<NoteDetailsFragmentArgs>()
    private lateinit var mViewmodel: NoteViewModel

    var isNoteFavorite: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteDetailsBinding.inflate(inflater, container, false)

        mViewmodel = ViewModelProvider(this).get(NoteViewModel::class.java)

        binding.noteTitleNoteDetailsScreen.setText(args.currentNote.noteTitle)
        binding.noteBodyNoteDetailsScreen.setText(args.currentNote.noteBody)
        isNoteFavorite = args.currentNote.favoriteStatus
        setUpFavoriteButton(isNoteFavorite)

        return binding.root
    }

    private fun setUpFavoriteButton(isNoteFavorite: Boolean) {
        if (isNoteFavorite) {
            binding.btnAddToFavoritesNoteDetailsScreen.setText("Unfavorite")
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnCancelNoteDetailsScreen.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_noteDetailsFragment_to_noteListFragment)
        }

        binding.btnSaveNoteNoteDetailsScreen.setOnClickListener {
            updateNoteInDatabase()
        }

        binding.btnAddToFavoritesNoteDetailsScreen.setOnClickListener {
            if (isNoteFavorite) {
                isNoteFavorite = false

                val noteTitle = binding.noteTitleNoteDetailsScreen.text.toString()
                val noteBody = binding.noteBodyNoteDetailsScreen.text.toString()
                val favoriteStatus = isNoteFavorite
                val note = Note(args.currentNote.id, noteTitle, noteBody, favoriteStatus)

                mViewmodel.updateNote(note)
                Snackbar.make(binding.root, "Note removed from favorites", Snackbar.LENGTH_SHORT).show()

            } else {
                isNoteFavorite = true

                val noteTitle = binding.noteTitleNoteDetailsScreen.text.toString()
                val noteBody = binding.noteBodyNoteDetailsScreen.text.toString()
                val favoriteStatus = isNoteFavorite
                val note = Note(args.currentNote.id, noteTitle, noteBody, favoriteStatus)

                mViewmodel.updateNote(note)

                Snackbar.make(binding.root, "Note added to favorites", Snackbar.LENGTH_SHORT).show()
            }
            addNoteToFavoritesDatabase()

        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun updateNoteInDatabase() {
        val noteTitle = binding.noteTitleNoteDetailsScreen.text.toString()
        val noteBody = binding.noteBodyNoteDetailsScreen.text.toString()

        val note = Note(args.currentNote.id, noteTitle, noteBody)
        mViewmodel.updateNote(note)

        Navigation.findNavController(binding.root).navigate(R.id.action_noteDetailsFragment_to_noteListFragment)
    }

    private fun addNoteToFavoritesDatabase() {
        val noteTitle = binding.noteTitleNoteDetailsScreen.text.toString()
        val noteBody = binding.noteBodyNoteDetailsScreen.text.toString()

        val note = FavoriteNote(args.currentNote.id, noteTitle, noteBody)
//        mViewmodel.addFavoriteNote(note)

        Navigation.findNavController(binding.root).navigate(R.id.action_noteDetailsFragment_to_noteListFragment)
    }
}