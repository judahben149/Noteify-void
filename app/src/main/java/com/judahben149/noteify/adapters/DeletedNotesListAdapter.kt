package com.judahben149.noteify.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.judahben149.noteify.databinding.FragmentNoteItemBinding
import com.judahben149.noteify.fragments.DeletedNotesListFragmentDirections
import com.judahben149.noteify.model.Note
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

class DeletedNotesListAdapter() : RecyclerView.Adapter<DeletedNotesListAdapter.DeletedNotesListViewHolder>() {

    var deletedNotes = emptyList<Note>()

    inner class DeletedNotesListViewHolder(private val binding: FragmentNoteItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(position: Int) {
            val currentNote = deletedNotes[position]
            val timeDeleted: String = PrettyTime().format(Date(currentNote.timeDeleted))

            binding.tvNoteTitle.text = currentNote.noteTitle
            binding.tvNoteDescription.text = currentNote.noteBody
            binding.tvNoteDate.text = "Deleted: " + timeDeleted

            binding.noteItem.setOnClickListener {
                val action = DeletedNotesListFragmentDirections.actionDeletedNotesListFragmentToDeletedNoteDetailsFragment(currentNote)
                Navigation.findNavController(binding.root).navigate(action)
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

    fun setData(note: List<Note>) {
        this.deletedNotes = note
        notifyDataSetChanged()
    }
}