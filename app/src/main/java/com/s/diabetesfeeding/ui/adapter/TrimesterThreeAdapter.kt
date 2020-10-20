package com.s.diabetesfeeding.ui.adapter

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.obgynentities.ProgressAllTrimester
import com.s.diabetesfeeding.data.db.entities.obgynentities.TrimesterDataOne
import com.s.diabetesfeeding.data.db.entities.obgynentities.TrimesterDataThree
import com.s.diabetesfeeding.data.db.entities.obgynentities.TrimesterDataTwo
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.util.Coroutines
import com.s.diabetesfeeding.util.getStandardFormattedDateForAllScreen
import com.s.diabetesfeeding.util.snackbar
import kotlinx.android.synthetic.main.item_trimester.view.*
import org.threeten.bp.OffsetDateTime
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class TrimesterThreeAdapter(val context: Context, val trimesterTopics : List<TrimesterDataThree>) : RecyclerView.Adapter<TrimesterThreeAdapter.TrimesterViewHolder>(){

    var day = 0
    var month: Int = 0
    var year: Int = 0
    private lateinit var calendar:Calendar

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrimesterViewHolder {
        return TrimesterViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_trimester, parent, false)
        )
    }

    override fun getItemCount()= trimesterTopics.size


    override fun onBindViewHolder(holder: TrimesterViewHolder, position: Int) {
        val topics = trimesterTopics[position]
        holder.view.tv_topic_title.text = topics.title
        holder.view.et_coment.setText(topics.comment)
        holder.view.cb_trimester.isChecked = topics.isChecked
        holder.view.tv_date.text = topics.date
        holder.view.tv_date.setOnClickListener {
            if (!prefs.getSavedDoctorId()?.isNotBlank()!!) {
                if (prefs.getSavedIsPreviousDate()) {
                    it.snackbar("Previous data can not be edited")
                    return@setOnClickListener
                } else
                    calendar = Calendar.getInstance()
                day = calendar.get(Calendar.DAY_OF_MONTH)
                month = calendar.get(Calendar.MONTH)
                year = calendar.get(Calendar.YEAR)

                val mDateSetListener =
                    OnDateSetListener { it, year, monthOfYear, day ->
                        //val date: String = year.toString() + "-" + (monthOfYear + 1).toString() + "-" + day.toString()
                        val selectedDate = formatDate(year, monthOfYear, day)
                        holder.view.tv_date.text =
                            getStandardFormattedDateForAllScreen(selectedDate)
                        topics.date = getStandardFormattedDateForAllScreen(selectedDate)
                    }
                val datePickerDialog = DatePickerDialog(context, mDateSetListener, year, month, day)
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000)
                datePickerDialog.show()
            } else {
                it.snackbar("Can not edit patient details")
            }

        }
        holder.view.cb_trimester.setOnClickListener(View.OnClickListener {
            if (!prefs.getSavedDoctorId()?.isNotBlank()!!) {
                if (prefs.getSavedIsPreviousDate()) {
                    it.snackbar("Previous data can not be edited")
                    if (holder.view.cb_trimester.isChecked) {
                        holder.view.cb_trimester.isChecked = false
                    }
                } else {
                    if (holder.view.cb_trimester.isChecked) {
                        topics.isChecked = true
                        topics.comment = holder.view.et_coment.text.toString()
                        update(topics)
                    } else {
                        topics.isChecked = false
                        topics.comment = holder.view.et_coment.text.toString()
                        update(topics)
                    }
                }
            } else{
                it.snackbar("Can not edit patient details")
                if (holder.view.cb_trimester.isChecked) {
                    holder.view.cb_trimester.isChecked = false
                    Log.d("selected index:",position.toString())
                }
            }

        })
    }
    private fun formatDate(year:Int, month:Int, day:Int):String{
        calendar.set(year, month, day, 0, 0, 0)
        val chosenDate = calendar.time
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM)
        return df.format(chosenDate)
    }

    class TrimesterViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun update(dataOne: TrimesterDataThree) {
        Coroutines.io {
            context.let {
                AppDatabase(it).getObgynDao().updateTrimesterDataThree(dataOne)
                updateProgress(dataOne)
            }
        }
    }
    private fun updateProgress(trimesterDataThree: TrimesterDataThree){
        Coroutines.io {
            context.let {
                val currentDate = OffsetDateTime.now()
                val progressData = ProgressAllTrimester(
                    0,
                    3,
                    trimesterDataThree.title,
                    trimesterDataThree.comment,
                    trimesterDataThree.date,
                    trimesterDataThree.isChecked,
                    currentDate)
                AppDatabase(it).getObgynDao().saveAllTrimesterProgressData(progressData)
            }
        }
    }
}