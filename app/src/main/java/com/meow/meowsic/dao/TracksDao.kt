package com.meow.meowsic.dao

import android.content.Context
import android.util.Log
import com.android.volley.VolleyError
import com.meow.meowsic.models.Playlists
import com.meow.meowsic.models.Songs
import com.meow.meowsic.utilities.Constants
import com.meow.meowsic.utilities.Utilities
import com.meow.meowsic.volley.RequestCallback
import com.meow.meowsic.volley.VolleyRequest
import org.json.JSONException
import org.json.JSONObject

class TracksDao(val context: Context?, requestCallback: RequestCallback) : VolleyRequest(context) {

    private val Utilities = Utilities()
    private val Constants = Constants()
    private var requestCallback: RequestCallback

    init {
        this.requestCallback = requestCallback
    }

    fun getTrackFromId(songId: String?) {
        val url = Utilities.getApiUrlTrackId(songId.toString())
        apiCallObject(
            url,
            object : DaoCallback {
                override fun response(response: Any?) {
                    val jsonObject = response as JSONObject
                    val songs = Songs(jsonObject)
                    requestCallback.onRequestSuccessful(
                        songs,
                        Constants.SEARCH_SONG_WITH_ID,
                        true
                    )
                }

                override fun stringResponse(response: String?) {

                }
                override fun errorResponse(error: VolleyError?) {
                    requestCallback.onRequestSuccessful(
                        null,
                        Constants.SEARCH_SONG_WITH_ID,
                        false
                    )
                }
            },
            Constants.METHOD_GET,
            null
        )
    }

    fun getTrackFromQuery(q: String) {
        val query = Utilities.encodeKeyword(q)
        val url = Utilities.getApiUrlTracksQuery(query)
        apiCallObject(
            url,
            object : DaoCallback {
                override fun response(response: Any?) {
                    val jsonObject = response as JSONObject
                    val songs: ArrayList<Songs> = ArrayList()
                    try {
                        val jsonArray = jsonObject.getJSONObject("tracks").getJSONArray("items")
                        for (i in 0 until jsonArray.length()) {
                            val song = Songs(jsonArray.getJSONObject(i))
                            songs.add(song)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Log.d("hehe", e.toString())
                    }
                    requestCallback.onRequestSuccessful(
                        songs,
                        Constants.SEARCH_SONGS_WITH_QUERY,
                        true
                    )
                }

                override fun stringResponse(response: String?) {

                }

                override fun errorResponse(error: VolleyError?) {
                    requestCallback.onRequestSuccessful(
                        null,
                        Constants.SEARCH_SONGS_WITH_QUERY,
                        false
                    )
                }
            },
            Constants.METHOD_GET,
            null
        )
    }
}