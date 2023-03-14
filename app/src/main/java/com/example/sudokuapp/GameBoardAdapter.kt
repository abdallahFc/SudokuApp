package com.example.sudokuapp

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class GameBoardAdapter : RecyclerView.Adapter<GameBoardAdapter.ViewHolder>() {

    private var userInputBoard = Array(9) { IntArray(9) }
    private var isCellFilledByUser = Array(9) { BooleanArray(9) }
    private lateinit var onItemClick: OnItemClickListener

    fun setDataList(dataList: Array<IntArray>) {
        userInputBoard = dataList
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        onItemClick = onItemClickListener
    }

    fun updateBoard(row: Int, col: Int, value: Int) {
        userInputBoard[row][col] = value
        isCellFilledByUser[row][col] = true
        notifyItemChanged(row * 9 + col)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_item, parent, false)
        return ViewHolder(view, onItemClick)
    }

    override fun getItemCount(): Int {
        return 81
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val row = position / 9
        val col = position % 9
        val value = userInputBoard[row][col]

        if (value != 0) {
            holder.cell.setTextColor(Color.BLACK)
            holder.cell.setTypeface(null, Typeface.BOLD)
            holder.cell.text = value.toString()
        } else {
            holder.cell.text = ""
            isCellFilledByUser = Array(9) { BooleanArray(9) }
        }

        if (isCellFilledByUser[row][col]) {
            holder.cell.setBackgroundResource(R.drawable.selected_button_border)
            holder.cell.setTextColor(Color.WHITE)
        } else {
            holder.cell.setBackgroundResource(R.drawable.button_border)
        }

        holder.cell.setOnClickListener {
            onItemClick.onItemClick(position)
        }
    }

    class ViewHolder(itemView: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {

        val cell: Button = itemView.findViewById(R.id.info_cell)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}
