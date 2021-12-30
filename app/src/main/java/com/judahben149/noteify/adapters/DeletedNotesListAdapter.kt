package com.judahben149.noteify.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.judahben149.noteify.databinding.FragmentNoteItemBinding
import com.judahben149.noteify.model.DeletedNote
import com.judahben149.noteify.model.Note

class DeletedNotesListAdapter() : RecyclerView.Adapter<DeletedNotesListAdapter.DeletedNotesListViewHolder>() {

    var deletedNotes = emptyList<DeletedNote>()

    inner class DeletedNotesListViewHolder(private val binding: FragmentNoteItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(position: Int) {
            val currentNote = deletedNotes[position]

            binding.tvNoteTitle.text = currentNote.noteTitle
            binding.tvNoteDescription.text = currentNote.noteBody

            binding.noteItem.setOnClickListener {
                Snackbar.make(binding.root, "You have clicked me", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeletedNotesListViewHolder {
        val binding = FragmentNoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeletedNotesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeletedNotesListViewHolder, position: Int) {
        holder.bindItem(position)
    }

    override fun getItemCount() = deletedNotes.size

    fun setData(note: List<DeletedNote>) {
        this.deletedNotes = note
        notifyDataSetChanged()
    }
}