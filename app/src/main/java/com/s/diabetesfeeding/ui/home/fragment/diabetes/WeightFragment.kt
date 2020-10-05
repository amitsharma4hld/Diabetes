package com.s.diabetesfeeding.ui.home.fragment.diabetes

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.lifecycleScope
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.InsulinToday
import com.s.diabetesfeeding.data.db.entities.ScoreTable
import com.s.diabetesfeeding.data.db.entities.WeightToday
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.util.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_insulin.*
import kotlinx.android.synthetic.main.fragment_weight.*
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime
import java.text.SimpleDateFormat
import java.util.*


class WeightFragment : Fragment() {

    private var isWeightCalculated = false
    private var weightCalculated :String? = null
    private val weightScore: Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        et_digit_one.requestFocus()
        // show the keyboard if has focus
        // showSoftKeyboard(et_digit_one)
        var currentDate:String = ""
        currentDate = if (!prefs.getOffsetDateTime().isNullOrEmpty()){
            val dateToString = prefs.getOffsetDateTime()!!.toString().split("T")
            dateToString[0]
        }else{
            getCurrentDateInString()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            context?.let {
                if (AppDatabase(it).getMonitorBloodGlucoseCatDao().getTodaysWeight().isNotEmpty()){
                    for (i in AppDatabase(it).getMonitorBloodGlucoseCatDao().getTodaysWeight().indices){
                        val date = AppDatabase(it).getMonitorBloodGlucoseCatDao().getTodaysWeight()[i].date
                        val dateToString = date.toString().split("T")
                        if (currentDate == dateToString[0]) {
                            isWeightCalculated = true
                            displayWeight(AppDatabase(it).getMonitorBloodGlucoseCatDao().getTodaysWeight()[i].weight!!)
                            return@let
                        }
                    }
                }
            }
        }

        mcv_weight_done.setOnClickListener { it ->
            if (prefs.getSavedIsPreviousDate()) {
                it.snackbar("Previous data can not edit")
            } else {
                val view: View = it
                if (et_digit_one.text.isNotEmpty() && et_digit_two.text.isNotEmpty() && et_digit_three.text.isNotEmpty()) {
                    weightCalculated =
                        et_digit_one.text.toString() + et_digit_two.text.toString() + et_digit_three.text.toString()
                    viewLifecycleOwner.lifecycleScope.launch {
                        context?.let {
                            val currentDate = OffsetDateTime.now()
                            if (!isWeightCalculated) {
                                AppDatabase(it).getMonitorBloodGlucoseCatDao()
                                    .saveTodaysWeight(
                                        WeightToday(
                                            0,
                                            weightCalculated!!,
                                            weightScore,
                                            currentDate
                                        )
                                    )
                                updateScore()
                                requireActivity().shortToast("Score Updated")
                            } else {
                                view.snackbar("Already Saved For Today")
                                requireActivity().onBackPressed()
                            }
                        }
                    }
                } else {
                    it.snackbar("All field are required")
                }
            }
        }
    }
    fun updateScore() {
        Coroutines.io {
            context?.let {
                val currentDate = OffsetDateTime.now()
                AppDatabase(it).getHomeMenusDao().saveScores(ScoreTable(0, 0, 3, weightScore, currentDate))
            }
            (context as Activity).onBackPressed()
        }
    }
    private fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            // here is one more tricky issue
            // imm.showSoftInputMethod doesn't work well
            // and imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0) doesn't work well for all cases too
            imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weight, container, false)
    }

    private fun getCurrentDateInString():String{
        val currentDate: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        return currentDate
    }

    fun displayWeight(weight:String){
        val numbers = weight.map { it.toString().toInt() }
        et_digit_one.setText(numbers[0].toString())
        et_digit_two.setText(numbers[1].toString())
        et_digit_three.setText(numbers[2].toString())
    }

}