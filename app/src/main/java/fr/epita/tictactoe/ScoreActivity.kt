package fr.epita.tictactoe

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_score.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class ScoreActivity : AppCompatActivity() {

    private val service : PlayerScoreWSInterface = Retrofit.Builder()
        .baseUrl("http://www.onzeweb.net/api/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build().create(PlayerScoreWSInterface::class.java)

    private val data : MutableList<PlayerScore> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        data.add(PlayerScore(intent.getStringExtra("name"),
                             intent.getStringExtra("result").toInt(),
                             Date(intent.getStringExtra("date"))
        ))

        val callback : Callback<List<PlayerScore>> = object : Callback<List<PlayerScore>> {
            override fun onFailure(call: Call<List<PlayerScore>>, t: Throwable) {
                Toast.makeText(this@ScoreActivity, "Not working: " + t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<PlayerScore>>, response: Response<List<PlayerScore>>) {
                if (response.code() == 200) {

                    val scores = response.body()!!

                    player_score_lv.adapter = PlayerScoreAdapter(scores)

                }
            }
        }

        service.getScores().enqueue(callback)
    }
}
