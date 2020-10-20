package com.s.diabetesfeeding.ui.home.fragment.obgyn

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.marginStart
import androidx.fragment.app.Fragment
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.BloodGlucoseCategoryItem
import com.s.diabetesfeeding.data.db.entities.ProgressBloodGlucose
import com.s.diabetesfeeding.data.db.entities.ScoreTable
import com.s.diabetesfeeding.data.db.entities.obgynentities.PrentalVisitRecord
import com.s.diabetesfeeding.data.db.entities.obgynentities.ProgressPrentalVisit
import com.s.diabetesfeeding.prefs
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
import kotlinx.android.synthetic.main.item_monitor_blood_glucose_child.view.*
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
            if (prentalVisitRecord?.measurementOf == "Height"){
                displayHeightValue(prentalVisitRecord?.value.toString())
            }
            else{
                if (prentalVisitRecord?.measurementOf == "Blood Pressure"){
                    displayBloodPressureValue(prentalVisitRecord?.value.toString())
                }
                else{
                    if (prentalVisitRecord?.measurementOf == "Significant Findings" ||
                        prentalVisitRecord?.measurementOf == "Instructions") {
                        et_text_digit_one.setText(prentalVisitRecord?.value.toString())
                        et_text_digit_one.isFocusable = false
                    }
                    else{
                        displayValue(prentalVisitRecord?.value.toString())
                    }
                }
            }

        }
        if (!prentalVisitRecord?.unit .isNullOrEmpty()){
            tv_unit.text = prentalVisitRecord?.unit
        }
        if (!prentalVisitRecord?.measurementOf.isNullOrEmpty()){
            if (prentalVisitRecord?.measurementOf == "Height"){
                et_digit_two.setText(getString(R.string.apostrophe))
                et_digit_two.isFocusable = false
                et_digit_one.requestFocus()
            }
            if (prentalVisitRecord?.measurementOf == "BMI"){
                et_digit_one.setText("0")
                et_digit_one.isFocusable = false
                mcv_one.visibility = View.GONE
                val param = mcv_three.layoutParams as ViewGroup.MarginLayoutParams
                param.setMargins(16,2,2,2)
                mcv_three.layoutParams = param
                et_digit_two.isFocusable = true
                et_digit_two.requestFocus()
            }
            if (prentalVisitRecord?.measurementOf == "Blood Pressure"){
                et_digit_two.setText(getString(R.string.apostrophe))
                et_digit_two.isFocusable = false
                mcv_two.visibility = View.GONE
                val param = mcv_three.layoutParams as ViewGroup.MarginLayoutParams
                param.setMargins(16,2,2,2)
                mcv_three.layoutParams = param
                et_digit_one.requestFocus()
                et_digit_one.setHint(
                    Html.fromHtml(
                        "<small><small><small>"
                                + getString(R.string.blood_presure_type_systolic)
                                + "</small></small></small>"));
                et_digit_three.setHint(
                    Html.fromHtml(
                        "<small><small><small>"
                                + getString(R.string.blood_presure_type_diastolic)
                                + "</small></small></small>"));
                et_digit_one.filters = arrayOf(InputFilter.LengthFilter(3))
                et_digit_three.filters = arrayOf(InputFilter.LengthFilter(3))
            }
            if (prentalVisitRecord?.measurementOf == "Fundal Height/EGA"){
                et_digit_one.setText("0")
                et_digit_one.isFocusable = false
                mcv_one.visibility = View.GONE
                val param = mcv_three.layoutParams as ViewGroup.MarginLayoutParams
                param.setMargins(16,2,2,2)
                mcv_three.layoutParams = param
                et_digit_two.isFocusable = true
                et_digit_two.requestFocus()
            }
            if (prentalVisitRecord?.measurementOf == "Urine"){
                et_digit_one.setText("0")
                et_digit_two.setText("0")
                et_digit_one.isFocusable = false
                et_digit_two.isFocusable = false
                et_digit_three.requestFocus()
            }
            if (prentalVisitRecord?.measurementOf == "Significant Findings" || prentalVisitRecord?.measurementOf == "Recommendations" ){
                tv_data_entry_title.text = "Text Entry"
                ll_data_entry.visibility = View.GONE
                mcv_text_one.visibility = View.VISIBLE

                if (!prentalVisitRecord?.value.isNullOrEmpty()){

                }
            }

        }
        username.setOnClickListener {
            requireActivity().onBackPressed()
        }
        mcv_weight_done.setOnClickListener {
            if (!prefs.getSavedDoctorId()?.isNotBlank()!!) {
                if (prefs.getSavedIsPreviousDate()) {
                    it.snackbar("Previous data can not be edited")
                    return@setOnClickListener
                } else {
                    if (!prentalVisitRecord?.value.isNullOrEmpty()) {
                        it.snackbar("Data is already saved")
                        (context as Activity).onBackPressed()
                    } else {
                        if (et_text_digit_one.text.isNotBlank()) {
                            prentalVisitRecord?.value = et_text_digit_one.text.toString()
                            prentalVisitRecord.let {
                                update(prentalVisitRecord!!)
                                updateProgressData(prentalVisitRecord!!)
                                updateScore()
                            }
                        } else
                            if (et_digit_one.text.isNotBlank() && et_digit_two.text.isNotBlank() && et_digit_three.text.isNotBlank()) {
                                prentalVisitRecord?.value =
                                    et_digit_one.text.toString() + et_digit_two.text.toString() + et_digit_three.text.toString()
                                if (prentalVisitRecord?.measurementOf == "Pre-pregnancy weight") {
                                    if (prefs.getSavedPrePregnancyWeight().isNullOrEmpty()) {
                                        prentalVisitRecord?.value?.let { it1 ->
                                            prefs.savePrePregnancyWeight(
                                                it1
                                            )
                                        }
                                        update(prentalVisitRecord!!)
                                        updateProgressData(prentalVisitRecord!!)
                                        updateScore()
                                    } else {
                                        it.snackbar("Pre-pregnancy weight can not be edited")
                                    }
                                } else {
                                    if (prentalVisitRecord?.measurementOf == "Height") {
                                        if (prefs.getSavedHeight().isNullOrEmpty()) {
                                            prentalVisitRecord?.value?.let { it1 ->
                                                prefs.saveHeight(
                                                    it1
                                                )
                                            }
                                            update(prentalVisitRecord!!)
                                            updateProgressData(prentalVisitRecord!!)
                                            updateScore()
                                        } else {
                                            it.snackbar("Height can not be edited")
                                        }
                                    } else {
                                        prentalVisitRecord.let {
                                            update(prentalVisitRecord!!)
                                            updateProgressData(prentalVisitRecord!!)
                                            updateScore()
                                        }
                                    }
                                }

                            } else {
                                mcv_weight_done.snackbar("Fields can not be empty")
                            }
                    }
                }
            }
            else {
                it.snackbar("Can not edit patient details")
                requireActivity().onBackPressed()
            }

        }

        et_digit_one.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }
            override fun afterTextChanged(editable: Editable) {
                if (prentalVisitRecord?.measurementOf == "Blood Pressure"){
                    if(editable.toString().length == 3)
                    {
                        et_digit_one.clearFocus()
                        et_digit_two.requestFocus()
                        et_digit_two.isCursorVisible = true
                    }
                }else{
                    if(editable.toString().length == 1)
                    {
                        et_digit_one.clearFocus()
                        et_digit_two.requestFocus()
                        et_digit_two.isCursorVisible = true
                    }
                }

            }
        })
        et_digit_two.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }
            override fun afterTextChanged(editable: Editable) {
                if (prentalVisitRecord?.measurementOf == "Blood Pressure"){
                    if(editable.toString().length == 3)
                    {
                        et_digit_two.clearFocus()
                        et_digit_three.requestFocus()
                        et_digit_three.isCursorVisible = true
                    }
                }else{
                    if(editable.toString().length == 1)
                    {
                        et_digit_two.clearFocus()
                        et_digit_three.requestFocus()
                        et_digit_three.isCursorVisible = true
                    }
                }

            }
        })
        //et_digit_one.requestFocus()
        // show the keyboard if has focus
        //showSoftKeyboard(et_digit_one)
    }

    private fun displayHeightValue(date: String) {
        val dateToString = date.split(getString(R.string.apostrophe))
        et_digit_one.setText(dateToString[0])
        et_digit_two.setText(getString(R.string.apostrophe))
        et_digit_three.setText(dateToString[1])
        et_digit_one.isFocusable = false
        et_digit_three.isFocusable = false
    }
    private fun displayBloodPressureValue(date: String) {
        et_digit_one.filters = arrayOf(InputFilter.LengthFilter(3))
        et_digit_three.filters = arrayOf(InputFilter.LengthFilter(3))
        val dateToString = date.split(getString(R.string.apostrophe))
        et_digit_one.setText(dateToString[0])
        et_digit_two.setText(getString(R.string.apostrophe))
        et_digit_three.setText(dateToString[1])
        et_digit_one.isFocusable = false
        et_digit_three.isFocusable = false
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
        et_digit_one.isFocusable = false
        et_digit_two.isFocusable = false
        et_digit_three.isFocusable = false

    }

    fun update(prentalVisitRecord: PrentalVisitRecord) {
        Coroutines.io {
            context.let {
                AppDatabase(requireContext()).getObgynDao().updatePrentalVisitRecord(prentalVisitRecord)
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
            }
        }
    }
    fun updateScore() {
        Coroutines.io {
            context?.let {
                val currentDate = OffsetDateTime.now()
                AppDatabase(it).getHomeMenusDao().saveScores(ScoreTable(0, 0, 8, prentalInputScore, currentDate))
            }
            (context as Activity).onBackPressed()
        }
    }

}