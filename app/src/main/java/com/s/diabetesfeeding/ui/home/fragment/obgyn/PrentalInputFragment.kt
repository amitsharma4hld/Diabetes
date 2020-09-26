package com.s.diabetesfeeding.ui.home.fragment.obgyn

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.BloodGlucoseCategoryItem
import com.s.diabetesfeeding.data.db.entities.ProgressBloodGlucose
import com.s.diabetesfeeding.data.db.entities.ScoreTable
import com.s.diabetesfeeding.data.db.entities.obgynentities.PrentalVisitRecord
import com.s.diabetesfeeding.data.db.entities.obgynentities.ProgressPrentalVisit
import com.s.diabetesfeeding.util.Coroutines
import com.s.diabetesfeeding.util.getDayFromOffsetDateTime
import com.s.diabetesfeeding.util.snackbar
import kotlinx.android.synthetic.main.fragment_pre_pregnancy_input.*
import kotlinx.android.synthetic.main.fragment_pre_pregnancy_input.et_digit_one
import kotlinx.android.synthetic.main.fragment_pre_pregnancy_input.et_digit_three
import kotlinx.android.synthetic.main.fragment_pre_pregnancy_input.et_digit_two
import kotlinx.android.synthetic.main.fragment_pre_pregnancy_input.mcv_weight_done
import kotlinx.android.synthetic.main.fragment_pre_pregnancy_input.username
import kotlinx.android.synthetic.main.fragment_weight.*
import org.threeten.bp.OffsetDateTime


class PrentalInputFragment : Fragment() {

    var prentalVisitRecord : PrentalVisitRecord? = null
    val prentalInputScore:Int = 5
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pre_pregnancy_input, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let {
            prentalVisitRecord = PrentalInputFragmentArgs.fromBundle(it).prentalVisitRecord
        }
        if (!prentalVisitRecord?.value.isNullOrEmpty()){
            displayValue(prentalVisitRecord?.value.toString())
            tv_unit.text = prentalVisitRecord?.unit
        }
        username.setOnClickListener {
            requireActivity().onBackPressed()
        }
        mcv_weight_done.setOnClickListener {
            if (et_digit_one.text.isNotBlank() && et_digit_two.text.isNotBlank() && et_digit_three.text.isNotBlank()) {
                prentalVisitRecord?.value = et_digit_one.text.toString() + et_digit_two.text.toString()+ et_digit_three.text.toString()
                prentalVisitRecord.let {
                    update(prentalVisitRecord!!)
                    updateProgressData(prentalVisitRecord!!)
                    updateScore()
                }
            }else {
                mcv_weight_done.snackbar("Fields can not be empty")
            }

        }
        et_digit_one.requestFocus()
        // show the keyboard if has focus
        //showSoftKeyboard(et_digit_one)
    }
    private fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }

    private fun displayValue(weight:String){
        val numbers = weight.map { it.toString().toInt() }
        for (i in numbers.indices){
            if (i == 0){
                numbers[0].let {
                    et_digit_one.setText(numbers[0].toString())
                }
            }
           if (i == 1){
               numbers[1].let {
                   et_digit_two.setText(numbers[1].toString())
               }
           }
            if (i == 2){
                numbers[2].let {
                    et_digit_three.setText(numbers[2].toString())
                }
            }
        }

    }

    fun update(prentalVisitRecord: PrentalVisitRecord) {
        Coroutines.io {
            context.let {
                AppDatabase(requireContext()).getObgynDao().updatePrentalVisitRecord(prentalVisitRecord)
                Log.d("APPDATABASE : ","Update value is ${prentalVisitRecord.value}")
            }
        }
    }
    private fun updateProgressData(prentalVisitRecord: PrentalVisitRecord ) {
        Coroutines.io {
            context.let {
                val currentDate = OffsetDateTime.now()
                val progressData =  ProgressPrentalVisit(0,prentalVisitRecord.id,prentalVisitRecord.measurementOf,
                    prentalVisitRecord.value,prentalVisitRecord.unit, currentDate,currentDate.dayOfWeek.name
                )
                AppDatabase(requireContext()).getObgynDao().saveProgressPrentalVisit(progressData)
                Log.d("APPDATABASE : ","progressData value is ${progressData.dateTime}")
            }
        }
    }
    fun updateScore() {
        Coroutines.io {
            context?.let {
                val currentDate = OffsetDateTime.now()
                AppDatabase(it).getHomeMenusDao().saveScores(ScoreTable(0, 0, 9, prentalInputScore, currentDate))
                Log.d("AppDatabase : ", "Scores Saved ${AppDatabase(it).getHomeMenusDao().getAllScores().size} added")
            }
            (context as Activity).onBackPressed()
        }
    }

}