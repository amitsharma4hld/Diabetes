package com.s.diabetesfeeding.ui.home.fragment.diabetes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.InsulinToday
import com.s.diabetesfeeding.util.longToast
import com.s.diabetesfeeding.util.roundOffDecimal
import com.s.diabetesfeeding.util.shortToast
import com.s.diabetesfeeding.util.snackbar
import kotlinx.android.synthetic.main.fragment_insulin.*
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class InsulinFragment : Fragment() {

    var cir: Double = 0.0
    var isf: Double = 0.0
    val CIR_DEFAULT = 450
    var ISF_DEFAULT: Int = 0
    var tddInputValue : Double = 0.0
    var unitCalculated : Double = 0.0
    val InsulinScore: Int = 5

    lateinit var insulinToday: InsulinToday
    var isInsulinCalculated : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_insulin, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            context?.let {
                if (AppDatabase(it).getMonitorBloodGlucoseCatDao().getTodaysInsulin().isNotEmpty()){
                        for (i in AppDatabase(it).getMonitorBloodGlucoseCatDao().getTodaysInsulin().indices){
                            val date = AppDatabase(it).getMonitorBloodGlucoseCatDao().getTodaysInsulin()[i].date
                            val dateToString = date.toString().split("T")
                            var currentDate = getCurrentDateInString()
                            if (currentDate.equals(dateToString[0])) {
                                isInsulinCalculated = true
                                displayUnit(AppDatabase(it).getMonitorBloodGlucoseCatDao().getTodaysInsulin()[i].unit!!)
                                return@let
                            }
                        }
                    }
            }
        }
        mcv_done.setOnClickListener {
            val view:View = it
            viewLifecycleOwner.lifecycleScope.launch {
                context?.let {
                    val current_date = OffsetDateTime.now()
                    if (!isInsulinCalculated){
                        AppDatabase(it).getMonitorBloodGlucoseCatDao()
                            .saveTodaysInsulin(InsulinToday(0, unitCalculated, InsulinScore, current_date))
                        requireActivity().onBackPressed()

                    }else{
                        view.snackbar("Already Saved For Today")
                    }
                }
            }
        }

      //  isf = getISF(tddInputValue)
        getSelectedInsulinType()
        mcv_calculate.setOnClickListener{
            if (!et_tdd.text.isNullOrEmpty() || !et_total_carb.text.isNullOrEmpty() || !et_current_blood_glucose.text.isNullOrEmpty())
            {
                tddInputValue = et_tdd.text.toString().toDouble()
                cir = getCIR(tddInputValue)
                val totalCarb = et_total_carb.text.toString().toDouble()
                unitCalculated = totalCarb/cir
                displayUnit(unitCalculated)
            }else{
                it.snackbar("All the details required")
            }

        }
    }

    fun displayUnit(unit:Double){
        tv_display_unit.text = requireActivity().roundOffDecimal(unit).toString() + " Unit"
    }


    private fun getISF(tddInputValue: Double): Double {
        isf = ISF_DEFAULT / tddInputValue
        return isf
    }

    fun getCIR(tddInputValue:Double):Double{
        cir = CIR_DEFAULT / tddInputValue
        return cir
    }

    private fun getSelectedInsulinType() {
        val id: Int = radio_group.checkedRadioButtonId
        // val tddInputValue = et_tdd.text.toString().toDouble()
        if (id!=-1){
            val radio: RadioButton = requireActivity().findViewById(id)
            if (radio.text == "Read acting insulin"){
                ISF_DEFAULT = 1700
            }else{
                ISF_DEFAULT = 1500
            }
            requireActivity().shortToast(radio.text as String)
        }else{
            requireActivity().shortToast("Please select insulin type")
        }
    }
    private fun getCurrentDateInString():String{
        val currentDate: String = SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(
            java.util.Date())
        return currentDate
    }
}