package com.judahben149.noteify.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.judahben149.noteify.databinding.FragmentNoteItemBinding
import com.judahben149.noteify.fragments.PrivateNotesListFragmentDirections
import com.judahben149.noteify.model.PrivateNote

class PrivateNotesAdapter() : RecyclerView.Adapter<PrivateNotesAdapter.PrivateNotesViewHolder>() {

    var privateNotes = emptyList<PrivateNote>()

    inner class PrivateNotesViewHolder(private val binding: FragmentNoteItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(position: Int) {
            val currentNote = privateNotes[position]

            binding.tvNoteTitle.text = currentNote.noteTitle
            binding.tvNoteDescription.text = currentNote.noteBody

            binding.noteItem.setOnClickListener {
                Snackbar.make(binding.root, "You have clicked me", Snackbar.LENGTH_SHORT).show()
                val action = PrivateNotesListFragmentDirections.actionPrivateNotesFragmentToPrivateNoteDetailsFragment(currentNote)
                Navigation.findNavController(binding.root).navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrivateNotesViewHolder {
        val binding = FragmentNoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PrivateNotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PrivateNotesViewHolder, position: Int) {
        holder.bindItem(position)
    }

    override fun getItemCount() = privateNotes.size

    fun setData(note: List<PrivateNote>) {
        this.privateNotes = note
        notifyDataSetChanged()
    }
}