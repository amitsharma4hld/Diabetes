package com.s.diabetesfeeding.ui.home.fragment.obgyn

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.ScoreTable
import com.s.diabetesfeeding.data.db.entities.obgynentities.Observation
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.ui.adapter.ObeservationAdapter
import com.s.diabetesfeeding.util.*
import kotlinx.android.synthetic.main.fragment_observation.*
import kotlinx.android.synthetic.main.fragment_symptoms.recyclerViewMonitorBloodGlucose
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ObservationFragment : Fragment() {

    lateinit var currentDate:OffsetDateTime
    var isObservationScored:Boolean = false
    val observationScore: Int = 20
    var observationList: List<Observation> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(!prefs.getOffsetDateTime().isNullOrEmpty()){
            currentDate = OffsetDateTime.parse(prefs.getOffsetDateTime())
            val value = getDateFromOffsetDateTime(currentDate)
            tv_display_date.text= value
        }else{
            currentDate =  OffsetDateTime.now()
            val value = getDateFromOffsetDateTime(currentDate)
            tv_display_date.text= value
        }
        var currentDate:String = ""
        currentDate = if (!prefs.getOffsetDateTime().isNullOrEmpty()){
            val dateToString = prefs.getOffsetDateTime()!!.toString().split("T")
            dateToString[0]
        }else{
            getCurrentDateInString()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            context?.let {
                observationList =  AppDatabase(requireActivity().applicationContext).getObgynDao().getAllObservation()
                showObservation(observationList)
            }
            context?.let {
                if (AppDatabase(it).getHomeMenusDao().getScoreByCategory(6).isNotEmpty()){
                    for (i in AppDatabase(it).getHomeMenusDao().getScoreByCategory(6).indices){
                        val date = AppDatabase(it).getHomeMenusDao().getScoreByCategory(6)[i].date_time
                        val dateToString = date.toString().split("T")
                        if (currentDate.equals(dateToString[0])) {
                            isObservationScored = true
                            return@let
                        }
                    }
                }
            }

        }
        mcv_observation_done.setOnClickListener {
            if (!prefs.getSavedDoctorId()?.isNotBlank()!!){
                if (prefs.getSavedIsPreviousDate()) {
                    it.snackbar("Previous data can not be edited")
                }else{
                    if (!isObservationScored){
                        updateScore()
                        requireActivity().shortToast("Score Updated")
                    }else{
                        view?.snackbar("Already Saved For Today")
                        requireActivity().onBackPressed()
                    }
                }
            }else{
                it.snackbar("Can not edit patient details")
                requireActivity().onBackPressed()
            }

        }
    }

    fun updateScore() {
        Coroutines.io {
            context?.let {
                val currentDate = OffsetDateTime.now()
                AppDatabase(it).getHomeMenusDao().saveScores(ScoreTable(0, 0, 6, observationScore, currentDate))
            }
            (context as Activity).onBackPressed()
        }
    }

    private fun showObservation(observations: List<Observation>) {
        recyclerViewMonitorBloodGlucose.layoutManager = LinearLayoutManager(activity)
        recyclerViewMonitorBloodGlucose.adapter =
            ObeservationAdapter(
                requireActivity(),
                observations
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_observation, container, false)
    }

    private fun getCurrentDateInString():String{
        val currentDate: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
            java.util.Date())
        return currentDate
    }

}