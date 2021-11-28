package com.judahben149.noteify.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.judahben149.noteify.R
import com.judahben149.noteify.databinding.FragmentNoteDetailsBinding
import com.judahben149.noteify.model.Note
import com.judahben149.noteify.viewmodel.NoteViewModel

class NoteDetailsFragment: Fragment() {

    private lateinit var binding: FragmentNoteDetailsBinding
    private val args by navArgs<NoteDetailsFragmentArgs>()
    private lateinit var mViewmodel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteDetailsBinding.inflate(inflater, container, false)

        mViewmodel = ViewModelProvider(this).get(NoteViewModel::class.java)

        binding.noteTitleNoteDetailsScreen.setText(args.currentNote.noteTitle)
        binding.noteBodyNoteDetailsScreen.setText(args.currentNote.noteBody)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnCancelNoteDetailsScreen.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_noteDetailsFragment_to_noteListFragment)
        }

        binding.btnSaveNoteNoteDetailsScreen.setOnClickListener {
            updateNoteInDatabase()
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
}