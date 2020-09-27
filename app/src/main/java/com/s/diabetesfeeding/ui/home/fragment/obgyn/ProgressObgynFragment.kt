package com.s.diabetesfeeding.ui.home.fragment.obgyn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.obgynentities.ProgressPrentalVisit
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.util.Coroutines
import kotlinx.android.synthetic.main.progress_blood_presure.*
import kotlinx.android.synthetic.main.progress_fetal_heart_rate.*
import kotlinx.android.synthetic.main.progress_fundal_height.*
import kotlinx.android.synthetic.main.progress_glucose.*
import kotlinx.android.synthetic.main.progress_obgyn_bmi.*
import kotlinx.android.synthetic.main.progress_obgyn_bmi.bmi_V110
import kotlinx.android.synthetic.main.progress_obgyn_bmi.bmi_V111
import kotlinx.android.synthetic.main.progress_obgyn_bmi.bmi_V112
import kotlinx.android.synthetic.main.progress_obgyn_bmi.bmi_V17
import kotlinx.android.synthetic.main.progress_obgyn_bmi.bmi_V19
import kotlinx.android.synthetic.main.progress_protien.*
import kotlinx.android.synthetic.main.progress_urine.*
import kotlinx.android.synthetic.main.progress_weight.*
import org.threeten.bp.OffsetDateTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt


class ProgressObgynFragment : Fragment() {

    private var progressBMI:List<ProgressPrentalVisit> = ArrayList()
    private var progressBloodPresure:List<ProgressPrentalVisit> = ArrayList()
    private var progressFundalHeight:List<ProgressPrentalVisit> = ArrayList()
    private var progressFetalHeartRate:List<ProgressPrentalVisit> = ArrayList()
    private var progressGlucose:List<ProgressPrentalVisit> = ArrayList()
    private var progressWeight:List<ProgressPrentalVisit> = ArrayList()
    private var progressProtien:List<ProgressPrentalVisit> = ArrayList()
    private var progressUrine:List<ProgressPrentalVisit> = ArrayList()

    lateinit var currentDate: OffsetDateTime
    lateinit var sevendaysDate: OffsetDateTime

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_progress_obgyn, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(!prefs.getOffsetDateTime().isNullOrEmpty()){
            currentDate = OffsetDateTime.parse(prefs.getOffsetDateTime())
            sevendaysDate = currentDate.minusDays(7)
           // val value = getDateFromOffsetDateTime(currentDate)
            setAllProgressUI()
        }else{
            currentDate =  OffsetDateTime.now()
            sevendaysDate = currentDate.minusDays(7)
           // val value = getDateFromOffsetDateTime(currentDate)
            setAllProgressUI()
        }
    }

    private fun setAllProgressUI() {
        Coroutines.io {
            // BMI
            context?.let {
                progressBMI = AppDatabase(it).getObgynDao().getProgressDataBetweenDates(3,sevendaysDate,currentDate)
                val bmiPointsValue = IntArray(12)
                for (i in   progressBMI.indices){
                    if (  progressBMI[i].measurementOf == "BMI")
                    bmiPointsValue[i] =   progressBMI[i].value.toInt()
                }
                requireActivity().runOnUiThread(Runnable {
                    val bmiParams: RelativeLayout.LayoutParams = V11.layoutParams as RelativeLayout.LayoutParams
                    bmiParams.height = 200
                    V11.layoutParams = bmiParams
                    V11.layoutParams.height = dpToPx(bmiPointsValue[0])
                    V12.layoutParams.height = dpToPx(bmiPointsValue[1])
                    V13.layoutParams.height = dpToPx(bmiPointsValue[2])
                    V14.layoutParams.height = dpToPx(bmiPointsValue[3])
                    V15.layoutParams.height = dpToPx(bmiPointsValue[4])
                    V16.layoutParams.height = dpToPx(bmiPointsValue[5])
                    V17.layoutParams.height = dpToPx(bmiPointsValue[6])
                    bmi_V17.layoutParams.height = dpToPx(bmiPointsValue[7])
                    bmi_V19.layoutParams.height = dpToPx(bmiPointsValue[8])
                    bmi_V110.layoutParams.height = dpToPx(bmiPointsValue[9])
                    bmi_V111.layoutParams.height = dpToPx(bmiPointsValue[10])
                    bmi_V112.layoutParams.height = dpToPx(bmiPointsValue[11])

                })

            }
        }
        Coroutines.io {
            // Blood Pressure
            context?.let {
                progressBloodPresure = AppDatabase(it).getObgynDao().getProgressDataBetweenDates(4,sevendaysDate,currentDate)
                val bloodPresurePointsValue = IntArray(12)
                for (i in   progressBloodPresure.indices){
                    if (  progressBloodPresure[i].measurementOf == "Blood Pressure")
                        bloodPresurePointsValue[i] =   progressBloodPresure[i].value.toInt()
                }
                requireActivity().runOnUiThread(Runnable {
                    val viewProgress = arrayOf(blood_presureV11,blood_presureV12,blood_presureV13,blood_presureV14,
                        blood_presureV15,blood_presureV16,blood_presureV17,blood_presureV18,blood_presureV19,blood_presureV110,
                        blood_presureV111,blood_presureV12)
                    val viewBloodPresureHigh = arrayOf(week_one_high,week_two_high,week_three_high,week_four_high,
                        week_five_high,week_six_high,week_seven_high,week_eight_high,week_ten_high,week_eleven_high,week_twelve_high)
                    val bmiParams: RelativeLayout.LayoutParams = V11.layoutParams as RelativeLayout.LayoutParams
                    bmiParams.height = 10
                    blood_presureV11.layoutParams = bmiParams
                    for (i in progressBloodPresure.indices){
                        viewProgress[i].layoutParams.height = dpToPx(40)
                        viewBloodPresureHigh[i].text = progressBloodPresure[i].value
                    }
                })
            }
        }
        Coroutines.io {
            // Fundal Hight
            context?.let {
                progressFundalHeight = AppDatabase(it).getObgynDao().getProgressDataBetweenDates(5,sevendaysDate,currentDate)
                val fundalHightPointsValue = IntArray(12)
                for (i in   progressFundalHeight.indices){
                    if (  progressFundalHeight[i].measurementOf == "Fundal Height/EGA")
                        fundalHightPointsValue[i] =   progressFundalHeight[i].value.toInt()
                }
                requireActivity().runOnUiThread(Runnable {
                    val bmiParams: RelativeLayout.LayoutParams = fundal_height_V11.layoutParams as RelativeLayout.LayoutParams
                    bmiParams.height = 200
                    fundal_height_V11.layoutParams = bmiParams
                    fundal_height_V11.layoutParams.height = dpToPx(fundalHightPointsValue[0])
                    fundal_height_V12.layoutParams.height = dpToPx(fundalHightPointsValue[1])
                    fundal_height_V13.layoutParams.height = dpToPx(fundalHightPointsValue[2])
                    fundal_height_V14.layoutParams.height = dpToPx(fundalHightPointsValue[3])
                    fundal_height_V15.layoutParams.height = dpToPx(fundalHightPointsValue[4])
                    fundal_height_V16.layoutParams.height = dpToPx(fundalHightPointsValue[5])
                    fundal_height_V17.layoutParams.height = dpToPx(fundalHightPointsValue[6])
                    fundal_height_V18.layoutParams.height = dpToPx(fundalHightPointsValue[7])
                    fundal_height_V19.layoutParams.height = dpToPx(fundalHightPointsValue[8])
                    fundal_height_V110.layoutParams.height = dpToPx(fundalHightPointsValue[9])
                    fundal_height_V111.layoutParams.height = dpToPx(fundalHightPointsValue[10])
                    fundal_height_V112.layoutParams.height = dpToPx(fundalHightPointsValue[11])
                })

            }
        }
        Coroutines.io {
            // Fetal Heart Rate - Beats per minute
            context?.let {
                progressFetalHeartRate = AppDatabase(it).getObgynDao().getProgressDataBetweenDates(6,sevendaysDate,currentDate)
                val fetalHeartRatePointsValue = IntArray(12)
                for (i in   progressFetalHeartRate.indices){
                    if (  progressFetalHeartRate[i].measurementOf == "Fetal heart rate")
                        fetalHeartRatePointsValue[i] =   progressFetalHeartRate[i].value.toInt()
                }
                requireActivity().runOnUiThread(Runnable {
                    val bmiParams: RelativeLayout.LayoutParams = fetal_heart_rateV11.layoutParams as RelativeLayout.LayoutParams
                    bmiParams.height = 200
                    fetal_heart_rateV11.layoutParams = bmiParams
                    fetal_heart_rateV11.layoutParams.height = dpToPx(fetalHeartRatePointsValue[0])
                    fetal_heart_rateV12.layoutParams.height = dpToPx(fetalHeartRatePointsValue[1])
                    fetal_heart_rateV13.layoutParams.height = dpToPx(fetalHeartRatePointsValue[2])
                    fetal_heart_rateV14.layoutParams.height = dpToPx(fetalHeartRatePointsValue[3])
                    fetal_heart_rateV15.layoutParams.height = dpToPx(fetalHeartRatePointsValue[4])
                    fetal_heart_rateV16.layoutParams.height = dpToPx(fetalHeartRatePointsValue[5])
                    fetal_heart_rateV17.layoutParams.height = dpToPx(fetalHeartRatePointsValue[6])
                    fetal_heart_rateV18.layoutParams.height = dpToPx(fetalHeartRatePointsValue[7])
                    fetal_heart_rateV19.layoutParams.height = dpToPx(fetalHeartRatePointsValue[8])
                    fetal_heart_rateV110.layoutParams.height = dpToPx(fetalHeartRatePointsValue[9])
                    fetal_heart_rateV111.layoutParams.height = dpToPx(fetalHeartRatePointsValue[10])
                    fetal_heart_rateV112.layoutParams.height = dpToPx(fetalHeartRatePointsValue[11])
                })
            }
        }
        Coroutines.io {
            // Glucose
            context?.let {
                progressGlucose = AppDatabase(it).getObgynDao().getProgressDataBetweenDates(8,sevendaysDate,currentDate)
                val glucosePointsValue = IntArray(12)
                for (i in   progressGlucose.indices){
                    if (  progressGlucose[i].measurementOf == "Glucose Level")
                        glucosePointsValue[i] =   progressGlucose[i].value.toInt()
                }
                requireActivity().runOnUiThread(Runnable {
                    val bmiParams: RelativeLayout.LayoutParams = glucoseV11.layoutParams as RelativeLayout.LayoutParams
                    bmiParams.height = 100
                    glucoseV11.layoutParams = bmiParams
                    glucoseV11.layoutParams.height = dpToPx(glucosePointsValue[0])
                    glucoseV12.layoutParams.height = dpToPx(glucosePointsValue[1])
                    glucoseV13.layoutParams.height = dpToPx(glucosePointsValue[2])
                    glucoseV14.layoutParams.height = dpToPx(glucosePointsValue[3])
                    glucoseV15.layoutParams.height = dpToPx(glucosePointsValue[4])
                    glucoseV16.layoutParams.height = dpToPx(glucosePointsValue[5])
                    glucoseV17.layoutParams.height = dpToPx(glucosePointsValue[6])
                    glucoseV18.layoutParams.height = dpToPx(glucosePointsValue[7])
                    glucoseV19.layoutParams.height = dpToPx(glucosePointsValue[8])
                    glucoseV110.layoutParams.height = dpToPx(glucosePointsValue[9])
                    glucoseV111.layoutParams.height = dpToPx(glucosePointsValue[10])
                    glucoseV112.layoutParams.height = dpToPx(glucosePointsValue[11])
                })
            }
        }

        Coroutines.io {
            // Weight
            context?.let {
                progressWeight = AppDatabase(it).getObgynDao().getProgressDataBetweenDates(7,sevendaysDate,currentDate)
                val weightPointsValue = IntArray(12)
                for (i in   progressWeight.indices){
                    if (  progressWeight[i].measurementOf == "Weight")
                        weightPointsValue[i] =   progressWeight[i].value.toInt()
                }
                requireActivity().runOnUiThread(Runnable {
                    val bmiParams: RelativeLayout.LayoutParams = weightV11.layoutParams as RelativeLayout.LayoutParams
                    bmiParams.height = 100
                    weightV11.layoutParams = bmiParams
                    weightV11.layoutParams.height = dpToPx(weightPointsValue[0])
                    weightV12.layoutParams.height = dpToPx(weightPointsValue[1])
                    weightV13.layoutParams.height = dpToPx(weightPointsValue[2])
                    weightV14.layoutParams.height = dpToPx(weightPointsValue[3])
                    weightV15.layoutParams.height = dpToPx(weightPointsValue[4])
                    weightV16.layoutParams.height = dpToPx(weightPointsValue[5])
                    weightV17.layoutParams.height = dpToPx(weightPointsValue[6])
                    weightV18.layoutParams.height = dpToPx(weightPointsValue[7])
                    weightV19.layoutParams.height = dpToPx(weightPointsValue[8])
                    weightV110.layoutParams.height = dpToPx(weightPointsValue[9])
                    weightV111.layoutParams.height = dpToPx(weightPointsValue[10])
                    weightV112.layoutParams.height = dpToPx(weightPointsValue[11])
                })
            }
        }

        Coroutines.io {
            // Protien
            context?.let {
                progressProtien = AppDatabase(it).getObgynDao().getProgressDataBetweenDates(9,sevendaysDate,currentDate)
                val protienPointsValue = IntArray(12)
                for (i in   progressProtien.indices){
                    if (progressProtien[i].measurementOf == "Protein")
                        protienPointsValue[i] =   progressProtien[i].value.toInt()
                }
                requireActivity().runOnUiThread(Runnable {
                    val bmiParams: RelativeLayout.LayoutParams = protienV11.layoutParams as RelativeLayout.LayoutParams
                    bmiParams.height = 100
                    protienV11.layoutParams = bmiParams
                    protienV11.layoutParams.height = dpToPx(protienPointsValue[0])
                    protienV12.layoutParams.height = dpToPx(protienPointsValue[1])
                    protienV13.layoutParams.height = dpToPx(protienPointsValue[2])
                    protienV14.layoutParams.height = dpToPx(protienPointsValue[3])
                    protienV15.layoutParams.height = dpToPx(protienPointsValue[4])
                    protienV16.layoutParams.height = dpToPx(protienPointsValue[5])
                    protienV17.layoutParams.height = dpToPx(protienPointsValue[6])
                    protienV18.layoutParams.height = dpToPx(protienPointsValue[7])
                    protienV19.layoutParams.height = dpToPx(protienPointsValue[8])
                    protienV110.layoutParams.height = dpToPx(protienPointsValue[9])
                    protienV111.layoutParams.height = dpToPx(protienPointsValue[10])
                    protienV112.layoutParams.height = dpToPx(protienPointsValue[11])
                })
            }
        }
        Coroutines.io {
            // Urine
            context?.let {
                progressUrine = AppDatabase(it).getObgynDao().getProgressDataBetweenDates(10,sevendaysDate,currentDate)
                val urinePointsValue = IntArray(12)
                for (i in   progressUrine.indices){
                    if (progressUrine[i].measurementOf == "Protein")
                        urinePointsValue[i] =   progressUrine[i].value.toInt()
                }
                requireActivity().runOnUiThread(Runnable {
                    val bmiParams: RelativeLayout.LayoutParams = urineV11.layoutParams as RelativeLayout.LayoutParams
                    bmiParams.height = 100
                    urineV11.layoutParams = bmiParams
                    urineV11.layoutParams.height = dpToPx(urinePointsValue[0]*20)
                    urineV12.layoutParams.height = dpToPx(urinePointsValue[1]*20)
                    urineV13.layoutParams.height = dpToPx(urinePointsValue[2]*20)
                    urineV14.layoutParams.height = dpToPx(urinePointsValue[3]*20)
                    urineV15.layoutParams.height = dpToPx(urinePointsValue[4]*20)
                    urineV16.layoutParams.height = dpToPx(urinePointsValue[5]*20)
                    urineV17.layoutParams.height = dpToPx(urinePointsValue[6]*20)
                    urineV18.layoutParams.height = dpToPx(urinePointsValue[7]*20)
                    urineV19.layoutParams.height = dpToPx(urinePointsValue[8]*20)
                    urineV110.layoutParams.height = dpToPx(urinePointsValue[9]*20)
                    urineV111.layoutParams.height = dpToPx(urinePointsValue[10]*20)
                    urineV112.layoutParams.height = dpToPx(urinePointsValue[11]*20)
                })
            }
        }

    }

        private fun dpToPx(dp: Int): Int {
        val density: Float = this.resources.displayMetrics.density
        return (dp.toFloat() * density).roundToInt()
    }
}