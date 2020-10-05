package com.s.diabetesfeeding.ui.home.fragment.diabetes

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.ProgressBloodGlucose
import com.s.diabetesfeeding.data.db.entities.ScoreTable
import com.s.diabetesfeeding.databinding.FragmentProgressBloodGlucoseBinding
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.util.Coroutines
import com.s.diabetesfeeding.util.getDateFromOffsetDateTime
import com.s.diabetesfeeding.util.shortToast
import com.s.diabetesfeeding.util.snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_progress_blood_glucose.*
import kotlinx.android.synthetic.main.progress_bedtime.*
import kotlinx.android.synthetic.main.progress_breakfast.*
import kotlinx.android.synthetic.main.progress_diner.*
import kotlinx.android.synthetic.main.progress_lunch.*
import kotlinx.android.synthetic.main.progress_lunch.friday_two_view12
import kotlinx.android.synthetic.main.progress_wakeup.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.threeten.bp.OffsetDateTime
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


class ProgressBloodGlucoseFragment : Fragment(),KodeinAware {
    override val kodein by kodein()
    private val factory : ProgressBloodGlucoseModelFactory by instance()

    private val progressScore:Int = 5
    private var isProgressScored = false
    val currentDateAuto: String = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date())
    private var viewModelJob = Job()
    private var progressBloodGlucose:List<ProgressBloodGlucose> = ArrayList()
    private var progressBloodGlucoseBreakFast:List<ProgressBloodGlucose> = ArrayList()
    private var progressBloodGlucoseLunch:List<ProgressBloodGlucose> = ArrayList()
    private var progressBloodGlucoseDinner:List<ProgressBloodGlucose> = ArrayList()
    private var progressBloodGlucoseBedTime:List<ProgressBloodGlucose> = ArrayList()
    private lateinit var wordViewModel: ProgressBloodGlucoseModel
    lateinit var currentDate:OffsetDateTime
    lateinit var sevendaysDate:OffsetDateTime

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentProgressBloodGlucoseBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_progress_blood_glucose,
            container,
            false
        )
        wordViewModel = ViewModelProvider(this, factory).get(ProgressBloodGlucoseModel::class.java)
        binding.viewmodel = wordViewModel
        binding.lifecycleOwner = this
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            context?.let {
                if (AppDatabase(it).getHomeMenusDao().getScoreByCategory(5).isNotEmpty()){
                    for (i in AppDatabase(it).getHomeMenusDao().getScoreByCategory(5).indices){
                        val date = AppDatabase(it).getHomeMenusDao().getScoreByCategory(5)[i].date_time
                        val dateToString = date.toString().split("T")
                        val currentDate = getCurrentDateInString()
                        if (currentDate == dateToString[0]) {
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

        tb_previous_date.setOnClickListener {
            currentDate = currentDate.minusDays(1)
            sevendaysDate = currentDate.minusDays(7)
            val value = getDateFromOffsetDateTime(currentDate)
            tv_date_of_week.text= getString(R.string.week_of,value)
            setAllProgressUI()
        }
        tb_next_date.setOnClickListener {
            currentDate = currentDate.plusDays(1)
            sevendaysDate = currentDate.minusDays(7)
            val value = getDateFromOffsetDateTime(currentDate)
            tv_date_of_week.text= getString(R.string.week_of,value)
            setAllProgressUI()
        }
        if(!prefs.getOffsetDateTime().isNullOrEmpty()){
            currentDate = OffsetDateTime.parse(prefs.getOffsetDateTime())
            sevendaysDate = currentDate.minusDays(7)
            val value = getDateFromOffsetDateTime(currentDate)
            tv_date_of_week.text= getString(R.string.week_of,value)
            setAllProgressUI()
        }else{
            currentDate =  OffsetDateTime.now()
            sevendaysDate = currentDate.minusDays(7)
            val value = getDateFromOffsetDateTime(currentDate)
            tv_date_of_week.text= getString(R.string.week_of,value)
            setAllProgressUI()
        }

    }

    private fun setAllProgressUI(){

        Coroutines.io {
            // Wakup
            context?.let {
                progressBloodGlucose = AppDatabase(it).getMonitorBloodGlucoseCatDao().getProgressDataBetweenDates(1,sevendaysDate,currentDate)
                val pointsValue = IntArray(7)
                for (i in progressBloodGlucose.indices){
                    if (progressBloodGlucose[i].day=="SUNDAY")
                        pointsValue[0] = progressBloodGlucose[i].value.toInt()
                    if (progressBloodGlucose[i].day=="MONDAY")
                        pointsValue[1] = progressBloodGlucose[i].value.toInt()
                    if (progressBloodGlucose[i].day=="TUESDAY")
                        pointsValue[2] = progressBloodGlucose[i].value.toInt()
                    if (progressBloodGlucose[i].day=="WEDNESDAY")
                        pointsValue[3] = progressBloodGlucose[i].value.toInt()
                    if (progressBloodGlucose[i].day=="THURSDAY")
                        pointsValue[4] = progressBloodGlucose[i].value.toInt()
                    if (progressBloodGlucose[i].day=="FRIDAY")
                        pointsValue[5] = progressBloodGlucose[i].value.toInt()
                    if (progressBloodGlucose[i].day=="SATURDAY")
                        pointsValue[6] = progressBloodGlucose[i].value.toInt()
                }


                requireActivity().runOnUiThread(Runnable {
                    val params: RelativeLayout.LayoutParams = V11.layoutParams as RelativeLayout.LayoutParams
                    params.height = 200
                    V11.layoutParams = params
                    V11.layoutParams.height = dpToPx(pointsValue[0])
                    V12.layoutParams.height = dpToPx(pointsValue[1])
                    V13.layoutParams.height = dpToPx(pointsValue[2])
                    V14.layoutParams.height = dpToPx(pointsValue[3])
                    V15.layoutParams.height = dpToPx(pointsValue[4])
                    V16.layoutParams.height = dpToPx(pointsValue[5])
                    V17.layoutParams.height = dpToPx(pointsValue[6])
                })
            }
        }
        Coroutines.io {
            // BreakFast
            context?.let {
                  progressBloodGlucoseBreakFast = AppDatabase(it).getMonitorBloodGlucoseCatDao().getProgressDataBetweenDates(2,sevendaysDate,currentDate)
                val breakFastPointsValue = IntArray(21)
                for (i in   progressBloodGlucoseBreakFast.indices){
                    if (  progressBloodGlucoseBreakFast[i].day=="SUNDAY"){
                        if (  progressBloodGlucoseBreakFast[i].title == "Before Breakfast")
                            breakFastPointsValue[0] =   progressBloodGlucoseBreakFast[i].value.toInt()
                        if (  progressBloodGlucoseBreakFast[i].title == "1 Hour After Breakfast")
                            breakFastPointsValue[1] =   progressBloodGlucoseBreakFast[i].value.toInt()
                        if (  progressBloodGlucoseBreakFast[i].title == "2 Hour After Breakfast")
                            breakFastPointsValue[2] =   progressBloodGlucoseBreakFast[i].value.toInt()
                    }
                    if (  progressBloodGlucoseBreakFast[i].day=="MONDAY") {
                        if (  progressBloodGlucoseBreakFast[i].title == "Before Breakfast")
                            breakFastPointsValue[3] =
                                progressBloodGlucoseBreakFast[i].value.toInt()
                        if (  progressBloodGlucoseBreakFast[i].title == "1 Hour After Breakfast")
                            breakFastPointsValue[4] =
                                progressBloodGlucoseBreakFast[i].value.toInt()
                        if (  progressBloodGlucoseBreakFast[i].title == "2 Hour After Breakfast")
                            breakFastPointsValue[5] =
                                progressBloodGlucoseBreakFast[i].value.toInt()
                    }
                    if (  progressBloodGlucoseBreakFast[i].day=="TUESDAY") {
                        if (  progressBloodGlucoseBreakFast[i].title == "Before Breakfast")
                            breakFastPointsValue[6] =
                                progressBloodGlucoseBreakFast[i].value.toInt()
                        if (  progressBloodGlucoseBreakFast[i].title == "1 Hour After Breakfast")
                            breakFastPointsValue[7] =
                                progressBloodGlucoseBreakFast[i].value.toInt()
                        if (  progressBloodGlucoseBreakFast[i].title == "2 Hour After Breakfast")
                            breakFastPointsValue[8] =
                                progressBloodGlucoseBreakFast[i].value.toInt()
                    }
                    if (  progressBloodGlucoseBreakFast[i].day=="WEDNESDAY") {
                        if (  progressBloodGlucoseBreakFast[i].title == "Before Breakfast")
                            breakFastPointsValue[9] =
                                progressBloodGlucoseBreakFast[i].value.toInt()
                        if (  progressBloodGlucoseBreakFast[i].title == "1 Hour After Breakfast")
                            breakFastPointsValue[10] =
                                progressBloodGlucoseBreakFast[i].value.toInt()
                        if (  progressBloodGlucoseBreakFast[i].title == "2 Hour After Breakfast")
                            breakFastPointsValue[11] =
                                progressBloodGlucoseBreakFast[i].value.toInt()
                    }
                    if (  progressBloodGlucoseBreakFast[i].day=="THURSDAY") {
                        if (  progressBloodGlucoseBreakFast[i].title == "Before Breakfast")
                            breakFastPointsValue[12] =
                                progressBloodGlucoseBreakFast[i].value.toInt()
                        if (  progressBloodGlucoseBreakFast[i].title == "1 Hour After Breakfast")
                            breakFastPointsValue[13] =
                                progressBloodGlucoseBreakFast[i].value.toInt()
                        if (  progressBloodGlucoseBreakFast[i].title == "2 Hour After Breakfast")
                            breakFastPointsValue[14] =
                                progressBloodGlucoseBreakFast[i].value.toInt()
                    }
                    if (  progressBloodGlucoseBreakFast[i].day=="FRIDAY") {
                        if (  progressBloodGlucoseBreakFast[i].title == "Before Breakfast")
                            breakFastPointsValue[15] =
                                progressBloodGlucoseBreakFast[i].value.toInt()
                        if (  progressBloodGlucoseBreakFast[i].title == "1 Hour After Breakfast")
                            breakFastPointsValue[16] =
                                progressBloodGlucoseBreakFast[i].value.toInt()
                        if (  progressBloodGlucoseBreakFast[i].title == "2 Hour After Breakfast")
                            breakFastPointsValue[17] =
                                progressBloodGlucoseBreakFast[i].value.toInt()
                    }
                    if (  progressBloodGlucoseBreakFast[i].day=="SATURDAY") {
                        if (  progressBloodGlucoseBreakFast[i].title == "Before Breakfast")
                            breakFastPointsValue[18] =
                                progressBloodGlucoseBreakFast[i].value.toInt()
                        if (  progressBloodGlucoseBreakFast[i].title == "1 Hour After Breakfast")
                            breakFastPointsValue[19] =
                                progressBloodGlucoseBreakFast[i].value.toInt()
                        if (  progressBloodGlucoseBreakFast[i].title == "2 Hour After Breakfast")
                            breakFastPointsValue[20] =
                                progressBloodGlucoseBreakFast[i].value.toInt()
                    }
                }

                requireActivity().runOnUiThread(Runnable {
                    //breakfast_pb_graph.visibility = View.GONE
                    val breakfastParams: RelativeLayout.LayoutParams = V11.layoutParams as RelativeLayout.LayoutParams
                    breakfastParams.height = 200
                    breakfast_V11.layoutParams = breakfastParams

                    breakfast_V11.layoutParams.height = dpToPx(breakFastPointsValue[0])
                    breakfast_sunday_one_V12.layoutParams.height = dpToPx(breakFastPointsValue[1])
                    breakfast_sunday_two_V12.layoutParams.height = dpToPx(breakFastPointsValue[2])

                    breakfast_breakfast_V12.layoutParams.height = dpToPx(breakFastPointsValue[3])
                    breakfast_monday_one_V12.layoutParams.height = dpToPx(breakFastPointsValue[4])
                    breakfast_monday_two_V12.layoutParams.height = dpToPx(breakFastPointsValue[5])

                    breakfast_V13.layoutParams.height = dpToPx(breakFastPointsValue[6])
                    breakfast_tuesday_one_V12.layoutParams.height = dpToPx(breakFastPointsValue[7])
                    breakfast_tuesday_two_V12.layoutParams.height = dpToPx(breakFastPointsValue[8])

                    breakfast_V14.layoutParams.height = dpToPx(breakFastPointsValue[9])
                    breakfast_wednesday_one_V12.layoutParams.height = dpToPx(breakFastPointsValue[10])
                    breakfast_wednesday_two_V12.layoutParams.height = dpToPx(breakFastPointsValue[11])

                    breakfast_V15.layoutParams.height = dpToPx(breakFastPointsValue[12])
                    breakfast_thursday_one_V12.layoutParams.height = dpToPx(breakFastPointsValue[13])
                    breakfast_thursday_two_V12.layoutParams.height = dpToPx(breakFastPointsValue[14])

                    breakfast_V16.layoutParams.height = dpToPx(breakFastPointsValue[15])
                    breakfast_friday_one_V12.layoutParams.height = dpToPx(breakFastPointsValue[16])
                    breakfast_friday_two_V12.layoutParams.height = dpToPx(breakFastPointsValue[17])

                    breakfast_V17.layoutParams.height = dpToPx(breakFastPointsValue[18])
                    breakfast_saturday_one_V12.layoutParams.height = dpToPx(breakFastPointsValue[19])
                    breakfast_saturday_two_V12.layoutParams.height = dpToPx(breakFastPointsValue[20])
                })

            }
        }
        Coroutines.io {
            // LUNCH
            context?.let {
                progressBloodGlucoseLunch = AppDatabase(it).getMonitorBloodGlucoseCatDao().getProgressDataBetweenDates(3,sevendaysDate,currentDate)
                val lunchPointsValue = IntArray(21)
                for (i in  progressBloodGlucoseLunch.indices){
                    if (progressBloodGlucoseLunch[i].day=="SUNDAY"){
                        if (progressBloodGlucoseLunch[i].title == "Before Lunch")
                            lunchPointsValue[0] = progressBloodGlucoseLunch[i].value.toInt()
                        if (progressBloodGlucoseLunch[i].title == "1 Hour After Lunch")
                            lunchPointsValue[1] = progressBloodGlucoseLunch[i].value.toInt()
                        if (progressBloodGlucoseLunch[i].title == "2 Hour After Lunch")
                            lunchPointsValue[2] = progressBloodGlucoseLunch[i].value.toInt()
                    }
                    if (progressBloodGlucoseLunch[i].day=="MONDAY") {
                        if (progressBloodGlucoseLunch[i].title == "Before Lunch")
                            lunchPointsValue[3] =
                                progressBloodGlucoseLunch[i].value.toInt()
                        if (progressBloodGlucoseLunch[i].title == "1 Hour After Lunch")
                            lunchPointsValue[4] =
                                progressBloodGlucoseLunch[i].value.toInt()
                        if (progressBloodGlucoseLunch[i].title == "2 Hour After Lunch")
                            lunchPointsValue[5] =
                                progressBloodGlucoseLunch[i].value.toInt()
                    }
                    if (progressBloodGlucoseLunch[i].day=="TUESDAY") {
                        if (progressBloodGlucoseLunch[i].title == "Before Lunch")
                            lunchPointsValue[6] =
                                progressBloodGlucoseLunch[i].value.toInt()
                        if (progressBloodGlucoseLunch[i].title == "1 Hour After Lunch")
                            lunchPointsValue[7] =
                                progressBloodGlucoseLunch[i].value.toInt()
                        if (progressBloodGlucoseLunch[i].title == "2 Hour After Lunch")
                            lunchPointsValue[8] =
                                progressBloodGlucoseLunch[i].value.toInt()
                    }
                    if (progressBloodGlucoseLunch[i].day=="WEDNESDAY") {
                        if (progressBloodGlucoseLunch[i].title == "Before Lunch")
                            lunchPointsValue[9] =
                                progressBloodGlucoseLunch[i].value.toInt()
                        if (progressBloodGlucoseLunch[i].title == "1 Hour After Lunch")
                            lunchPointsValue[10] =
                                progressBloodGlucoseLunch[i].value.toInt()
                        if (progressBloodGlucoseLunch[i].title == "2 Hour After Lunch")
                            lunchPointsValue[11] =
                                progressBloodGlucoseLunch[i].value.toInt()
                    }
                    if (progressBloodGlucoseLunch[i].day=="THURSDAY") {
                        if (progressBloodGlucoseLunch[i].title == "Before Lunch")
                            lunchPointsValue[12] =
                                progressBloodGlucoseLunch[i].value.toInt()
                        if (progressBloodGlucoseLunch[i].title == "1 Hour After Lunch")
                            lunchPointsValue[13] =
                                progressBloodGlucoseLunch[i].value.toInt()
                        if (progressBloodGlucoseLunch[i].title == "2 Hour After Lunch")
                            lunchPointsValue[14] =
                                progressBloodGlucoseLunch[i].value.toInt()
                    }
                    if (progressBloodGlucoseLunch[i].day=="FRIDAY") {
                        if (progressBloodGlucoseLunch[i].title == "Before Lunch")
                            lunchPointsValue[15] =
                                progressBloodGlucoseLunch[i].value.toInt()
                        if (progressBloodGlucoseLunch[i].title == "1 Hour After Lunch")
                            lunchPointsValue[16] =
                                progressBloodGlucoseLunch[i].value.toInt()
                        if (progressBloodGlucoseLunch[i].title == "2 Hour After Lunch")
                            lunchPointsValue[17] =
                                progressBloodGlucoseLunch[i].value.toInt()
                    }
                    if (progressBloodGlucoseLunch[i].day=="SATURDAY") {
                        if (progressBloodGlucoseLunch[i].title == "Before Lunch")
                            lunchPointsValue[18] =
                                progressBloodGlucoseLunch[i].value.toInt()
                        if (progressBloodGlucoseLunch[i].title == "1 Hour After Lunch")
                            lunchPointsValue[19] =
                                progressBloodGlucoseLunch[i].value.toInt()
                        if (progressBloodGlucoseLunch[i].title == "2 Hour After Lunch")
                            lunchPointsValue[20] =
                                progressBloodGlucoseLunch[i].value.toInt()
                    }
                }
                requireActivity().runOnUiThread(Runnable {
                    pb_lunch_graph.visibility = View.GONE

                    val breakfastParams: RelativeLayout.LayoutParams = V11.layoutParams as RelativeLayout.LayoutParams
                    breakfastParams.height = 200
                    lunch_V11.layoutParams = breakfastParams

                    lunch_V11.layoutParams.height = dpToPx(lunchPointsValue[0])
                    sunday_onelunch_V12.layoutParams.height = dpToPx(lunchPointsValue[1])
                    sunday_twolunch_V12.layoutParams.height = dpToPx(lunchPointsValue[2])

                    lunch_V12.layoutParams.height = dpToPx(lunchPointsValue[3])
                    monday_onelunch_V12.layoutParams.height = dpToPx(lunchPointsValue[4])
                    monday_twolunch_V12.layoutParams.height = dpToPx(lunchPointsValue[5])

                    lunch_V13.layoutParams.height = dpToPx(lunchPointsValue[6])
                    tuesday_onelunch_V12.layoutParams.height = dpToPx(lunchPointsValue[7])
                    tuesday_twolunch_V12.layoutParams.height = dpToPx(lunchPointsValue[8])

                    lunch_V14.layoutParams.height = dpToPx(lunchPointsValue[9])
                    wednesday_onelunch_V12.layoutParams.height = dpToPx(lunchPointsValue[10])
                    wednesday_twolunch_V12.layoutParams.height = dpToPx(lunchPointsValue[11])

                    lunch_V15.layoutParams.height = dpToPx(lunchPointsValue[12])
                    thursday_onelunch_V12.layoutParams.height = dpToPx(lunchPointsValue[13])
                    thursday_twolunch_V12.layoutParams.height = dpToPx(lunchPointsValue[14])

                    lunch_V16.layoutParams.height = dpToPx(lunchPointsValue[15])
                    friday_onelunch_V12.layoutParams.height = dpToPx(lunchPointsValue[16])
                    friday_two_view12.layoutParams.height = dpToPx(lunchPointsValue[17])

                    lunch_V17.layoutParams.height = dpToPx(lunchPointsValue[18])
                    lunch_saturday_onelunch_V12.layoutParams.height = dpToPx(lunchPointsValue[19])
                    lunch_saturday_twolunch_V12.layoutParams.height = dpToPx(lunchPointsValue[20])
                })
            }
        }
        Coroutines.io {
            // DINNER
            context?.let {
                progressBloodGlucoseDinner = AppDatabase(it).getMonitorBloodGlucoseCatDao().getProgressDataBetweenDates(4,sevendaysDate,currentDate)
                val dinnerPointsValue = IntArray(21)
                for (i in  progressBloodGlucoseDinner.indices){
                    if (progressBloodGlucoseDinner[i].day=="SUNDAY"){
                        if (progressBloodGlucoseDinner[i].title == "Before Diner")
                            dinnerPointsValue[0] = progressBloodGlucoseDinner[i].value.toInt()
                        if (progressBloodGlucoseDinner[i].title == "1 Hour After Diner")
                            dinnerPointsValue[1] = progressBloodGlucoseDinner[i].value.toInt()
                        if (progressBloodGlucoseDinner[i].title == "2 Hour After Diner")
                            dinnerPointsValue[2] = progressBloodGlucoseDinner[i].value.toInt()
                    }
                    if (progressBloodGlucoseDinner[i].day=="MONDAY") {
                        if (progressBloodGlucoseDinner[i].title == "Before Diner")
                            dinnerPointsValue[3] =
                                progressBloodGlucoseDinner[i].value.toInt()
                        if (progressBloodGlucoseDinner[i].title == "1 Hour After Diner")
                            dinnerPointsValue[4] =
                                progressBloodGlucoseDinner[i].value.toInt()
                        if (progressBloodGlucoseDinner[i].title == "2 Hour After Diner")
                            dinnerPointsValue[5] =
                                progressBloodGlucoseDinner[i].value.toInt()
                    }
                    if (progressBloodGlucoseDinner[i].day=="TUESDAY") {
                        if (progressBloodGlucoseDinner[i].title == "Before Diner")
                            dinnerPointsValue[6] =
                                progressBloodGlucoseDinner[i].value.toInt()
                        if (progressBloodGlucoseDinner[i].title == "1 Hour After Diner")
                            dinnerPointsValue[7] =
                                progressBloodGlucoseDinner[i].value.toInt()
                        if (progressBloodGlucoseDinner[i].title == "2 Hour After Diner")
                            dinnerPointsValue[8] =
                                progressBloodGlucoseDinner[i].value.toInt()
                    }
                    if (progressBloodGlucoseDinner[i].day=="WEDNESDAY") {
                        if (progressBloodGlucoseDinner[i].title == "Before Diner")
                            dinnerPointsValue[9] =
                                progressBloodGlucoseDinner[i].value.toInt()
                        if (progressBloodGlucoseDinner[i].title == "1 Hour After Diner")
                            dinnerPointsValue[10] =
                                progressBloodGlucoseDinner[i].value.toInt()
                        if (progressBloodGlucoseDinner[i].title == "2 Hour After Diner")
                            dinnerPointsValue[11] =
                                progressBloodGlucoseDinner[i].value.toInt()
                    }
                    if (progressBloodGlucoseDinner[i].day=="THURSDAY") {
                        if (progressBloodGlucoseDinner[i].title == "Before Diner")
                            dinnerPointsValue[12] =
                                progressBloodGlucoseDinner[i].value.toInt()
                        if (progressBloodGlucoseDinner[i].title == "1 Hour After Diner")
                            dinnerPointsValue[13] =
                                progressBloodGlucoseDinner[i].value.toInt()
                        if (progressBloodGlucoseDinner[i].title == "2 Hour After Diner")
                            dinnerPointsValue[14] =
                                progressBloodGlucoseDinner[i].value.toInt()
                    }
                    if (progressBloodGlucoseDinner[i].day=="FRIDAY") {
                        if (progressBloodGlucoseDinner[i].title == "Before Diner")
                            dinnerPointsValue[15] =
                                progressBloodGlucoseDinner[i].value.toInt()
                        if (progressBloodGlucoseDinner[i].title == "1 Hour After Diner")
                            dinnerPointsValue[16] =
                                progressBloodGlucoseDinner[i].value.toInt()
                        if (progressBloodGlucoseDinner[i].title == "2 Hour After Diner")
                            dinnerPointsValue[17] =
                                progressBloodGlucoseDinner[i].value.toInt()
                    }
                    if (progressBloodGlucoseDinner[i].day=="SATURDAY") {
                        if (progressBloodGlucoseDinner[i].title == "Before Diner")
                            dinnerPointsValue[18] =
                                progressBloodGlucoseDinner[i].value.toInt()
                        if (progressBloodGlucoseDinner[i].title == "1 Hour After Diner")
                            dinnerPointsValue[19] =
                                progressBloodGlucoseDinner[i].value.toInt()
                        if (progressBloodGlucoseDinner[i].title == "2 Hour After Diner")
                            dinnerPointsValue[20] =
                                progressBloodGlucoseDinner[i].value.toInt()
                    }
                }
                requireActivity().runOnUiThread(Runnable {
                    pb_graph_dinner.visibility = View.GONE

                    val breakfastParams: RelativeLayout.LayoutParams = V11.layoutParams as RelativeLayout.LayoutParams
                    breakfastParams.height = 200
                    dinnerV11.layoutParams = breakfastParams

                    dinnerV11.layoutParams.height = dpToPx(dinnerPointsValue[0])
                    dinner_sunday_one_V12.layoutParams.height = dpToPx(dinnerPointsValue[1])
                    dinner_sunday_two_V12.layoutParams.height = dpToPx(dinnerPointsValue[2])

                    dinner_V12.layoutParams.height = dpToPx(dinnerPointsValue[3])
                    dinner_monday_one_V12.layoutParams.height = dpToPx(dinnerPointsValue[4])
                    dinner_monday_two_V12.layoutParams.height = dpToPx(dinnerPointsValue[5])

                    dinner_V13.layoutParams.height = dpToPx(dinnerPointsValue[6])
                    dinner_tuesday_one_V12.layoutParams.height = dpToPx(dinnerPointsValue[7])
                    dinner_tuesday_two_V12.layoutParams.height = dpToPx(dinnerPointsValue[8])

                    dinner_V14.layoutParams.height = dpToPx(dinnerPointsValue[9])
                    dinner_wednesday_one_V12.layoutParams.height = dpToPx(dinnerPointsValue[10])
                    dinner_wednesday_two_V12.layoutParams.height = dpToPx(dinnerPointsValue[11])

                    dinner_V15.layoutParams.height = dpToPx(dinnerPointsValue[12])
                    dinner_thursday_one_V12.layoutParams.height = dpToPx(dinnerPointsValue[13])
                    dinner_thursday_two_V12.layoutParams.height = dpToPx(dinnerPointsValue[14])

                    dinner_V16.layoutParams.height = dpToPx(dinnerPointsValue[15])
                    dinner_friday_one_V12.layoutParams.height = dpToPx(dinnerPointsValue[16])
                    dinner_friday_two_V12.layoutParams.height = dpToPx(dinnerPointsValue[17])

                    dinner_V17.layoutParams.height = dpToPx(dinnerPointsValue[18])
                    dinner_saturday_one_V12.layoutParams.height = dpToPx(dinnerPointsValue[19])
                    dinner_saturday_two_V12.layoutParams.height = dpToPx(dinnerPointsValue[20])
                })
            }
        }
        Coroutines.io {
            // BedTime
            context?.let {
                progressBloodGlucoseBedTime = AppDatabase(it).getMonitorBloodGlucoseCatDao().getProgressDataBetweenDates(5,sevendaysDate,currentDate)
                val bedTimePointsValue = IntArray(7)
                for (i in progressBloodGlucoseBedTime.indices){
                    if (progressBloodGlucoseBedTime[i].day=="SUNDAY")
                        bedTimePointsValue[0] = progressBloodGlucoseBedTime[i].value.toInt()
                    if (progressBloodGlucoseBedTime[i].day=="MONDAY")
                        bedTimePointsValue[1] = progressBloodGlucoseBedTime[i].value.toInt()
                    if (progressBloodGlucoseBedTime[i].day=="TUESDAY")
                        bedTimePointsValue[2] = progressBloodGlucoseBedTime[i].value.toInt()
                    if (progressBloodGlucoseBedTime[i].day=="WEDNESDAY")
                        bedTimePointsValue[3] = progressBloodGlucoseBedTime[i].value.toInt()
                    if (progressBloodGlucoseBedTime[i].day=="THURSDAY")
                        bedTimePointsValue[4] = progressBloodGlucoseBedTime[i].value.toInt()
                    if (progressBloodGlucoseBedTime[i].day=="FRIDAY")
                        bedTimePointsValue[5] = progressBloodGlucoseBedTime[i].value.toInt()
                    if (progressBloodGlucoseBedTime[i].day=="SATURDAY")
                        bedTimePointsValue[6] = progressBloodGlucoseBedTime[i].value.toInt()
                }


                requireActivity().runOnUiThread(Runnable {
                    pb_graph_bedtime.visibility = View.GONE
                    val params: RelativeLayout.LayoutParams = V11.layoutParams as RelativeLayout.LayoutParams
                    params.height = 200
                    bedtime_V11.layoutParams = params
                    bedtime_V11.layoutParams.height = dpToPx(bedTimePointsValue[0])
                    bedtime_V12.layoutParams.height = dpToPx(bedTimePointsValue[1])
                    bedtime_V13.layoutParams.height = dpToPx(bedTimePointsValue[2])
                    bedtime_V14.layoutParams.height = dpToPx(bedTimePointsValue[3])
                    bedtime_V15.layoutParams.height = dpToPx(bedTimePointsValue[4])
                    bedtime_V16.layoutParams.height = dpToPx(bedTimePointsValue[5])
                    bedtime_V17.layoutParams.height = dpToPx(bedTimePointsValue[6])
                })
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
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
        val density: Float = this.resources.displayMetrics.density
        return (dp.toFloat() * density).roundToInt()
    }

}