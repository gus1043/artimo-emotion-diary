package com.example.artimo_emotion_diary

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.artimo_emotion_diary.roomdb.DiaryDaoDatabase
import com.example.artimo_emotion_diary.roomdb.DiaryTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class WriteActivity : AppCompatActivity() {

    private lateinit var todayimage: ImageView
    private lateinit var todayemoji: ImageView
    private lateinit var diary: EditText
    private lateinit var caption: EditText
    private lateinit var writebtn: Button

    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        todayimage = findViewById(R.id.todayimage)
        todayemoji = findViewById(R.id.todayemoji)
        diary = findViewById(R.id.diary)
        caption = findViewById(R.id.caption)
        writebtn = findViewById(R.id.writebtn)

        val year = intent.getIntExtra("YEAR", 0)
        val month = intent.getIntExtra("MONTH", 0)
        val day = intent.getIntExtra("DAY", 0)
        val emoji = intent.getStringExtra("emoji") ?: ""

        // 날짜 설정
        val date: TextView = findViewById(R.id.date)
        date.text = getString(R.string.date_format_emoji, year, month, day)

        if (emoji != null) {
            try {
                // assets에서 이미지를 불러옴
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

        writebtn.setOnClickListener{
            val diaryText = diary.text.toString()
            val captionText = caption.text.toString()

            // roomdb에 저장
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val db = DiaryDaoDatabase.getDatabase(applicationContext)
                    val diaryDAO = db?.diaryDAO()

                    // diaryEntity 생성
                    val diaryEntity = DiaryTable(
                        year = year,
                        month = month,
                        date = day,
                        emoji = emoji,
                        diary = diaryText,
                        imageUri = imageUri?.toString(),
                        caption = captionText
                    )
                    diaryDAO?.insert(diaryEntity)

                    // 다이어리에 저장이 되면 다이어리의 내용을 전체 출력해서 db 확인
                    val allDiaries = diaryDAO?.selectALL()
                    allDiaries?.forEach {
                        Log.d("DiaryEntry", "Year: ${it.year}, Month: ${it.month}, Day: ${it.date}, Emoji: ${it.emoji}, Diary: ${it.diary}, ImageUri: ${it.imageUri}, Caption: ${it.caption}")
                    }
                }
            }

            // 적은 내용 확인 페이지로 이동
            val intent = Intent(this, DiaryActivity::class.java).apply {
                putExtra("YEAR", year)
                putExtra("MONTH", month)
                putExtra("DAY", day)
                putExtra("emoji", emoji)
                putExtra("imageUri", imageUri.toString())
                putExtra("diary", diaryText)
                putExtra("caption", captionText)
            }
            startActivity(intent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            imageUri = data.data
            todayimage.setImageURI(imageUri)
        }
    }
}
