package com.example.artimo_emotion_diary

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class WriteActivity : AppCompatActivity() {

    private lateinit var todayimage: ImageView
    private lateinit var todayemoji: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        // findViewById 호출은 onCreate 안에서 해야 합니다.
        todayimage = findViewById(R.id.todayimage)
        todayemoji = findViewById(R.id.todayemoji)

        // 인텐트로부터 전달된 emoji 값을 받습니다.
        val emoji = intent.getStringExtra("emoji")

        if (emoji != null) {
            try {
                // assets에서 이미지를 불러옵니다.
                val inputStream = assets.open(emoji)
                val drawable = Drawable.createFromStream(inputStream, null)
                todayemoji.setImageDrawable(drawable)
                inputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
                todayemoji.setImageResource(R.drawable.logo) // 에러 발생 시 기본 이미지 설정
            }
        } else {
            // null인 경우 처리 (기본 이미지 설정)
            todayemoji.setImageResource(R.drawable.logo)
        }

        // 이미지 선택 버튼 클릭 리스너 설정
        todayimage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data
            todayimage.setImageURI(imageUri)
        }
    }
}
