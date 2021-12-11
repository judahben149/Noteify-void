package com.judahben149.noteify.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.judahben149.noteify.databinding.FragmentNoteItemBinding
import com.judahben149.noteify.fragments.NoteListFragmentDirections
import com.judahben149.noteify.model.Note

class NoteListRecyclerViewAdapter(): RecyclerView.Adapter<NoteListRecyclerViewAdapter.NoteListRecyclerViewViewHolder>() {

    var noteList = emptyList<Note>()
    val isSelectMode = false
    val selectedItems = arrayListOf<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListRecyclerViewViewHolder {
        val binding = FragmentNoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteListRecyclerViewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteListRecyclerViewViewHolder, position: Int) {
        holder.bindItem(position)
    }

    override fun getItemCount() = noteList.size


    inner class NoteListRecyclerViewViewHolder(private val binding: FragmentNoteItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItem(position: Int) {
            val currentNote = noteList[position]

            binding.tvNoteTitle.text = currentNote.noteTitle
            binding.tvNoteDescription.text = currentNote.noteBody

            binding.noteItem.setOnClickListener {
                val action = NoteListFragmentDirections.actionNoteListFragmentToNoteDetailsFragment(currentNote)
                Navigation.findNavController(binding.root).navigate(action)
            }

            binding.noteItem.setOnLongClickListener()
        }
    }

    fun setData(note: List<Note>) {
        this.noteList = note
        notifyDataSetChanged()
    }


    fun passItemPositionDuringSwipe(position: Int): Note {
        return noteList[position]
    }
}