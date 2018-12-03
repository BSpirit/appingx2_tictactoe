package fr.epita.tictactoe

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.random.Random.Default.nextBoolean

class GameActivity : AppCompatActivity() {

    var grid = arrayOfNulls<String>(9)
    var gridSize = 0
    var isYourTurn = nextBoolean()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        game_guest_name.text = "X (GUEST)"
        game_player_name.text = "O (" + intent.getStringExtra("name") + ")"

        game_btn0.setOnClickListener(SquareListener(0))
        game_btn1.setOnClickListener(SquareListener(1))
        game_btn2.setOnClickListener(SquareListener(2))
        game_btn3.setOnClickListener(SquareListener(3))
        game_btn4.setOnClickListener(SquareListener(4))
        game_btn5.setOnClickListener(SquareListener(5))
        game_btn6.setOnClickListener(SquareListener(6))
        game_btn7.setOnClickListener(SquareListener(7))
        game_btn8.setOnClickListener(SquareListener(8))

        update()
    }

    private fun update() {
        if (isYourTurn) {
            game_player_turn.visibility = View.VISIBLE
            game_player_turn_guest.visibility = View.INVISIBLE
        } else {
            game_player_turn.visibility = View.INVISIBLE
            game_player_turn_guest.visibility = View.VISIBLE
        }
    }

    private fun getVal() : String {
        if (isYourTurn)
            return "O"

        return "X"
    }

    private fun checkWinner() : String? {
        val lines = arrayOf(
            Triple(0, 1, 2),
            Triple(3, 4, 5),
            Triple(6, 7, 8),
            Triple(0, 3, 6),
            Triple(1, 4, 7),
            Triple(2, 5, 8),
            Triple(0, 4, 8),
            Triple(2, 4, 6))

        for (line in lines) {
            val (a, b, c) = line
            if (grid[a] != null && grid[a] == grid[b] && grid[a] == grid[c]) {
                return grid[a]
            }
        }
        return null
    }

    private fun createIntent(name : String, result : Int, date : Date) : Intent {
        val explicitIntent = Intent(this@GameActivity, ScoreActivity::class.java)
        explicitIntent.putExtra("name", name)
        explicitIntent.putExtra("result", result.toString())
        explicitIntent.putExtra("date", date.toString())

        return explicitIntent
    }

    inner class SquareListener(val id : Int) : View.OnClickListener {

        override fun onClick(v: View?) {
            val button : Button = v as Button
            if (button.text == "") {
                val input = getVal()
                grid[id] = input
                button.text = input
                gridSize += 1

                val winner = checkWinner()
                if (winner != null && isYourTurn) {
                    Toast.makeText(this@GameActivity, "WIN", Toast.LENGTH_SHORT).show()
                    val explicitIntent = createIntent(game_player_name.text.toString(), 3, Date())
                    startActivity(explicitIntent)
                }

                else if (winner != null && !isYourTurn) {
                    Toast.makeText(this@GameActivity, "LOOSE", Toast.LENGTH_SHORT).show()
                    val explicitIntent = createIntent(game_player_name.text.toString(), 1, Date())
                    startActivity(explicitIntent)
                }

                else if (gridSize == 9) {
                    Toast.makeText(this@GameActivity, "DRAW", Toast.LENGTH_SHORT).show()
                    val explicitIntent = createIntent(game_player_name.text.toString(), 2, Date())
                    startActivity(explicitIntent)

                }


                isYourTurn = !isYourTurn
                update()
            }
        }
    }
}
