package com.s.diabetesfeeding.ui.home.fragment.obgyn

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
import com.s.diabetesfeeding.data.db.entities.obgynentities.PrentalVisitRecord
import com.s.diabetesfeeding.util.Coroutines
import kotlinx.android.synthetic.main.fragment_pre_pregnancy_input.*
import kotlinx.android.synthetic.main.fragment_pre_pregnancy_input.et_digit_one
import kotlinx.android.synthetic.main.fragment_pre_pregnancy_input.et_digit_three
import kotlinx.android.synthetic.main.fragment_pre_pregnancy_input.et_digit_two
import kotlinx.android.synthetic.main.fragment_pre_pregnancy_input.mcv_weight_done
import kotlinx.android.synthetic.main.fragment_pre_pregnancy_input.username
import kotlinx.android.synthetic.main.fragment_weight.*


class PrePregnancyInputFragment : Fragment() {

    var prentalVisitRecord : PrentalVisitRecord? = null

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
            prentalVisitRecord = PrePregnancyInputFragmentArgs.fromBundle(it).prentalVisitRecord
        }
        if (!prentalVisitRecord?.value.isNullOrEmpty()){
            displayValue(prentalVisitRecord?.value.toString())
        }
        username.setOnClickListener {
            requireActivity().onBackPressed()
        }
        mcv_weight_done.setOnClickListener {
            prentalVisitRecord?.value = et_digit_one.text.toString() + et_digit_two.text.toString()+et_digit_three.text.toString()
            update(prentalVisitRecord!!)
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

    fun displayValue(weight:String){
        val numbers = weight.map { it.toString().toInt() }
        et_digit_one.setText(numbers[0].toString())
        et_digit_two.setText(numbers[1].toString())
        et_digit_three.setText(numbers[2].toString())
    }

    fun update(prentalVisitRecord: PrentalVisitRecord) {
        Coroutines.io {
            context.let {
                AppDatabase(requireContext()).getObgynDao().updatePrentalVisitRecord(prentalVisitRecord)
                Log.d("APPDATABASE : ","Update value is ${prentalVisitRecord.value}")
                requireActivity().onBackPressed()
            }
        }
    }

}