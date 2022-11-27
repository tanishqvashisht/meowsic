package com.meow.meowsic.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.meow.meowsic.R
import com.meow.meowsic.adapters.HomeAdapter
import com.meow.meowsic.dao.PlaylistDao
import com.meow.meowsic.databinding.FragmentHomeBinding
import com.meow.meowsic.models.Songs
import com.meow.meowsic.volley.RequestCallback

class HomeFragment : Fragment(), RequestCallback {

    private lateinit var homeAdapter: HomeAdapter
    private lateinit var playlistDao: PlaylistDao
    private lateinit var songs: ArrayList<Songs>
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        playlistDao = PlaylistDao(context, this)
        playlistDao.getTracksFromPlaylistId("37i9dQZF1DXcBWIGoYBM5M")
        songs = ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.bind(view)
        return view
    }

    override fun onListRequestSuccessful(list: ArrayList<Songs>?, check: Int, status: Boolean) {
        if (list != null) songs.addAll(list)
        homeAdapter = HomeAdapter(context, songs)
        binding.homerv.layoutManager = GridLayoutManager(context, 1)
        binding.homerv.setHasFixedSize(true)
        binding.homerv.adapter = homeAdapter
    }

    override fun onObjectRequestSuccessful(`object`: Any?, check: Int, status: Boolean) {

    }

}