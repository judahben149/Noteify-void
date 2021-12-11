package com.judahben149.noteify.adapters

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import android.content.Context

import com.judahben149.noteify.activities.MainActivity

import androidx.core.content.ContextCompat
import com.judahben149.noteify.R

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator




abstract class NoteListAdapterSwipeGestures(context: Context): ItemTouchHelper
    .SimpleCallback(0, ItemTouchHelper.LEFT) { //or ItemTouchHelper.RIGHT) {

    val deleteColor = ContextCompat.getColor(context, R.color.swipeDelete)
    val pinColor = ContextCompat.getColor(context, R.color.swipePin)

    val deleteIcon = R.drawable.ic_delete
    val pinIcon = R.drawable.ic_pin

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        RecyclerViewSwipeDecorator.Builder(
            c,
            recyclerView,
            viewHolder,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
            .addSwipeLeftBackgroundColor(deleteColor)
            .addSwipeRightBackgroundColor(pinColor)
            .addSwipeLeftActionIcon(deleteIcon)
            .addSwipeRightActionIcon(pinIcon)
            .create()
            .decorate()

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}