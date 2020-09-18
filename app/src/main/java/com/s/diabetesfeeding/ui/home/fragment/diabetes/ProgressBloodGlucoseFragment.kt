package com.s.diabetesfeeding.ui.home.fragment.diabetes

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.ProgressDataWithCategory
import com.s.diabetesfeeding.data.db.entities.ScoreTable
import com.s.diabetesfeeding.util.Coroutines
import com.s.diabetesfeeding.util.shortToast
import com.s.diabetesfeeding.util.snackbar
import kotlinx.android.synthetic.main.fragment_progress_blood_glucose.*
import kotlinx.android.synthetic.main.progress_wakeup.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


class ProgressBloodGlucoseFragment : Fragment() {

    val progressScore:Int = 5
    private var isProgressScored = false
    val currentDate: String = java.text.SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date())
    var progressWithCategory: List<ProgressDataWithCategory> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("onCreate :","callled")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("onCreateView :","callled")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_progress_blood_glucose, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("onActivityCreated :","callled")

        viewLifecycleOwner.lifecycleScope.launch {
            context?.let {
                pb_graph.visibility = View.VISIBLE

                progressWithCategory =  AppDatabase(it)
                    .getMonitorBloodGlucoseCatDao().getProgressDataWithCategory()

                pb_graph.visibility = View.GONE

            }
        }
        Handler(Looper.getMainLooper()).postDelayed({
            val graphPoints = arrayOf(V11,V12,V13,V14,V15,V16,V17)
            for (i in progressWithCategory[0].progressBloodGlucose.indices){
                //graphPoints[i].post { graphPoints[i].layoutParams.height = dpToPx(progressWithCategory[0].progressBloodGlucose[i].value.toInt()) }
                graphPoints[i].layoutParams.height = dpToPx(progressWithCategory[0].progressBloodGlucose[i].value.toInt())
            }
        }, 9000)


        viewLifecycleOwner.lifecycleScope.launch {
            context?.let {
                if (AppDatabase(it).getHomeMenusDao().getScoreByCategory(5).isNotEmpty()){
                    for (i in AppDatabase(it).getHomeMenusDao().getScoreByCategory(5).indices){
                        val date = AppDatabase(it).getHomeMenusDao().getScoreByCategory(5)[i].date_time
                        val dateToString = date.toString().split("T")
                        val currentDate = getCurrentDateInString()
                        if (currentDate.equals(dateToString[0])) {
                            isProgressScored = true
                            return@let
                        }
                    }
                }
            }

        }

        mcv_progress_done.setOnClickListener {
            if (!isProgressScored){
                updateScore()
                requireActivity().shortToast("Score Updated")
            }else{
                view?.snackbar("Already Saved For Today")
                requireActivity().onBackPressed()
            }
        }
        tv_date_of_week.text= "Week of $currentDate"
    }

    override fun onResume() {
        Log.d("onResume :","callled")
        super.onResume()
    }

    override fun onPause() {
        Log.d("onPause :","callled")
        super.onPause()
    }

    override fun onAttach(context: Context) {
        Log.d("onAttach :","callled")
        super.onAttach(context)
    }
    private fun showWakeupGraph() {
            val graphPoints = arrayOf(V11,V12,V13,V14,V15,V16,V17)
            for (i in progressWithCategory[0].progressBloodGlucose.indices){
                //graphPoints[i].post { graphPoints[i].layoutParams.height = dpToPx(progressWithCategory[0].progressBloodGlucose[i].value.toInt()) }
                graphPoints[i].layoutParams.height = dpToPx(progressWithCategory[0].progressBloodGlucose[i].value.toInt())
            }


    }


    private fun getCurrentDateInString():String{
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
            Date()
        )
    }

    fun updateScore() {
        Coroutines.io {
            context?.let {
                val currentDate = OffsetDateTime.now()
                AppDatabase(it).getHomeMenusDao().saveScores(ScoreTable(0, 0, 5, progressScore, currentDate))
                Log.d("AppDatabase : ", "Scores Saved ${AppDatabase(it).getHomeMenusDao().getAllScores().size} added")
            }
            (context as Activity).onBackPressed()
        }
    }
    fun dpToPx(dp: Int): Int {
        val density: Float = this.resources
            .displayMetrics.density
        return (dp.toFloat() * density).roundToInt()
    }

}