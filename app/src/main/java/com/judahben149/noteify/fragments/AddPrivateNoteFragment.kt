package com.judahben149.noteify.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.judahben149.noteify.R
import com.judahben149.noteify.databinding.FragmentAddPrivateNoteBinding
import com.judahben149.noteify.hideKeyboard
import com.judahben149.noteify.model.PrivateNote
import com.judahben149.noteify.viewmodel.PrivateNoteViewModel


class AddPrivateNoteFragment : Fragment() {

    private var _binding: FragmentAddPrivateNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var mViewmodel: PrivateNoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddPrivateNoteBinding.inflate(inflater, container, false)
        mViewmodel = ViewModelProvider(this).get(PrivateNoteViewModel::class.java)
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


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


    private fun insertNoteToDatabase() {
        val noteTitle = binding.etNoteTitleAddPrivateNoteScreen.text.toString()
        val noteBody = binding.etNoteBodyAddPrivateNoteScreen.text.toString()
        val timeCreated = System.currentTimeMillis()

        val note = PrivateNote(0, noteTitle, noteBody, timeCreated, timeCreated)
        mViewmodel.addPrivateNote(note)
    }

    private fun navigateToListFragment() {
        Navigation.findNavController(binding.root).navigate(R.id.action_addPrivateNoteFragment_to_privateNotesFragment)
    }
}