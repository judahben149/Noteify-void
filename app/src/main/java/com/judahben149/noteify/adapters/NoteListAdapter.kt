package com.judahben149.noteify.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.judahben149.noteify.databinding.FragmentNoteItemBinding
import com.judahben149.noteify.fragments.NoteListFragmentDirections
import com.judahben149.noteify.model.Note
import kotlinx.android.synthetic.main.fragment_note_item.view.*
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

class NoteListAdapter(): RecyclerView.Adapter<NoteListAdapter.NoteListRecyclerViewViewHolder>() {

    var noteList = emptyList<Note>()


    inner class NoteListRecyclerViewViewHolder(private val binding: FragmentNoteItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItem(position: Int) {
            val currentNote = noteList[position]

            val prettyTime: String = PrettyTime().format(Date(currentNote.timeUpdated))

            binding.tvNoteTitle.text = currentNote.noteTitle
            binding.tvNoteDescription.text = currentNote.noteBody
            binding.tvNoteDate.text = prettyTime

            binding.noteItem.setOnClickListener {
                val action = NoteListFragmentDirections.actionNoteListFragmentToNoteDetailsFragment(currentNote)
                Navigation.findNavController(binding.root).navigate(action)
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListRecyclerViewViewHolder {
        val binding = FragmentNoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteListRecyclerViewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteListRecyclerViewViewHolder, position: Int) {
        holder.bindItem(position)
    }

    override fun getItemCount() = noteList.size


    fun setData(note: List<Note>) {
        this.noteList = note
        notifyDataSetChanged()
    }

    fun tellItemCount(note: List<Note>): Int {
        return note.size
    }

}