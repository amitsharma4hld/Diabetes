package com.s.diabetesfeeding.ui.home.fragment.breastfeeding

import android.app.Activity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import androidx.fragment.app.Fragment
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.entities.breastfeeding.BreastFeedingSessionData
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.ScoreTable
import com.s.diabetesfeeding.util.Coroutines
import kotlinx.android.synthetic.main.fragment_goal_breastfeed.*
import org.threeten.bp.OffsetDateTime
import java.util.concurrent.TimeUnit


class GoalBreastfeedFragment : Fragment() {
    var breastFeedingSessionData : BreastFeedingSessionData? = null
    val breastFeedingScore:Int = 5
    val currentDate: String = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(
        java.util.Date())
    val currentTime: String = java.text.SimpleDateFormat("h:mma", java.util.Locale.getDefault()).format(
        java.util.Date())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let {
            breastFeedingSessionData = GoalBreastfeedFragmentArgs.fromBundle(it).breastFeedingSession
        }
        tv_date.text = currentDate
        tv_time.text = currentTime

        val meter = requireActivity().findViewById<Chronometer>(R.id.tv_timer)
        meter.format = "%s"
        meter.base = SystemClock.elapsedRealtime()
        var timmer:String = ""

        cv_start.setOnClickListener {
            if (tv_start_stop_done.text == "START"){
                tv_start_stop_done.text = "STOP"
                // ISSUE : Start while initializing
                    meter.start()
            }else if (tv_start_stop_done.text == "STOP"){
                tv_start_stop_done.text = "DONE +5"
                meter.stop()
                val elapsedMillis = (SystemClock.elapsedRealtime() - meter.base)
              timmer =   String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(elapsedMillis),
                    TimeUnit.MILLISECONDS.toSeconds(elapsedMillis) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsedMillis))
                )

            }else if (tv_start_stop_done.text == "DONE +5"){
                breastFeedingSessionData?.breastfeedingTime = currentTime
                breastFeedingSessionData?.breastfeedingTimerCount = timmer
                update(breastFeedingSessionData!!)
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goal_breastfeed, container, false)
    }


    fun update(breastFeedingSessionData: BreastFeedingSessionData) {
        Coroutines.io {
            context.let {
                AppDatabase(requireContext()).getBreastFeedingDao().updateBreastFeedSesssion(breastFeedingSessionData)
                Log.d("APPDATABASE : ","Updated id is ${breastFeedingSessionData.id}")
                //requireActivity().onBackPressed()
                updateScore()
            }
        }
    }
    fun updateScore() {
        Coroutines.io {
            context?.let {
                val currentDate = OffsetDateTime.now()
                AppDatabase(it).getHomeMenusDao().saveScores(ScoreTable(0, 0, 10, breastFeedingScore, currentDate))
                Log.d("AppDatabase : ", "Scores Saved ${AppDatabase(it).getHomeMenusDao().getAllScores().size} added")
            }
            (context as Activity).onBackPressed()
        }
    }
}