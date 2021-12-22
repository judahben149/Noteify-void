package com.judahben149.noteify

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.judahben149.noteify.adapters.NoteListRecyclerViewAdapter

class NoteLookup(private val rv: RecyclerView): ItemDetailsLookup<Long> () {

    override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
        val view = rv.findChildViewUnder(event.x, event.y)

        if (view != null) {
            return (rv.getChildViewHolder(view) as NoteListRecyclerViewAdapter.NoteListRecyclerViewViewHolder).getItemDetails()
        }
        return null
    }
}