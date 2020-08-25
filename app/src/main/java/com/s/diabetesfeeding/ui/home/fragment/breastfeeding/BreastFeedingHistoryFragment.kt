package com.s.diabetesfeeding.ui.home.fragment.breastfeeding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.BreastFeedingSessionData
import com.s.diabetesfeeding.data.db.entities.*
import com.s.diabetesfeeding.ui.CellClickListener
import com.s.diabetesfeeding.ui.adapter.BreastFeedHistoryMainAdapter
import com.s.diabetesfeeding.ui.adapter.MonitorBloodGlucoseMainAdapter
import com.s.diabetesfeeding.util.Coroutines
import kotlinx.android.synthetic.main.bottom_sheet_history.*
import kotlinx.android.synthetic.main.fragment_breast_feeding_history.*
import kotlinx.android.synthetic.main.monitor_blood_glucose_fragment.*
import java.time.Month
import java.util.*


class BreastFeedingHistoryFragment : Fragment(), CellClickListener {

    val days = listOf(
        Days(1,"1"), Days(2,"2"), Days(3,"3"), Days(4,"4"),
        Days(5,"5"), Days(6,"6"), Days(7,"7"), Days(8,"8"),
        Days(9,"9"), Days(10,"10"), Days(11,"11"), Days(12,"12"), Days(13,"13"), Days(14,"14"),
        Days(15,"15"), Days(16,"16"), Days(17,"17"), Days(18,"18"),
        Days(19,"19"), Days(20,"20"), Days(21,"21"), Days(22,"22"), Days(23,"23"), Days(24,"24"),
        Days(25,"25"), Days(26,"26"), Days(27,"27"), Days(28,"28"),
        Days(29,"29"), Days(30,"30")
    )
    val allCategory: MutableList<MonthWithDates> = ArrayList()
    // val daysList: MutableList<Days> = ArrayList()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //daysList.add(Days(1,"1"))

        allCategory.add(MonthWithDates(1,"Aug 2020",days))
       // allCategory.add(MonthWithDates(2,"Sep 2020",days))
       /*
        allCategory.add(MonthWithDates(3,"Mar 2020",days))
        allCategory.add(MonthWithDates(4,"April 2020",days))
        allCategory.add(MonthWithDates(5,"May 2020",days))
        allCategory.add(MonthWithDates(6,"June 2020",days))
        allCategory.add(MonthWithDates(7,"July 2020",days))
        allCategory.add(MonthWithDates(8,"Aug 2020",days))
        allCategory.add(MonthWithDates(9,"Sept 2020",days))
        allCategory.add(MonthWithDates(10,"Oct 2020",days))
        allCategory.add(MonthWithDates(11,"Nov 2020",days))
        allCategory.add(MonthWithDates(12,"Dec 2020",days))
        */
        Coroutines.main {
            context?.let {
                showHistoryCalander(allCategory)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bottomSheetBehavior = BottomSheetBehavior.from(ly_bottom_sheet)
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
            /*    buttonBottomSheetPersistent.text = when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> "Close Persistent Bottom Sheet"
                    BottomSheetBehavior.STATE_COLLAPSED -> "Open Persistent Bottom Sheet"
                    else -> "Persistent Bottom Sheet"
                }*/
            }
        })

        mcv_back.setOnClickListener {
            requireActivity().onBackPressed()
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
}