package fr.epita.tictactoe

import retrofit2.Call
import retrofit2.http.GET

interface PlayerScoreWSInterface {

    @GET("scores.json")
    fun getScores() : Call<List<PlayerScore>>
}