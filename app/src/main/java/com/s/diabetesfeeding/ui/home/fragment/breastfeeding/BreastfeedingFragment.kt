package com.s.diabetesfeeding.ui.home.fragment.breastfeeding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.BreastFeedingSessionData
import com.s.diabetesfeeding.ui.adapter.BreastFeedingSessionAdapter
import com.s.diabetesfeeding.ui.CellClickListener
import com.s.diabetesfeeding.ui.home.fragment.*
import com.s.diabetesfeeding.ui.home.fragment.diabetes.DiabetesFragmentArgs
import com.s.diabetesfeeding.ui.home.fragment.obgyn.ObgynFragmentDirections
import com.s.diabetesfeeding.util.SpaceGridDecoration
import kotlinx.android.synthetic.main.fragment_breastfeeding.*
import kotlinx.android.synthetic.main.fragment_diabetes.*
import kotlinx.android.synthetic.main.fragment_obgyn.*


class BreastfeedingFragment : Fragment(), CellClickListener {
    var isFirst = true
    val currentDate: String = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(
        java.util.Date())

    val sessions = listOf(
        BreastFeedingSessionData(1,"6:00AM","00:48"),
        BreastFeedingSessionData(2,"8:00AM","00:45"),
        BreastFeedingSessionData(3,"",""),
        BreastFeedingSessionData(4,"",""),
        BreastFeedingSessionData(5,"",""),
        BreastFeedingSessionData(6,"",""),
        BreastFeedingSessionData(7,"",""),
        BreastFeedingSessionData(8,"",""),
        BreastFeedingSessionData(9,"",""),
        BreastFeedingSessionData(10,"",""),
        BreastFeedingSessionData(11,"",""),
        BreastFeedingSessionData(12,"","")
    )
    val sessions2 = listOf(
        BreastFeedingSessionData(1,"6:00AM","poop pee"),
        BreastFeedingSessionData(2,"8:00AM","poop"),
        BreastFeedingSessionData(3,"",""),
        BreastFeedingSessionData(4,"",""),
        BreastFeedingSessionData(5,"",""),
        BreastFeedingSessionData(6,"",""),
        BreastFeedingSessionData(7,"",""),
        BreastFeedingSessionData(8,"",""),
        BreastFeedingSessionData(9,"",""),
        BreastFeedingSessionData(10,"",""),
        BreastFeedingSessionData(11,"",""),
        BreastFeedingSessionData(12,"","")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_today_date_breastfeed.text = currentDate
        arguments?.let {
            username_breastfeeding?.text = "Hello "+ DiabetesFragmentArgs.fromBundle(it).name +","
        }
        val typefaceRegular = ResourcesCompat.getFont(requireContext(), R.font.avenir_next_regular)
        val typefaceBold = ResourcesCompat.getFont(requireContext(), R.font.avenir_next_bold)

        showSessions(sessions)
        rl_breast_feeding.setOnClickListener {
            isFirst = true
            rl_show_calender.visibility = View.GONE
            tv_beast_feeding.typeface = typefaceBold
            tv_diaper_change.typeface = typefaceRegular
            tv_daily_observations.typeface = typefaceRegular
            tv_baby_wight.typeface = typefaceRegular
            tv_selected_title.text = "Record breastfeeding sessions per day"
            rv_breastfeeding_sessions.visibility = View.VISIBLE
            showSessionsSecond(sessions)

        }
        rl_diaper_change.setOnClickListener {
            isFirst = false
            rl_show_calender.visibility = View.GONE
            tv_beast_feeding.typeface = typefaceRegular
            tv_diaper_change.typeface = typefaceBold
            tv_daily_observations.typeface = typefaceRegular
            tv_baby_wight.typeface = typefaceRegular
            tv_selected_title.text = "Record diaper changes per day"
            rv_breastfeeding_sessions.visibility = View.VISIBLE
            showSessionsSecond(sessions2)

        }
        rl_daily_observation.setOnClickListener {
            tv_beast_feeding.typeface = typefaceRegular
            tv_diaper_change.typeface = typefaceRegular
            tv_daily_observations.typeface = typefaceBold
            tv_baby_wight.typeface = typefaceRegular

            val action = BreastfeedingFragmentDirections.actionBreastfeedingFragmentToDailyObservationFragment()
            Navigation.findNavController(it).navigate(action)

        }
        rl_baby_weight.setOnClickListener {
            tv_beast_feeding.typeface = typefaceRegular
            tv_diaper_change.typeface = typefaceRegular
            tv_daily_observations.typeface = typefaceRegular
            tv_baby_wight.typeface = typefaceBold

            val action = BreastfeedingFragmentDirections.actionBreastfeedingFragmentToBabyWeightFragment()
            Navigation.findNavController(it).navigate(action)
        }

        rl_show_calender.setOnClickListener {
            val action = BreastfeedingFragmentDirections.actionBreastfeedingFragmentToBreastFeedingHistoryFragment()
            Navigation.findNavController(it).navigate(action)
        }

    }

    private fun showSessions(sessions: List<BreastFeedingSessionData>) {
        rv_breastfeeding_sessions.layoutManager = GridLayoutManager(activity, 4)
        rv_breastfeeding_sessions!!.addItemDecoration(SpaceGridDecoration(4, 8, false))
        rv_breastfeeding_sessions.adapter =
            BreastFeedingSessionAdapter(
                sessions,
                this
            )
    }
    private fun showSessionsSecond(sessions: List<BreastFeedingSessionData>) {
        rv_breastfeeding_sessions.removeAllViews()
        rv_breastfeeding_sessions.layoutManager = null
        rv_breastfeeding_sessions.layoutManager = GridLayoutManager(activity, 4)
        //rv_breastfeeding_sessions!!.addItemDecoration(SpaceGridDecoration(4, 8, false))
        rv_breastfeeding_sessions.adapter =
            BreastFeedingSessionAdapter(
                sessions,
                this
            )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_breastfeeding, container, false)
    }

    override fun onCellClickListener(view: View) {
        if (isFirst){
            val action = BreastfeedingFragmentDirections.actionBreastfeedingFragmentToGoalBreastfeedFragment()
            Navigation.findNavController(view).navigate(action)
            // GoalBreastfeedFragment
        }else{
            val action = BreastfeedingFragmentDirections.actionBreastfeedingFragmentToDiaperChangeFragment()
            Navigation.findNavController(view).navigate(action)
            //  DiaperChangeFragment

        }
    }
}