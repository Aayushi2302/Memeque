package com.example.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private var selectDate:TextView ? = null
    private var selectDateMinutes : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn: Button = findViewById(R.id.button)
        selectDate = findViewById(R.id.selectedDate)
        selectDateMinutes = findViewById(R.id.inMinutes)

        btn.setOnClickListener {
            clickDatePicker()
        }
    }


    private fun clickDatePicker(){

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this,

            //view is never used so we use _
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDayOfMonth ->

                val date = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
                selectDate?.text = date

                //Simple Date Format is used to arrange the date in form of a particular format
                val sdf = SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH)

                val theDate = sdf.parse(date)
                    theDate?.let{       //for null safety

                    val selectedDateInMinutes = theDate.time / 60000
                    /*Here in the above statement we are using time property to get the selected date time
                    milliseconds and this property is same as using getTime() function in Kotlin
                    Now to convert in minutes from milliseconds we have to first divide by 1000 then we have to divide by 60
                    so we divide by 60000
                     */

                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let{   // for null safety
                        val currentDateInMinutes = currentDate.time/60000
                        val difference = currentDateInMinutes - selectedDateInMinutes

                        selectDateMinutes?.text = difference.toString()
                    }

                }
            },
            year,
            month,
            day
        )

        dpd.datePicker.maxDate = System.currentTimeMillis()
        dpd.show()

    }
}