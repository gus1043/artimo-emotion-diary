package com.example.artimo_emotion_diary

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val dayNames = listOf(
        DayOfWeek.SUNDAY,
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY,
        DayOfWeek.SATURDAY
    )

    private val itemList = arrayListOf<Date>()
    private val listAdapter = CalendarAdapter(itemList, object : CalendarAdapter.OnItemClickListener {
        override fun onItemClick(date: Date) {
            val intent = Intent(this@MainActivity, EmojiSelectActivity::class.java).apply {
                putExtra("YEAR", LocalDate.now().year)
                putExtra("MONTH", LocalDate.now().monthValue)
                putExtra("DAY", date.date.toInt())
            }
            startActivity(intent)
            finish()
        }
    })

    private lateinit var headerRow: RecyclerView
    private lateinit var calendarList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val date: TextView = findViewById(R.id.month_year_tv)
        val year = LocalDate.now().year
        val month = LocalDate.now().monthValue
        date.text = getString(R.string.date_format_main, year, month)

        // 요일 이름을 헤더로 설정
        headerRow = findViewById(R.id.header_row)
        headerRow.layoutManager = GridLayoutManager(this, 7)
        headerRow.adapter = WeekAdapter(dayNames)

        // 날짜 들어가는 캘린더
        calendarList = findViewById(R.id.calendar_list)
        val gridLayoutManager = GridLayoutManager(this, 7)
        calendarList.layoutManager = gridLayoutManager

        setListView()
    }

    private fun setListView() {
        val lastDayOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth())
        val dayOfMonthCount = lastDayOfMonth.dayOfMonth

        val firstDayOfMonth = LocalDate.now().withDayOfMonth(1)
        val firstDayOfWeek = (firstDayOfMonth.dayOfWeek.value % 7)

        // Add empty cells for the days before the first of the month
        for (i in 0 until firstDayOfWeek) {
            itemList.add(Date("", ""))
        }

        // 실제 연도, 달, 일 전달
        for (i in 1..dayOfMonthCount) {
            val date = LocalDate.of(LocalDate.now().year, LocalDate.now().month, i)
            val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.US)

            itemList.add(Date(dayOfWeek, i.toString()))
        }

        calendarList.adapter = listAdapter
    }
}
