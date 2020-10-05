package com.s.diabetesfeeding.ui.home.fragment.obgyn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.obgynentities.PrentalVisitRecord
import com.s.diabetesfeeding.data.db.entities.obgynentities.ProgressPrentalVisit
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.ui.home.fragment.diabetes.DiabetesFragmentArgs
import com.s.diabetesfeeding.util.Coroutines
import com.s.diabetesfeeding.util.getDateFromOffsetDateTime
import kotlinx.android.synthetic.main.fragment_obgyn.*
import kotlinx.android.synthetic.main.fragment_progress_blood_glucose.*
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime


class ObgynFragment : Fragment() {


    lateinit var currentDate:OffsetDateTime
    private var progressVisitRecord:List<ProgressPrentalVisit> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_obgyn, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(!prefs.getOffsetDateTime().isNullOrEmpty()){
            currentDate = OffsetDateTime.parse(prefs.getOffsetDateTime())
            val value = getDateFromOffsetDateTime(currentDate)
            tv_today_date_obgyn.text= value
        }else{
            currentDate =  OffsetDateTime.now()
            val value = getDateFromOffsetDateTime(currentDate)
            tv_today_date_obgyn.text= value
        }

        var prentalVisitRecordList = listOf(
            PrentalVisitRecord(1,"Pre-pregnancy weight", prefs.getSavedPrePregnancyWeight()!!, "Lbs"),
            PrentalVisitRecord(2,"Height",prefs.getSavedHeight()!!,"Inch"),
            PrentalVisitRecord(3,"BMI","","Kg/m2"),
            PrentalVisitRecord(4,"Blood Pressure","","mmHg"),
            PrentalVisitRecord(5,"Fundal Height/EGA","","CM"),
            PrentalVisitRecord(6,"Fetal heart rate","","bpm"),
            PrentalVisitRecord(7,"Weight","","Lbs"),
            PrentalVisitRecord(8,"Glucose Level","","mg/dl"),
            PrentalVisitRecord(9,"Protein","","g/L"),
            PrentalVisitRecord(10,"Urine","",""),
            PrentalVisitRecord(11,"Significant Findings","",""),
            PrentalVisitRecord(12,"Instructions","","")
        )

        if (!prefs.getOffsetDateTime().isNullOrEmpty()) {
            val fromDate = OffsetDateTime.parse(prefs.getFromOffsetDateTime())
            val toDate = OffsetDateTime.parse(prefs.getOffsetDateTime())
            Coroutines.io {
                context?.let {
                    progressVisitRecord = emptyList()
                    progressVisitRecord =  AppDatabase(it).getObgynDao().getProgressDataBetweenDatesWithoutId(fromDate,toDate)
                }
                context?.let {
                    if (progressVisitRecord.isEmpty()) {
                        // prentalVisitRecordList = AppDatabase(it).getObgynDao().getAllPrentalVisitRecord()
                        AppDatabase(it).getObgynDao().saveAllPrentalVisitRecord(prentalVisitRecordList)
                    }else{
                        AppDatabase(it).getObgynDao().saveAllPrentalVisitRecord(prentalVisitRecordList)
                        for (i in progressVisitRecord.indices){
                            AppDatabase(it).getObgynDao()
                                .updatePrentalVisitColumn(
                                    progressVisitRecord[i].value,
                                    progressVisitRecord[i].measurementOf)
                        }
                    }
                }

            }
        }else{
            viewLifecycleOwner.lifecycleScope.launch {
                context?.let {
                    if (AppDatabase(it).getObgynDao().getAllPrentalVisitRecord().isNullOrEmpty()) {
                        AppDatabase(it).getObgynDao().saveAllPrentalVisitRecord(prentalVisitRecordList)
                    }
                }
            }
        }


        arguments?.let {
            username_obgy?.text = "Hello "+ DiabetesFragmentArgs.fromBundle(it).name +","
            tv_week_obyn?.text = DiabetesFragmentArgs.fromBundle(it).weekofpreg.toString()
            tv_doctorname_obgyn?.text = DiabetesFragmentArgs.fromBundle(it).doctorname
            tv_time_obgyn?.text = DiabetesFragmentArgs.fromBundle(it).time
        }
        mc_observation.setOnClickListener {
            val action = ObgynFragmentDirections.actionObgynFragmentToObservationFragment()
            Navigation.findNavController(it).navigate(action)
        }
        mc_counseling.setOnClickListener {
            val action = ObgynFragmentDirections.actionObgynFragmentToCounselingFragment()
            Navigation.findNavController(it).navigate(action)
        }
        mc_prenatal_visit.setOnClickListener {
            val action = ObgynFragmentDirections.actionObgynFragmentToPrentalVisitFragment()
            Navigation.findNavController(it).navigate(action)
        }
        mc_progress.setOnClickListener {
            val action = ObgynFragmentDirections.actionObgynFragmentToProgressObgynFragment()
            Navigation.findNavController(it).navigate(action)
        }

    }


}