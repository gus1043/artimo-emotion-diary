package com.example.artimo_emotion_diary

import android.os.Bundle
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
            // Handle item click here
        }
    })

    private lateinit var headerRow: RecyclerView
    private lateinit var calendarList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up header row with day names
        headerRow = findViewById(R.id.header_row)
        headerRow.layoutManager = GridLayoutManager(this, 7)
        headerRow.adapter = WeekAdapter(dayNames)

        // Set up calendar grid
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

        // Add actual dates of the month
        for (i in 1..dayOfMonthCount) {
            val date = LocalDate.of(LocalDate.now().year, LocalDate.now().month, i)
            val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.US)

            itemList.add(Date(dayOfWeek, i.toString()))
        }

        calendarList.adapter = listAdapter
    }
}
