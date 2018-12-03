package fr.epita.tictactoe

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_start_btn.setOnClickListener {
            val explicitIntent = Intent(this@MainActivity, GameActivity::class.java)
            explicitIntent.putExtra("name", main_name_input.text.toString())
            startActivity(explicitIntent)
        }
    }
}
