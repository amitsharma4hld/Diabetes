package com.s.diabetesfeeding.ui.home.fragment.breastfeeding

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.ScoreTable
import com.s.diabetesfeeding.data.db.entities.WeightToday
import com.s.diabetesfeeding.data.db.entities.breastfeeding.BabyWeight
import com.s.diabetesfeeding.util.Coroutines
import com.s.diabetesfeeding.util.showSoftKeyboard
import com.s.diabetesfeeding.util.snackbar
import kotlinx.android.synthetic.main.fragment_baby_weight.*
import kotlinx.android.synthetic.main.fragment_baby_weight.et_digit_one
import kotlinx.android.synthetic.main.fragment_baby_weight.et_digit_two
import kotlinx.android.synthetic.main.fragment_baby_weight.et_digit_three
import kotlinx.android.synthetic.main.fragment_weight.*
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime
import java.text.SimpleDateFormat
import java.util.*


class BabyWeightFragment : Fragment() {
    private var isWeightCalculated = false
    private var weightCalculated : String? = null
    private val weightScore: Int = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        et_digit_one.requestFocus()
        et_digit_one.showSoftKeyboard(et_digit_one)

        viewLifecycleOwner.lifecycleScope.launch {
            context?.let {
                if (AppDatabase(it).getBreastFeedingDao().getBabyWeight().isNotEmpty()){
                    for (i in AppDatabase(it).getBreastFeedingDao().getBabyWeight().indices){
                        val date = AppDatabase(it).getBreastFeedingDao().getBabyWeight()[i].date
                        val dateToString = date.toString().split("T")
                        var currentDate = getCurrentDateInString()
                        if (currentDate.equals(dateToString[0])) {
                            isWeightCalculated = true
                            displayWeight(AppDatabase(it).getBreastFeedingDao().getBabyWeight()[i].weight!!)
                            return@let
                        }
                    }
                }
            }
        }
        done.setOnClickListener {
            val view:View = it
            if (et_digit_one.text.isNotEmpty() && et_digit_two.text.isNotEmpty() && et_digit_three.text.isNotEmpty()){
                weightCalculated = et_digit_one.text.toString() + et_digit_two.text.toString()+et_digit_three.text.toString()
                viewLifecycleOwner.lifecycleScope.launch {
                    context?.let {
                        val currentDate = OffsetDateTime.now()
                        if (!isWeightCalculated){
                            AppDatabase(it).getBreastFeedingDao()
                                .saveBabyWeight(BabyWeight(0, weightCalculated!!, weightScore, currentDate))
                            updateScore()
                            //requireActivity().onBackPressed()
                        }else{
                            view.snackbar("Already Saved For Today")
                        }
                    }
                }
            }else{
                it.snackbar("All field are required")
            }
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_baby_weight, container, false)
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
    fun updateScore() {
        Coroutines.io {
            context?.let {
                val currentDate = OffsetDateTime.now()
                AppDatabase(it).getHomeMenusDao().saveScores(ScoreTable(0, 0, 14, weightScore, currentDate))
                Log.d("AppDatabase : ", "Scores Saved ${AppDatabase(it).getHomeMenusDao().getAllScores().size} added")
            }
            (context as Activity).onBackPressed()
        }
    }
}