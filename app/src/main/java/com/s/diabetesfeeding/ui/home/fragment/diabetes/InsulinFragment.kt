package com.s.diabetesfeeding.ui.home.fragment.diabetes

import android.app.Activity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.BloodGlucoseCategoryItem
import com.s.diabetesfeeding.data.db.entities.InsulinToday
import com.s.diabetesfeeding.data.db.entities.ScoreTable
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.ui.home.MonitorBloodGlucoseViewModel
import com.s.diabetesfeeding.ui.home.MonitorBloodGlucoseViewModelFactory
import com.s.diabetesfeeding.util.*
import kotlinx.android.synthetic.main.fragment_insulin.*
import kotlinx.android.synthetic.main.item_monitor_blood_glucose_child.view.*
import kotlinx.coroutines.launch
import okhttp3.internal.Internal.instance
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.threeten.bp.OffsetDateTime
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


class InsulinFragment : Fragment(), KodeinAware, MonitorGlucoseResponseListner {
    override val kodein by kodein()
    var cir: Float = 0.0F  //CIR(carbohydrate to insulin ratio)
    var mib: Float = 0.0F // Meal insulin bolus
    var isf: Float = 0.0F // Insulin Sensitivity Factor 
    val CIR_DEFAULT = 450  // CIR(carbohydrate to insulin ratio)
    val targetbloodValue = 80 // Target Blood Value
    var ISF_DEFAULT: Int = 0
    var tddInputValue : Double = 0.0
    var unitCalculated : Float = 0.0F
    val InsulinScore: Int = 5
    var InsulineType:String = ""
    private lateinit var viewModel : MonitorBloodGlucoseViewModel
    private val factory: MonitorBloodGlucoseViewModelFactory by instance()

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

        viewModel = ViewModelProvider(this,factory).get(MonitorBloodGlucoseViewModel::class.java)
        viewModel.monitorGlucoseResponseListner = this

        var currentDate:String = ""
        currentDate = if (!prefs.getOffsetDateTime().isNullOrEmpty()){
            val dateToString = prefs.getOffsetDateTime()!!.toString().split("T")
            dateToString[0]
        }else{
            getCurrentDateInString()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            context?.let {
                if (AppDatabase(it).getMonitorBloodGlucoseCatDao().getTodaysInsulin().isNotEmpty()){
                        for (i in AppDatabase(it).getMonitorBloodGlucoseCatDao().getTodaysInsulin().indices){
                            val date = AppDatabase(it).getMonitorBloodGlucoseCatDao().getTodaysInsulin()[i].date
                            val dateToString = date.toString().split("T")
                            if (currentDate == dateToString[0]) {
                                isInsulinCalculated = true
                                displayUnit(AppDatabase(it).getMonitorBloodGlucoseCatDao().getTodaysInsulin()[i].unit!!)
                            }
                        }
                    }
            }
        }
        if (prefs.getSavedIsPreviousDate()){
            mcv_done.isEnabled = false
        }
        mcv_done.setOnClickListener {
            val view:View = it
            viewLifecycleOwner.lifecycleScope.launch {
                context?.let {
                    val current_date = OffsetDateTime.now()
                    if (!prefs.getSavedDoctorId()?.isNotBlank()!!){
                        if (!isInsulinCalculated){
                            AppDatabase(it).getMonitorBloodGlucoseCatDao()
                                .saveTodaysInsulin(InsulinToday(0, unitCalculated.toDouble(), InsulinScore, current_date))
                            updateScore()
                            it.shortToast("Score Updated")
                        }else{
                            view.snackbar("Already Saved For Today")
                            requireActivity().onBackPressed()
                        }
                    }else{
                        view.snackbar("You can not edit patient details.")
                        requireActivity().onBackPressed()
                    }

                }
            }
        }
      //  isf = getISF(tddInputValue)
        mcv_calculate.setOnClickListener{
            if (!prefs.getSavedDoctorId()?.isNotBlank()!!){
                getSelectedInsulinType()
                if (prefs.getSavedIsPreviousDate()){
                    it.snackbar("Previous data can not edit")
                }else{
                    if (!et_tdd.text.isNullOrEmpty() || !et_total_carb.text.isNullOrEmpty() || !et_current_blood_glucose.text.isNullOrEmpty())
                    {
                        tddInputValue = et_tdd.text.toString().toDouble()
                        val currentbloodvalue = et_current_blood_glucose.text.toString().toDouble()
                        cir = getCIR(tddInputValue.toFloat())
                        val totalCarb = et_total_carb.text.toString().toDouble()
                        mib = totalCarb.toFloat()/ cir
                        // Calculation of Correction Dose
                        isf = getISF(tddInputValue.toFloat())
                        //  Correction dose =  // (Current blood sugar -Target blood sugar) / ISF  =  (160-90)/ 34  =   2.1 units
                        val correctionDoseValue = (currentbloodvalue - targetbloodValue ) / isf
                        unitCalculated = mib + correctionDoseValue.toFloat()
                        displayUnit(unitCalculated.toDouble())
                    }else{
                        it.snackbar("All the details required")
                    }
                }
            }else{
                it.snackbar("Can not edit patient details")
            }

        }
        if (prefs.getSavedDoctorId()?.isNotBlank()!!){
            et_tdd.inputType = InputType.TYPE_NULL
            et_total_carb.inputType = InputType.TYPE_NULL
            et_current_blood_glucose.inputType = InputType.TYPE_NULL
            et_tdd.isFocusable = false
            et_total_carb.isFocusable = false
            et_current_blood_glucose.isFocusable = false
        }
    }
    fun updateScore() {
        Coroutines.io {
            context?.let {
                val currentDate = OffsetDateTime.now()
                AppDatabase(it).getHomeMenusDao().saveScores(ScoreTable(0, 0, 2, InsulinScore, currentDate))
                Log.d("AppDatabase : ", "Scores Saved ${AppDatabase(it).getHomeMenusDao().getAllScores().size} added")
            }
            saveDataToServer(
                prefs.getSavedUserId().toString(),"insuline",InsulineType,unitCalculated.toString(),et_total_carb.text.toString(),
                et_current_blood_glucose.text.toString(),targetbloodValue.toString(), InsulinScore.toString())
        }
    }
    fun displayUnit(unit:Double){
        tv_display_unit.text = unit.roundToInt().toString()   + " Unit" // requireActivity().roundOffDecimal(unit).toString() + " Unit"
    }


    private fun getISF(tddInputValue: Float): Float {
        isf = ISF_DEFAULT / tddInputValue
        return isf
    }

    fun getCIR(tddInputValue:Float):Float{
        cir = CIR_DEFAULT / tddInputValue
        return cir
    }

    private fun getSelectedInsulinType() {
        val id: Int = radio_group.checkedRadioButtonId
        // val tddInputValue = et_tdd.text.toString().toDouble()
        if (id!=-1){
            val radio: RadioButton = requireActivity().findViewById(id)
            if (radio.text == "Rapid acting insulin"){
                InsulineType = "rapid-acting-insulin"
                ISF_DEFAULT = 1700
            }else{
                InsulineType = "short-acting-insulin"
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

    private fun saveDataToServer(userId:String,type:String,insulinType:String,totalDailyDose:String,
                                 totalCarb:String,currentBloodGlucose:String,targetBloodGlucose:String,totalPoints:String){
        if (!prefs.getSavedIsPreviousDate()) {
            viewModel.saveInsulinDataToServer(userId,type,insulinType,totalDailyDose,totalCarb,currentBloodGlucose,
                targetBloodGlucose,totalPoints)
        }
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
