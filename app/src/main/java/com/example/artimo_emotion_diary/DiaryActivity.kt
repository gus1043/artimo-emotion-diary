package com.example.artimo_emotion_diary

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DiaryActivity : AppCompatActivity() {

    private lateinit var todayimage: ImageView
    private lateinit var todayemoji: ImageView
    private lateinit var todaydiary: TextView
    private lateinit var todaycaption: TextView
    private lateinit var tomainbtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        todayimage = findViewById(R.id.todayimage)
        todayemoji = findViewById(R.id.todayemoji)
        todaydiary = findViewById(R.id.todaydiary)
        todaycaption = findViewById(R.id.todaycaption)
        tomainbtn = findViewById(R.id.tomainbtn)

        // intent로 받아온거 불러오기
        val year = intent.getIntExtra("YEAR", 0)
        val month = intent.getIntExtra("MONTH", 0)
        val day = intent.getIntExtra("DAY", 0)
        val emoji = intent.getStringExtra("emoji")
        val diary = intent.getStringExtra("diary")
        val caption = intent.getStringExtra("caption")
        val imageUriString = intent.getStringExtra("imageUri")

        // 날짜 설정
        val date: TextView = findViewById(R.id.date)
        date.text = getString(R.string.date_format_emoji, year, month, day)

        // 이모지 설정
        if (emoji != null) {
            try {
                val inputStream = assets.open(emoji)
                val drawable = Drawable.createFromStream(inputStream, null)
                todayemoji.setImageDrawable(drawable)
                inputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
                todayemoji.setImageResource(R.drawable.logo) // Default image in case of error
            }
        } else {
            todayemoji.setImageResource(R.drawable.logo) // Default image if emoji is null
        }

        // 글이랑 캡션 설정
        todaydiary.text = diary
        todaycaption.text = caption

        // 이미지 등록
        imageUriString?.let {
            val imageUri = Uri.parse(it)
            todayimage.setImageURI(imageUri)
        }

        //메인으로 돌아가기
        tomainbtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
