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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.ProgressBloodGlucose
import com.s.diabetesfeeding.data.db.entities.ProgressDataWithCategory
import com.s.diabetesfeeding.data.db.entities.ScoreTable
import com.s.diabetesfeeding.databinding.FragmentProgressBloodGlucoseBinding
import com.s.diabetesfeeding.util.Coroutines
import com.s.diabetesfeeding.util.shortToast
import com.s.diabetesfeeding.util.snackbar
import kotlinx.android.synthetic.main.fragment_progress_blood_glucose.*
import kotlinx.android.synthetic.main.progress_bedtime.*
import kotlinx.android.synthetic.main.progress_breakfast.*
import kotlinx.android.synthetic.main.progress_diner.*
import kotlinx.android.synthetic.main.progress_lunch.*
import kotlinx.android.synthetic.main.progress_lunch.friday_two_view12
import kotlinx.android.synthetic.main.progress_wakeup.*
import kotlinx.android.synthetic.main.progress_wakeup.V11
import kotlinx.android.synthetic.main.progress_wakeup.V12
import kotlinx.android.synthetic.main.progress_wakeup.V13
import kotlinx.android.synthetic.main.progress_wakeup.V14
import kotlinx.android.synthetic.main.progress_wakeup.V15
import kotlinx.android.synthetic.main.progress_wakeup.V16
import kotlinx.android.synthetic.main.progress_wakeup.V17
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
    val currentDate: String = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date())
    private var viewModelJob = Job()
    private val pointsValue = IntArray(7)
    private val breakFastPointsValue = IntArray(21)
    private val lunchPointsValue = IntArray(21)
    private val dinnerPointsValue = IntArray(21)
    private val bedTimePointsValue = IntArray(7)
    private var progressBloodGlucose:List<ProgressBloodGlucose> = ArrayList()
    private var progressBloodGlucoseBreakFast:List<ProgressBloodGlucose> = ArrayList()
    private var progressBloodGlucoseLunch:List<ProgressBloodGlucose> = ArrayList()
    private var progressBloodGlucoseDinner:List<ProgressBloodGlucose> = ArrayList()
    private var progressBloodGlucoseBedTime:List<ProgressBloodGlucose> = ArrayList()



    private lateinit var wordViewModel: ProgressBloodGlucoseModel


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

        setAllProgressUI()

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
        tv_date_of_week.text= "Week of $currentDate"

        tv_date_of_week.setOnClickListener {
            val currentDate = OffsetDateTime.parse("2020-09-19T16:47:39.824+05:30")
            val sevendaysDate = OffsetDateTime.parse("2020-09-20T16:46:57.369+05:30")
        }
    }

    private fun setAllProgressUI(){

        Coroutines.io {
            // Wakup
            context?.let {
                pb_graph.visibility = View.VISIBLE
                val currentDate = OffsetDateTime.parse("2020-09-19T16:47:39.824+05:30")
                val sevendaysDate = OffsetDateTime.parse("2020-09-20T16:46:57.369+05:30")
                progressBloodGlucose = AppDatabase(it).getMonitorBloodGlucoseCatDao().getProgressDataBetweenDates(1,currentDate,sevendaysDate)

                for (i in progressBloodGlucose.indices){
                    if (progressBloodGlucose[i].day=="Sunday")
                        pointsValue[0] = progressBloodGlucose[i].value.toInt()
                    if (progressBloodGlucose[i].day=="Monday")
                        pointsValue[1] = progressBloodGlucose[i].value.toInt()
                    if (progressBloodGlucose[i].day=="Tuesday")
                        pointsValue[2] = progressBloodGlucose[i].value.toInt()
                    if (progressBloodGlucose[i].day=="Wednesday")
                        pointsValue[3] = progressBloodGlucose[i].value.toInt()
                    if (progressBloodGlucose[i].day=="Thursday")
                        pointsValue[4] = progressBloodGlucose[i].value.toInt()
                    if (progressBloodGlucose[i].day=="Friday")
                        pointsValue[5] = progressBloodGlucose[i].value.toInt()
                    if (progressBloodGlucose[i].day=="Saturday")
                        pointsValue[6] = progressBloodGlucose[i].value.toInt()
                }


                requireActivity().runOnUiThread(Runnable {
                    pb_graph.visibility = View.GONE
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
                breakfast_pb_graph.visibility = View.VISIBLE
                val currentDate = OffsetDateTime.parse("2020-09-19T16:47:39.824+05:30")
                val sevendaysDate = OffsetDateTime.parse("2020-09-22T16:46:57.369+05:30")
                progressBloodGlucoseBreakFast = AppDatabase(it).getMonitorBloodGlucoseCatDao().getProgressDataBetweenDates(2,currentDate,sevendaysDate)

                for (i in   progressBloodGlucoseBreakFast.indices){
                    if (  progressBloodGlucoseBreakFast[i].day=="Sunday"){
                        if (  progressBloodGlucoseBreakFast[i].title == "Before Breakfast")
                            breakFastPointsValue[0] =   progressBloodGlucoseBreakFast[i].value.toInt()
                        if (  progressBloodGlucoseBreakFast[i].title == "1 Hour After Breakfast")
                            breakFastPointsValue[1] =   progressBloodGlucoseBreakFast[i].value.toInt()
                        if (  progressBloodGlucoseBreakFast[i].title == "2 Hour After Breakfast")
                            breakFastPointsValue[2] =   progressBloodGlucoseBreakFast[i].value.toInt()
                    }
                    if (  progressBloodGlucoseBreakFast[i].day=="Monday") {
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
                    if (  progressBloodGlucoseBreakFast[i].day=="Tuesday") {
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
                    if (  progressBloodGlucoseBreakFast[i].day=="Wednesday") {
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
                    if (  progressBloodGlucoseBreakFast[i].day=="Thursday") {
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
                    if (  progressBloodGlucoseBreakFast[i].day=="Friday") {
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
                    if (  progressBloodGlucoseBreakFast[i].day=="Saturday") {
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
                    breakfast_pb_graph.visibility = View.GONE
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
                pb_lunch_graph.visibility = View.VISIBLE
                val currentDate = OffsetDateTime.parse("2020-09-16T00:54:51.356+05:30")
                val sevendaysDate = OffsetDateTime.parse("2020-09-22T16:46:57.369+05:30")
                progressBloodGlucoseLunch = AppDatabase(it).getMonitorBloodGlucoseCatDao().getProgressDataBetweenDates(3,currentDate,sevendaysDate)

                for (i in  progressBloodGlucoseLunch.indices){
                    if (progressBloodGlucoseLunch[i].day=="Sunday"){
                        if (progressBloodGlucoseLunch[i].title == "Before Lunch")
                            lunchPointsValue[0] = progressBloodGlucoseLunch[i].value.toInt()
                        if (progressBloodGlucoseLunch[i].title == "1 Hour After Lunch")
                            lunchPointsValue[1] = progressBloodGlucoseLunch[i].value.toInt()
                        if (progressBloodGlucoseLunch[i].title == "2 Hour After Lunch")
                            lunchPointsValue[2] = progressBloodGlucoseLunch[i].value.toInt()
                    }
                    if (progressBloodGlucoseLunch[i].day=="Monday") {
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
                    if (progressBloodGlucoseLunch[i].day=="Tuesday") {
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
                    if (progressBloodGlucoseLunch[i].day=="Wednesday") {
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
                    if (progressBloodGlucoseLunch[i].day=="Thursday") {
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
                    if (progressBloodGlucoseLunch[i].day=="Friday") {
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
                    if (progressBloodGlucoseLunch[i].day=="Saturday") {
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
            // LUNCH
            context?.let {
                pb_graph_dinner.visibility = View.VISIBLE
                val currentDate = OffsetDateTime.parse("2020-09-16T00:54:51.356+05:30")
                val sevendaysDate = OffsetDateTime.parse("2020-09-22T16:46:57.369+05:30")
                progressBloodGlucoseDinner = AppDatabase(it).getMonitorBloodGlucoseCatDao().getProgressDataBetweenDates(4,currentDate,sevendaysDate)

                for (i in  progressBloodGlucoseDinner.indices){
                    if (progressBloodGlucoseDinner[i].day=="Sunday"){
                        if (progressBloodGlucoseDinner[i].title == "Before Dinner")
                            dinnerPointsValue[0] = progressBloodGlucoseDinner[i].value.toInt()
                        if (progressBloodGlucoseDinner[i].title == "1 Hour After Dinner")
                            dinnerPointsValue[1] = progressBloodGlucoseDinner[i].value.toInt()
                        if (progressBloodGlucoseDinner[i].title == "2 Hour After Dinner")
                            dinnerPointsValue[2] = progressBloodGlucoseDinner[i].value.toInt()
                    }
                    if (progressBloodGlucoseDinner[i].day=="Monday") {
                        if (progressBloodGlucoseDinner[i].title == "Before Dinner")
                            dinnerPointsValue[3] =
                                progressBloodGlucoseDinner[i].value.toInt()
                        if (progressBloodGlucoseDinner[i].title == "1 Hour After Dinner")
                            dinnerPointsValue[4] =
                                progressBloodGlucoseDinner[i].value.toInt()
                        if (progressBloodGlucoseDinner[i].title == "2 Hour After Dinner")
                            dinnerPointsValue[5] =
                                progressBloodGlucoseDinner[i].value.toInt()
                    }
                    if (progressBloodGlucoseDinner[i].day=="Tuesday") {
                        if (progressBloodGlucoseDinner[i].title == "Before Dinner")
                            dinnerPointsValue[6] =
                                progressBloodGlucoseDinner[i].value.toInt()
                        if (progressBloodGlucoseDinner[i].title == "1 Hour After Dinner")
                            dinnerPointsValue[7] =
                                progressBloodGlucoseDinner[i].value.toInt()
                        if (progressBloodGlucoseDinner[i].title == "2 Hour After Dinner")
                            dinnerPointsValue[8] =
                                progressBloodGlucoseDinner[i].value.toInt()
                    }
                    if (progressBloodGlucoseDinner[i].day=="Wednesday") {
                        if (progressBloodGlucoseDinner[i].title == "Before Dinner")
                            dinnerPointsValue[9] =
                                progressBloodGlucoseDinner[i].value.toInt()
                        if (progressBloodGlucoseDinner[i].title == "1 Hour After Dinner")
                            dinnerPointsValue[10] =
                                progressBloodGlucoseDinner[i].value.toInt()
                        if (progressBloodGlucoseDinner[i].title == "2 Hour After Dinner")
                            dinnerPointsValue[11] =
                                progressBloodGlucoseDinner[i].value.toInt()
                    }
                    if (progressBloodGlucoseDinner[i].day=="Thursday") {
                        if (progressBloodGlucoseDinner[i].title == "Before Dinner")
                            dinnerPointsValue[12] =
                                progressBloodGlucoseDinner[i].value.toInt()
                        if (progressBloodGlucoseDinner[i].title == "1 Hour After Dinner")
                            dinnerPointsValue[13] =
                                progressBloodGlucoseDinner[i].value.toInt()
                        if (progressBloodGlucoseDinner[i].title == "2 Hour After Dinner")
                            dinnerPointsValue[14] =
                                progressBloodGlucoseDinner[i].value.toInt()
                    }
                    if (progressBloodGlucoseDinner[i].day=="Friday") {
                        if (progressBloodGlucoseDinner[i].title == "Before Dinner")
                            dinnerPointsValue[15] =
                                progressBloodGlucoseDinner[i].value.toInt()
                        if (progressBloodGlucoseDinner[i].title == "1 Hour After Dinner")
                            dinnerPointsValue[16] =
                                progressBloodGlucoseDinner[i].value.toInt()
                        if (progressBloodGlucoseDinner[i].title == "2 Hour After Dinner")
                            dinnerPointsValue[17] =
                                progressBloodGlucoseDinner[i].value.toInt()
                    }
                    if (progressBloodGlucoseDinner[i].day=="Saturday") {
                        if (progressBloodGlucoseDinner[i].title == "Before Dinner")
                            dinnerPointsValue[18] =
                                progressBloodGlucoseDinner[i].value.toInt()
                        if (progressBloodGlucoseDinner[i].title == "1 Hour After Dinner")
                            dinnerPointsValue[19] =
                                progressBloodGlucoseDinner[i].value.toInt()
                        if (progressBloodGlucoseDinner[i].title == "2 Hour After Dinner")
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
                pb_graph_bedtime.visibility = View.VISIBLE
                val currentDate = OffsetDateTime.parse("2020-09-16T00:54:51.356+05:30")
                val sevendaysDate = OffsetDateTime.parse("2020-09-22T16:46:57.369+05:30")
                progressBloodGlucoseBedTime = AppDatabase(it).getMonitorBloodGlucoseCatDao().getProgressDataBetweenDates(5,currentDate,sevendaysDate)

                for (i in progressBloodGlucoseBedTime.indices){
                    if (progressBloodGlucoseBedTime[i].day=="Sunday")
                        bedTimePointsValue[0] = progressBloodGlucoseBedTime[i].value.toInt()
                    if (progressBloodGlucoseBedTime[i].day=="Monday")
                        bedTimePointsValue[1] = progressBloodGlucoseBedTime[i].value.toInt()
                    if (progressBloodGlucoseBedTime[i].day=="Tuesday")
                        bedTimePointsValue[2] = progressBloodGlucoseBedTime[i].value.toInt()
                    if (progressBloodGlucoseBedTime[i].day=="Wednesday")
                        bedTimePointsValue[3] = progressBloodGlucoseBedTime[i].value.toInt()
                    if (progressBloodGlucoseBedTime[i].day=="Thursday")
                        bedTimePointsValue[4] = progressBloodGlucoseBedTime[i].value.toInt()
                    if (progressBloodGlucoseBedTime[i].day=="Friday")
                        bedTimePointsValue[5] = progressBloodGlucoseBedTime[i].value.toInt()
                    if (progressBloodGlucoseBedTime[i].day=="Saturday")
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