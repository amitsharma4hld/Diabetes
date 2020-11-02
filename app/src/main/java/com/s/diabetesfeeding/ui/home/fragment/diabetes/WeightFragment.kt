package com.s.diabetesfeeding.ui.home.fragment.diabetes

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.InsulinToday
import com.s.diabetesfeeding.data.db.entities.ScoreTable
import com.s.diabetesfeeding.data.db.entities.WeightToday
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.ui.home.MonitorBloodGlucoseViewModel
import com.s.diabetesfeeding.ui.home.MonitorBloodGlucoseViewModelFactory
import com.s.diabetesfeeding.util.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_insulin.*
import kotlinx.android.synthetic.main.fragment_pre_pregnancy_input.*
import kotlinx.android.synthetic.main.fragment_weight.*
import kotlinx.android.synthetic.main.fragment_weight.et_digit_one
import kotlinx.android.synthetic.main.fragment_weight.et_digit_three
import kotlinx.android.synthetic.main.fragment_weight.et_digit_two
import kotlinx.android.synthetic.main.fragment_weight.mcv_weight_done
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.threeten.bp.OffsetDateTime
import java.text.SimpleDateFormat
import java.util.*


class WeightFragment : Fragment(), KodeinAware, MonitorGlucoseResponseListner {

    private lateinit var viewModel : MonitorBloodGlucoseViewModel
    override val kodein by kodein()
    private val factory: MonitorBloodGlucoseViewModelFactory by instance()

    private var isWeightCalculated = false
    private var weightCalculated :String? = null
    private val weightScore: Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this,factory).get(MonitorBloodGlucoseViewModel::class.java)
        viewModel.monitorGlucoseResponseListner = this

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
            if (!prefs.getSavedDoctorId()?.isNotBlank()!!){
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
            }else{
                it.snackbar("Can not edit patient detail")
            }
        }
        et_digit_one.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }
            override fun afterTextChanged(editable: Editable) {
                    if(editable.toString().length == 1)
                    {
                        et_digit_one.clearFocus()
                        et_digit_two.requestFocus()
                        et_digit_two.isCursorVisible = true
                    }
            }
        })

        et_digit_two.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }
            override fun afterTextChanged(editable: Editable) {
                    if(editable.toString().length == 1)
                    {
                        et_digit_two.clearFocus()
                        et_digit_three.requestFocus()
                        et_digit_three.isCursorVisible = true
                    }
            }
        })

        if (prefs.getSavedDoctorId()?.isNotBlank()!!){
            et_digit_one.inputType = InputType.TYPE_NULL
            et_digit_two.inputType = InputType.TYPE_NULL
            et_digit_three.inputType = InputType.TYPE_NULL
            et_digit_one.isFocusable = false
            et_digit_two.isFocusable = false
            et_digit_three.isFocusable = false
        }
    }
    fun updateScore() {
        Coroutines.io {
            context?.let {
                val currentDate = OffsetDateTime.now()
                AppDatabase(it).getHomeMenusDao().saveScores(ScoreTable(0, 0, 3, weightScore, currentDate))
            }
            viewModel.saveBloodGlucoseWeightDataToServer(prefs.getSavedUserId().toString(),"body-weight",weightCalculated.toString())

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


    override fun onStarted() {
        requireActivity().shortToast("Syncing to server")
    }

    override fun onSuccess(message: String) {
        requireActivity().shortToast(message)
        requireActivity().onBackPressed()
    }

    override fun onFailure(message: String) {
        requireActivity().shortToast(message)
        requireActivity().onBackPressed()
    }

}