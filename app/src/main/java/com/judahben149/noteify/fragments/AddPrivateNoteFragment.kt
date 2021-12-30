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
import com.judahben149.noteify.databinding.FragmentAddPrivateNoteBinding
import com.judahben149.noteify.hideKeyboard
import com.judahben149.noteify.model.Note
import com.judahben149.noteify.model.PrivateNote
import com.judahben149.noteify.viewmodel.NoteViewModel


class AddPrivateNoteFragment : Fragment() {

    private lateinit var binding: FragmentAddPrivateNoteBinding
    private lateinit var mViewmodel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPrivateNoteBinding.inflate(inflater, container, false)
        mViewmodel = ViewModelProvider(this).get(NoteViewModel::class.java)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnCancelAddPrivateNoteScreen.setOnClickListener {
            hideKeyboard()
            navigateToListFragment()
        }

        binding.btnSaveNoteAddPrivateNoteScreen.setOnClickListener {
            insertNoteToDatabase()
            hideKeyboard()
            navigateToListFragment()
        }

        super.onViewCreated(view, savedInstanceState)
    }




    private fun insertNoteToDatabase() {
        val noteTitle = binding.etNoteTitleAddPrivateNoteScreen.text.toString()
        val noteBody = binding.etNoteBodyAddPrivateNoteScreen.text.toString()

        val note = PrivateNote(0, noteTitle, noteBody)
        mViewmodel.addPrivateNote(note)
    }

    private fun navigateToListFragment() {
        Navigation.findNavController(binding.root).navigate(R.id.action_addPrivateNoteFragment_to_privateNotesFragment)
    }
}