package com.rankstudio.app

import android.os.Bundle
import android.net.Uri
import android.graphics.Color
import android.view.Gravity
import android.widget.*
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    private val videos = mutableListOf<Uri>()
    private lateinit var videoList: LinearLayout
    private lateinit var status: TextView

    private val videoPicker =
        registerForActivityResult(
            ActivityResultContracts.GetMultipleContents()
        ) { selected ->

            videos.clear()
            videos.addAll(selected)
            updateVideos()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 35, 24, 24)
            setBackgroundColor(Color.rgb(16, 16, 20))
        }

        val title = TextView(this).apply {
            text = "RANKSTUDIO"
            textSize = 28f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }

        val subtitle = TextView(this).apply {
            text = "Create your ranking videos"
            textSize = 14f
            setTextColor(Color.LTGRAY)
            gravity = Gravity.CENTER
        }

        val projectTitle = EditText(this).apply {
            hint = "Enter your video title"
            setTextColor(Color.WHITE)
            setHintTextColor(Color.GRAY)
            setSingleLine(true)
            setPadding(16, 12, 16, 12)
            setBackgroundColor(Color.rgb(35, 35, 43))
        }

        val addButton = Button(this).apply {
            text = "+ Add Videos"
            setOnClickListener {
                videoPicker.launch("video/*")
            }
        }

        status = TextView(this).apply {
            text = "No videos selected"
            textSize = 15f
            setTextColor(Color.LTGRAY)
            setPadding(0, 20, 0, 15)
        }

        videoList = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        val note = TextView(this).apply {
            text = "Supported output: MP4 • 720p • 30 FPS"
            textSize = 12f
            setTextColor(Color.GRAY)
            setPadding(0, 25, 0, 0)
        }

        root.addView(title)
        root.addView(subtitle)

        root.addView(projectTitle,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 65
            ).apply {
                topMargin = 30
                bottomMargin = 15
            }
        )

        root.addView(addButton)
        root.addView(status)
        root.addView(videoList)
        root.addView(note)

        setContentView(root)
    }

    private fun updateVideos() {
        videoList.removeAllViews()

        status.text = "${videos.size} videos selected"

        videos.forEachIndexed { index, uri ->

            val item = TextView(this).apply {
                text = "${index + 1}. ${uri.lastPathSegment ?: "Video"}"
                textSize = 14f
                setTextColor(Color.WHITE)
                setPadding(15, 18, 15, 18)
                setBackgroundColor(Color.rgb(35, 35, 43))
            }

            videoList.addView(
                item,
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    bottomMargin = 8
                }
            )
        }
    }
}
