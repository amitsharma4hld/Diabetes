package com.s.diabetesfeeding.ui.home.fragment.obgyn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.navigation.Navigation
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.ProgressBloodGlucose
import com.s.diabetesfeeding.data.db.entities.obgynentities.ProgressPrentalVisit
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.ui.home.fragment.diabetes.DiabetesFragmentDirections
import com.s.diabetesfeeding.util.Coroutines
import com.s.diabetesfeeding.util.getDateFromOffsetDateTime
import kotlinx.android.synthetic.main.fragment_diabetes.*
import kotlinx.android.synthetic.main.fragment_progress_blood_glucose.*
import kotlinx.android.synthetic.main.progress_breakfast.*
import kotlinx.android.synthetic.main.progress_urine.*
import kotlinx.android.synthetic.main.progress_wakeup.*
import org.threeten.bp.OffsetDateTime
import java.util.ArrayList
import kotlin.math.roundToInt


class ProgressObgynFragment : Fragment() {

    private var progressWeight:List<ProgressPrentalVisit> = ArrayList()
    private var progressBMI:List<ProgressPrentalVisit> = ArrayList()
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
        val breakfastParams: RelativeLayout.LayoutParams = V11.layoutParams as RelativeLayout.LayoutParams
        breakfastParams.height = 200
        urineV11.layoutParams = breakfastParams
        urineV11.layoutParams.height = dpToPx(3*20)

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
            // Wakup
            context?.let {
                progressBMI = AppDatabase(it).getObgynDao().getProgressDataBetweenDates(1,sevendaysDate,currentDate)
                progressBMI.size

            }
        }
    }

        fun dpToPx(dp: Int): Int {
        val density: Float = this.resources.displayMetrics.density
        return (dp.toFloat() * density).roundToInt()
    }
}