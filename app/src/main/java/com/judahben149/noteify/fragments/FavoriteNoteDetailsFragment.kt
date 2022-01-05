package com.judahben149.noteify.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.judahben149.noteify.R
import com.judahben149.noteify.databinding.FragmentFavoriteNoteDetailsBinding
import com.judahben149.noteify.hideKeyboard
import com.judahben149.noteify.model.Note
import com.judahben149.noteify.viewmodel.NoteViewModel
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

class FavoriteNoteDetailsFragment : Fragment() {

    private var _binding: FragmentFavoriteNoteDetailsBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<FavoriteNoteDetailsFragmentArgs>()
    private lateinit var mViewmodel: NoteViewModel

    private var isNoteFavorite: Boolean = false
    var timeCreated: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteNoteDetailsBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mViewmodel = ViewModelProvider(this).get(NoteViewModel::class.java)
        timeCreated = PrettyTime().format(Date(args.favoriteNoteDetails.timeCreated))

        binding.noteTitleFavoriteNoteDetailsScreen.setText(args.favoriteNoteDetails.noteTitle)
        binding.noteBodyFavoriteNoteDetailsScreen.setText(args.favoriteNoteDetails.noteBody)
        binding.dateCreatedFavoriteNoteDetailsScreen.text = "Created: " + timeCreated

        isNoteFavorite = args.favoriteNoteDetails.favoriteStatus

        binding.btnCancelFavoriteNoteDetailsScreen.setOnClickListener {
            hideKeyboard()
            navigateToListFragment()
        }

        binding.btnSaveNoteFavoriteNoteDetailsScreen.setOnClickListener {
            updateNoteInDatabase(isNoteFavorite, false)
            hideKeyboard()
            navigateToListFragment()
        }

        super.onViewCreated(view, savedInstanceState)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite_note_details_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.removeFromFavorites) {
            hideKeyboard()
            isNoteFavorite = false
            updateNoteInDatabase(isNoteFavorite, false)
            navigateToListFragment()
            Snackbar.make(binding.root, "Note removed from favorites", Snackbar.LENGTH_SHORT).show()
        } else if (item.itemId == R.id.deleteFavoriteNote){
            hideKeyboard()
            updateNoteInDatabase(isNoteFavorite, true)
            navigateToListFragment()
            Snackbar.make(binding.root, "Note sent to trash", Snackbar.LENGTH_SHORT).show()
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

    private fun updateNoteInDatabase(favoriteStatus: Boolean, isNoteDeleted: Boolean) {
        val noteTitle = binding.noteTitleFavoriteNoteDetailsScreen.text.toString()
        val noteBody = binding.noteTitleFavoriteNoteDetailsScreen.text.toString()
        val timeUpdated = System.currentTimeMillis()

        val note = Note(
            args.favoriteNoteDetails.id,
            noteTitle,
            noteBody,
            favoriteStatus = isNoteFavorite,
            timeCreated = args.favoriteNoteDetails.timeCreated,
            timeUpdated = timeUpdated,
            deletedStatus = isNoteDeleted,
            timeDeleted = timeUpdated
        )
        mViewmodel.updateNote(note)
    }

    private fun navigateToListFragment() {
        Navigation.findNavController(binding.root).navigate(R.id.action_favoriteNoteDetailsFragment_to_favoritesFragment2)
    }
}