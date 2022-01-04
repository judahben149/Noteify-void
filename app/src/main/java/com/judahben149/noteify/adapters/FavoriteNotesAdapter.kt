package com.judahben149.noteify.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.judahben149.noteify.databinding.FragmentNoteItemBinding
import com.judahben149.noteify.fragments.FavoriteNoteDetailsFragmentDirections
import com.judahben149.noteify.fragments.FavoriteNotesListFragmentDirections
import com.judahben149.noteify.model.Note
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

class FavoriteNotesAdapter(): RecyclerView.Adapter<FavoriteNotesAdapter.FavoriteNotesViewHolder>() {

    var favNoteList = emptyList<Note>()

    inner class FavoriteNotesViewHolder(private val binding: FragmentNoteItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItem(position: Int) {
            val currentNote = favNoteList[position]
            val prettyTime: String = PrettyTime().format(Date(currentNote.timeUpdated))

            binding.tvNoteTitle.text = currentNote.noteTitle
            binding.tvNoteDescription.text = currentNote.noteBody
            binding.tvNoteDate.text = prettyTime

            binding.noteItem.setOnClickListener {
//                Snackbar.make(binding.root, "You have clicked me", Snackbar.LENGTH_SHORT).show()
                val action = FavoriteNotesListFragmentDirections.actionFavoritesFragmentToFavoriteNoteDetailsFragment(currentNote)
                Navigation.findNavController(binding.root).navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteNotesViewHolder {
        val binding = FragmentNoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteNotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteNotesViewHolder, position: Int) {
        holder.bindItem(position)
    }

    override fun getItemCount() = favNoteList.size


    fun setData(note: List<Note>) {
        this.favNoteList = note
        notifyDataSetChanged()
    }
}