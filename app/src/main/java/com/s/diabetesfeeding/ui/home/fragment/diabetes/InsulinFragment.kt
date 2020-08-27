package com.s.diabetesfeeding.ui.home.fragment.diabetes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.util.longToast
import com.s.diabetesfeeding.util.shortToast
import kotlinx.android.synthetic.main.fragment_insulin.*


class InsulinFragment : Fragment() {

     // carbohydrate-to-insulin ratio (CIR)
    // Insulin Sensitivity Factor (ISF)
    var cir: Double = 0.0
    var isf: Double = 0.0
    val CIR_DEFAULT = 450
    var ISF_DEFAULT: Int = 0
    var tddInputValue : Double = 0.0
    var unitCalculated : Double = 0.0
    // val percentage = bytesReceived * 100 / fizeSize

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insulin, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mcv_done.setOnClickListener {
            requireActivity().onBackPressed()
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
                tv_display_unit.text = unitCalculated.toString() + " Unit"
            }else{
                requireActivity().longToast("All the details required")
            }

        }
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
}