package com.s.diabetesfeeding.ui.home.fragment.breastfeeding

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.ScoreTable
import com.s.diabetesfeeding.data.db.entities.breastfeeding.BabyPoopData
import com.s.diabetesfeeding.data.db.entities.breastfeeding.BreastFeedingSessionData
import com.s.diabetesfeeding.data.db.entities.breastfeeding.ProgressBabyPoopDiaperChange
import com.s.diabetesfeeding.data.db.entities.breastfeeding.ProgressBreastFeeding
import com.s.diabetesfeeding.util.Coroutines
import kotlinx.android.synthetic.main.fragment_diaper_change.*
import org.threeten.bp.OffsetDateTime
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.net.URLEncoder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class DiaperChangeFragment : Fragment() {
    val currentTime: String = java.text.SimpleDateFormat("h:mma", java.util.Locale.getDefault()).format(
        java.util.Date())
    var DiaperChangeSessionData : BabyPoopData? = null
    val diaperChangeScore:Int = 5
    var isPoopSelected:Boolean  = false
    var isPeepSelected:Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diaper_change, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let {
            DiaperChangeSessionData = DiaperChangeFragmentArgs.fromBundle(it).babyPoopData
        }

        tv_time.text=currentTime

        iv_poop.setOnClickListener {
            isPoopSelected = !isPoopSelected
            if (isPoopSelected){
                iv_poop.setImageResource(R.drawable.ic_poop_fill)
                mcv_diaper_done.visibility = View.VISIBLE
                DiaperChangeSessionData?.isPoop = true
            }else{
                iv_poop.setImageResource(R.drawable.ic_poop_unfill)
            }
            if (!isPoopSelected && !isPeepSelected){
                mcv_diaper_done.visibility = View.GONE
            }

        }
        iv_pee.setOnClickListener {
            isPeepSelected = !isPeepSelected
            if (isPeepSelected){
                iv_pee.setImageResource(R.drawable.ic_pee_fill)
                mcv_diaper_done.visibility = View.VISIBLE
                DiaperChangeSessionData?.isPee = true
            }else{
                iv_pee.setImageResource(R.drawable.ic_pee_unfill)
            }
            if (!isPoopSelected && !isPeepSelected){
                mcv_diaper_done.visibility = View.GONE
            }

        }
        mcv_diaper_done.setOnClickListener {
            DiaperChangeSessionData?.poop_pee_time = currentTime
            updateProgressData(DiaperChangeSessionData!!)

        }

    }

    fun update(babyPoopData: BabyPoopData) {
        Coroutines.io {
            context.let {
                AppDatabase(requireContext()).getBreastFeedingDao().updateDiaperChangeSesssion(babyPoopData)
                Log.d("APPDATABASE : ","Updated id is ${babyPoopData.id}")
                updateScore()
            }
        }
    }
    // New Line
    private fun updateProgressData(babyPoopData: BabyPoopData) {
        // NEW LINE
        Coroutines.io {
            context.let {
                val currentDate = OffsetDateTime.now()
                val progressData =  ProgressBabyPoopDiaperChange(0,babyPoopData.temId,babyPoopData.poop_pee_time,babyPoopData.isPoop, babyPoopData.isPee,currentDate)
                AppDatabase(requireContext()).getBreastFeedingDao().saveProgressDiaperChange(progressData)
                Log.d("APPDATABASE : ","progressData value is ${progressData.dateTime}")
                update(babyPoopData)
            }
        }
    }
    fun updateScore() {
        Coroutines.io {
            context?.let {
                val currentDate = OffsetDateTime.now()
                AppDatabase(it).getHomeMenusDao().saveScores(ScoreTable(0, 0, 12, diaperChangeScore, currentDate))
                Log.d("AppDatabase : ", "Scores Saved ${AppDatabase(it).getHomeMenusDao().getAllScores().size} added")
            }
            (context as Activity).onBackPressed()
        }
    }
}