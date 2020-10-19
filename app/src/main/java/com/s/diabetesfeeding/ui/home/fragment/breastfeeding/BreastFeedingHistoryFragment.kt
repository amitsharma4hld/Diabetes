package com.s.diabetesfeeding.ui.home.fragment.breastfeeding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.*
import com.s.diabetesfeeding.data.db.entities.breastfeeding.ProgressBabyPoopDiaperChange
import com.s.diabetesfeeding.data.db.entities.breastfeeding.ProgressBreastFeeding
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.ui.CellClickListener
import com.s.diabetesfeeding.ui.adapter.BreastFeedHistoryMainAdapter
import com.s.diabetesfeeding.util.Coroutines
import com.s.diabetesfeeding.util.getDateInMMYYYYOffsetDateTime
import com.s.diabetesfeeding.util.logger.Log
import kotlinx.android.synthetic.main.bottom_sheet_history.*
import kotlinx.android.synthetic.main.fragment_breast_feeding_history.*
import org.threeten.bp.*
import java.util.*


class BreastFeedingHistoryFragment : Fragment(), CellClickListener {

    val allCategory: MutableList<MonthWithDates> = ArrayList()
    // val daysList: MutableList<Days> = ArrayList()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    lateinit var currentDate: OffsetDateTime
    lateinit var sevendaysDate:OffsetDateTime
    var days: ArrayList<Days> =ArrayList()
    var month: Int = 0
    var year: Int = 0
    // NEW LINE
    private var progressBreastFeeding:List<ProgressBreastFeeding> = ArrayList()
    private var progressDiaperChange:List<ProgressBabyPoopDiaperChange> = ArrayList()
    val weekdaysArray = arrayOf("SUNDAY","MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bottomSheetBehavior = BottomSheetBehavior.from(ly_bottom_sheet)
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
            /*      buttonBottomSheetPersistent.text = when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> "Close Persistent Bottom Sheet"
                    BottomSheetBehavior.STATE_COLLAPSED -> "Open Persistent Bottom Sheet"
                    else -> "Persistent Bottom Sheet"
                }*/
            }
        })

        mcv_back.setOnClickListener {
            requireActivity().onBackPressed()
        }
        currentDate =  OffsetDateTime.now()

        tb_previous_date.setOnClickListener {
            currentDate = currentDate.minusMonths(1)
            sevendaysDate = currentDate.minusMonths(1)
            val value = getDateInMMYYYYOffsetDateTime(currentDate)
            tv_date_of_week.text= value
            setAllProgressUI()
        }
        tb_next_date.setOnClickListener {
            currentDate = currentDate.plusMonths(1)
            sevendaysDate =  currentDate.plusMonths(1)
            val value = getDateInMMYYYYOffsetDateTime(currentDate)
            tv_date_of_week.text= value
            setAllProgressUI()
        }
        if(!prefs.getOffsetDateTime().isNullOrEmpty()){
            currentDate = OffsetDateTime.parse(prefs.getOffsetDateTime())
            sevendaysDate = currentDate.minusMonths(1)
            val value = getDateInMMYYYYOffsetDateTime(currentDate)
            tv_date_of_week.text= value
            setAllProgressUI()
        }else{
           currentDate =  OffsetDateTime.now()
           sevendaysDate = currentDate.minusMonths(1)

            val value = getDateInMMYYYYOffsetDateTime(currentDate)
            tv_date_of_week.text= value
            setAllProgressUI()
        }

    }
    private fun getCurrentDateInString(currentDate1: OffsetDateTime) {
        if(allCategory.size>0)
        allCategory.clear()
        month = currentDate1.monthValue
        year = currentDate1.year

        val yearMonthObject: YearMonth = YearMonth.of(year, month)
        val initial: LocalDate = LocalDate.of(OffsetDateTime.now().year, OffsetDateTime.now().monthValue, OffsetDateTime.now().dayOfMonth)
        val daysInMonth: Int = initial.lengthOfMonth()
        val diffrenceDaysInt = daysInMonth - currentDate1.dayOfMonth  // 31-1 = 30
        // val firstDate = daysInMonth - (daysInMonth-1)
        var firstDate:OffsetDateTime? = null


        firstDate  = currentDate1.plusDays(diffrenceDaysInt.toLong()).minusDays(daysInMonth.toLong()-1)
        var valueToAppend:Int = 0
        for (i in weekdaysArray.indices){
            if (firstDate.dayOfWeek.toString() == weekdaysArray[i]){
                valueToAppend = i
            }
        }
        if(days.size>0)
            days.clear()
        for (i in 0 until daysInMonth+valueToAppend)
        {
            if (i <= valueToAppend){
                days.add(Days(i,"",progressBreastFeeding,progressDiaperChange))
            }else{
                days.add(Days(0,currentDate1.plusDays(diffrenceDaysInt.toLong()).minusDays(i.toLong()).toString(),progressBreastFeeding,progressDiaperChange))
            }
        }
        allCategory.add(MonthWithDates(1,days))
        Coroutines.main {
            context?.let {
                showHistoryCalander(allCategory)
            }
        }
    }


    private fun setAllProgressUI() {
        // NEW LINE
        Coroutines.io {
            // breastfeeding
            context?.let {
                progressBreastFeeding = AppDatabase(it).getBreastFeedingDao()
                    .getProgressDataBetweenDates( sevendaysDate, currentDate)
            }
            // DiaperChange
            context?.let {
                progressDiaperChange = AppDatabase(it).getBreastFeedingDao()
                    .getProgressDiaperChange( sevendaysDate, currentDate)

            }
            getCurrentDateInString(currentDate)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_breast_feeding_history, container, false)
    }
    private fun showHistoryCalander(category: List<MonthWithDates>) {
        historylist.layoutManager = LinearLayoutManager(activity)
        historylist.adapter = BreastFeedHistoryMainAdapter(requireActivity(),category,this)
    }

    override fun onCellClickListener(view: View) {
        val state =
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED){
                BottomSheetBehavior.STATE_COLLAPSED
            }
            else{
                BottomSheetBehavior.STATE_EXPANDED
            }
        bottomSheetBehavior.state = state
    }

    override fun onBottomClickListener(
        day: String,
        breastFeedingCount: String,
        poopCount: String,
        peepCount: String
    ) {
        val state =
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED){
                BottomSheetBehavior.STATE_COLLAPSED
            }
            else{
                day_name.text = day
                tv_breastfeed_count.text = breastFeedingCount
                tv_poop_count.text = poopCount
                tv_peep_count.text = peepCount
                BottomSheetBehavior.STATE_EXPANDED
            }
        bottomSheetBehavior.state = state
    }
}