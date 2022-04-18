package com.example.practice_fitbit

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView

class VideoActivity : AppCompatActivity() {
    // declaring a null variable for VideoView
    var simpleVideoView: VideoView? = null
    private lateinit var skip_btn : Button

    // declaring a null variable for MediaController
    var mediaControls: MediaController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        simpleVideoView = findViewById<View>(R.id.videoviewid) as VideoView

        skip_btn = findViewById(R.id.videobtn)

        if (mediaControls == null) {
            // creating an object of media controller class
            mediaControls = MediaController(this)

            // set the anchor view for the video view
            mediaControls!!.setAnchorView(this.simpleVideoView)

            // set the media controller for video view
            simpleVideoView!!.setMediaController(mediaControls)

            // set the absolute path of the video file which is going to be played
            simpleVideoView!!.setVideoURI(
                Uri.parse("android.resource://"
                    + packageName + "/" + R.raw.videoview1))

            simpleVideoView!!.requestFocus()

            // starting the video
            simpleVideoView!!.start()

            // display a toast message
            // after the video is completed
            simpleVideoView!!.setOnCompletionListener {
                Toast.makeText(applicationContext, "Video completed",
                    Toast.LENGTH_LONG).show()
            }

            // display a toast message if any
            // error occurs while playing the video
            simpleVideoView!!.setOnErrorListener { mp, what, extra ->
                Toast.makeText(applicationContext, "An Error Occured " +
                        "While Playing Video !!!", Toast.LENGTH_LONG).show()
                false
            }

            skip_btn.setOnClickListener {
                startActivity(Intent(this,HomeScreen::class.java))
                finish()
            }



        }
    }
}