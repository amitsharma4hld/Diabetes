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
            iv_poop.setImageResource(R.drawable.ic_poop_fill)
            iv_pee.setImageResource(R.drawable.ic_pee_unfill)
            mcv_diaper_done.visibility = View.VISIBLE
            DiaperChangeSessionData?.isPoop = true
        }
        iv_pee.setOnClickListener {
            iv_pee.setImageResource(R.drawable.ic_pee_fill)
            iv_poop.setImageResource(R.drawable.ic_poop_unfill)
            mcv_diaper_done.visibility = View.VISIBLE
            DiaperChangeSessionData?.isPee = true
        }
        mcv_diaper_done.setOnClickListener {
            DiaperChangeSessionData?.poop_pee_time = currentTime
            update(DiaperChangeSessionData!!)
        }

    }

    fun update(babyPoopData: BabyPoopData) {
        Coroutines.io {
            context.let {
                AppDatabase(requireContext()).getBreastFeedingDao().updateDiaperChangeSesssion(babyPoopData)
                Log.d("APPDATABASE : ","Updated id is ${babyPoopData.id}")
                //requireActivity().onBackPressed()
                updateScore()
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