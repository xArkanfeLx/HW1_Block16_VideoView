package com.example.videoview

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.videoview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val videoList: MutableList<Uri> = mutableListOf()
    private var nowVideo = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        videoList.add(Uri.parse("android.resource://$packageName/${R.raw.video_1}"))
        videoList.add(Uri.parse("android.resource://$packageName/${R.raw.video_2}"))
        val mediaController = MediaController(this)
        mediaController.setAnchorView(mediaController)

        binding.videoView.setMediaController(mediaController)

        binding.playBTN.setOnClickListener {
            if (binding.videoView.duration == -1) playVideo()
            else  binding.videoView.start()
        }
        binding.pauseBTN.setOnClickListener {
            binding.videoView.pause()
        }
        binding.stopBTN.setOnClickListener {
            binding.videoView.stopPlayback()
        }
        binding.prevBTN.setOnClickListener {
            binding.videoView.stopPlayback()
            changeNowVideo(--nowVideo)
        }
        binding.nextBTN.setOnClickListener {
            binding.videoView.stopPlayback()
            changeNowVideo(++nowVideo)
        }
    }

    fun changeNowVideo(nv: Int) {
        nowVideo = nv
        if (nowVideo < 0) nowVideo = videoList.size - 1
        if (nowVideo >= videoList.size) nowVideo = 0
        playVideo()
    }

    fun playVideo() {
        binding.videoView.setVideoURI(videoList[nowVideo])
        binding.videoView.requestFocus()
        binding.videoView.start()
    }
}