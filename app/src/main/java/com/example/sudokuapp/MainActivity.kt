package com.example.sudokuapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity(), View.OnClickListener{
    private var userInputBoard = Array(9) { IntArray(9){0} }
    private var selectedPosition=-1
    private lateinit var gameBoardAdapter: GameBoardAdapter
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycle)
        recyclerView.layoutManager = GridLayoutManager(applicationContext, 9)
        gameBoardAdapter = GameBoardAdapter()
        recyclerView.adapter = gameBoardAdapter
        gameBoardAdapter.setDataList(userInputBoard)
        gameBoardAdapter.setOnItemClickListener(object : GameBoardAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                selectedPosition=position
            }
        })
        val buttonOne = findViewById<AppCompatButton>(R.id.one)
        val buttonTwo = findViewById<AppCompatButton>(R.id.two)
        val buttonThree = findViewById<AppCompatButton>(R.id.three)
        val buttonFour = findViewById<AppCompatButton>(R.id.four)
        val buttonFive = findViewById<AppCompatButton>(R.id.five)
        val buttonSix = findViewById<AppCompatButton>(R.id.six)
        val buttonSeven = findViewById<AppCompatButton>(R.id.seven)
        val buttonEight = findViewById<AppCompatButton>(R.id.eight)
        val buttonNine = findViewById<AppCompatButton>(R.id.nine)
        val buttonClear = findViewById<AppCompatButton>(R.id.button3)
        val buttonSolve = findViewById<AppCompatButton>(R.id.button2)
        buttonOne.setOnClickListener(this)
        buttonTwo.setOnClickListener(this)
        buttonThree.setOnClickListener(this)
        buttonFour.setOnClickListener(this)
        buttonFive.setOnClickListener(this)
        buttonSix.setOnClickListener(this)
        buttonSeven.setOnClickListener(this)
        buttonEight.setOnClickListener(this)
        buttonNine.setOnClickListener(this)
        buttonClear.setOnClickListener(this)
        buttonSolve.setOnClickListener(this)
    }
    private fun isValidSudoku(board: Array<IntArray>): Boolean {
        for (i in board.indices) {
            val rowSet = mutableSetOf<Int>()
            for (j in board.indices) {
                val value = board[i][j]
                if (value != 0 && !rowSet.add(value)) {
                    return false
                }
            }
        }

        for (j in board.indices) {
            val colSet = mutableSetOf<Int>()
            for (i in board.indices) {
                val value = board[i][j]
                if (value != 0 && !colSet.add(value)) {
                    return false
                }
            }
        }

        for (boxRow in 0..6 step 3) {
            for (boxCol in 0..6 step 3) {
                val boxSet = mutableSetOf<Int>()
                for (i in boxRow until boxRow + 3) {
                    for (j in boxCol until boxCol + 3) {
                        val value = board[i][j]
                        if (value != 0 && !boxSet.add(value)) {
                            return false
                        }
                    }
                }
            }
        }

        return true
    }

    private fun solveSudoku(board: Array<IntArray>): Boolean {
        if (!isValidSudoku(board)) {
            return false
        }
        val emptyCell = findEmptyCell(board) ?: return true
        for (value in 1..9) {
            if (isValidMove(board, emptyCell, value)) {
                board[emptyCell.first][emptyCell.second] = value
                if (solveSudoku(board)) {
                    return true
                }
                board[emptyCell.first][emptyCell.second] = 0
            }
        }

        return false
    }

    private fun findEmptyCell(board: Array<IntArray>): Pair<Int, Int>? {
        for (i in board.indices) {
            for (j in board.indices) {
                if (board[i][j] == 0) {
                    return Pair(i, j)
                }
            }
        }
        return null
    }

    private fun isValidMove(board: Array<IntArray>, cell: Pair<Int, Int>, value: Int): Boolean {
        val row = cell.first
        val col = cell.second

        for (i in board.indices) {
            if (board[row][i] == value || board[i][col] == value) {
                return false
            }
        }

        val boxRow = row - row % 3
        val boxCol = col - col % 3
        for (i in boxRow until boxRow + 3) {
            for (j in boxCol until boxCol + 3) {
                if (board[i][j] == value) {
                    return false
                }
            }
        }

        // Valid move
        return true
    }

    private fun updateCellWithNumber(number: Int) {
        if (selectedPosition != -1) {
            val row = selectedPosition / 9
            val col = selectedPosition % 9
            gameBoardAdapter.updateBoard(row, col, number)
        } else {
            Toast.makeText(this, "Please select a cell first", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.one -> {
                updateCellWithNumber(1)
            }
            R.id.two -> {
                updateCellWithNumber(2)
            }
            R.id.three -> {
                updateCellWithNumber(3)
            }
            R.id.four -> {
                updateCellWithNumber(4)
            }
            R.id.five -> {
                updateCellWithNumber(5)
            }
            R.id.six -> {
                updateCellWithNumber(6)
            }
            R.id.seven -> {
                updateCellWithNumber(7)
            }
            R.id.eight -> {
                updateCellWithNumber(8)
            }
            R.id.nine -> {
                updateCellWithNumber(9)
            }
            R.id.button3 -> {
                userInputBoard = Array(9) { IntArray(9) }
                recyclerView.adapter = gameBoardAdapter
                gameBoardAdapter.setDataList(userInputBoard)
                selectedPosition=-1
            }
            R.id.button2 -> {
                if (solveSudoku(userInputBoard)) {
                    recyclerView.adapter = gameBoardAdapter
                    gameBoardAdapter.setDataList(userInputBoard)
                } else {
                    Toast.makeText(
                        applicationContext,
                        "No solution",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}