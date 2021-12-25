package com.judahben149.noteify.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.judahben149.noteify.databinding.FragmentNoteItemBinding
import com.judahben149.noteify.fragments.NoteListFragmentDirections
import com.judahben149.noteify.model.Note
import kotlinx.android.synthetic.main.fragment_note_item.view.*

class NoteListRecyclerViewAdapter(): RecyclerView.Adapter<NoteListRecyclerViewAdapter.NoteListRecyclerViewViewHolder>() {

    var noteList = emptyList<Note>()
    val isSelectMode = false
    val selectedItems = arrayListOf<Note>()
    private var tracker: SelectionTracker<Long>? = null


    init {
        setHasStableIds(true)
    }
    override fun getItemId(position: Int): Long = position.toLong()


    inner class NoteListRecyclerViewViewHolder(private val binding: FragmentNoteItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItem(position: Int) {
            val currentNote = noteList[position]

            binding.tvNoteTitle.text = currentNote.noteTitle
            binding.tvNoteDescription.text = currentNote.noteBody

            binding.noteItem.setOnClickListener {
                val action = NoteListFragmentDirections.actionNoteListFragmentToNoteDetailsFragment(currentNote)
                Navigation.findNavController(binding.root).navigate(action)
            }
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object: ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = bindingAdapterPosition

                override fun getSelectionKey(): Long? = itemId

            }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListRecyclerViewViewHolder {
        val binding = FragmentNoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteListRecyclerViewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteListRecyclerViewViewHolder, position: Int) {
        holder.bindItem(position)

        val noteItem = holder.itemView.noteItem

        //this changes the color of the note when selected; for visual feedback
        if (tracker!!.isSelected(position.toLong())) {
            noteItem.background = ColorDrawable(
                Color.parseColor("#B2BEB5")
            )
        } else {
            noteItem.background = ColorDrawable(Color.WHITE)
        }
    }

    override fun getItemCount() = noteList.size


    fun setData(note: List<Note>) {
        this.noteList = note
        notifyDataSetChanged()
    }


    fun passItemPositionDuringSwipe(position: Int): Note {
        return noteList[position]
    }

    fun setTracker(tracker: SelectionTracker<Long>?) {
        this.tracker = tracker
    }
}