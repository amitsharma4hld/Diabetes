package com.s.diabetesfeeding.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.BreastFeedingSessionData
import com.s.diabetesfeeding.ui.BreastFeedingSessionAdapter
import com.s.diabetesfeeding.ui.CellClickListener
import com.s.diabetesfeeding.util.SpaceGridDecoration
import kotlinx.android.synthetic.main.fragment_breastfeeding.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class BreastfeedingFragment : Fragment(), CellClickListener {
    private var param1: String? = null
    private var param2: String? = null
    var isFirst = true
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
        arguments?.let {
            param1 = it.getString(BreastfeedingFragment.ARG_PARAM1)
            param2 = it.getString(BreastfeedingFragment.ARG_PARAM2)
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.getString(BreastfeedingFragment.ARG_TITLE)?.let {
            username_breastfeeding?.text  = "Hello "+it +","
        }
        val typefaceRegular = ResourcesCompat.getFont(requireContext(), R.font.avenir_next_regular)
        val typefaceBold = ResourcesCompat.getFont(requireContext(), R.font.avenir_next_bold)

        showSessions(sessions)
        rl_breast_feeding.setOnClickListener {
            tv_beast_feeding.typeface = typefaceBold
            tv_diaper_change.typeface = typefaceRegular
            tv_daily_observations.typeface = typefaceRegular
            tv_baby_wight.typeface = typefaceRegular
            tv_selected_title.text = "Record breastfeeding sessions per day"
            showSessionsSecond(sessions)
            isFirst = true

        }
        rl_diaper_change.setOnClickListener {
            tv_beast_feeding.typeface = typefaceRegular
            tv_diaper_change.typeface = typefaceBold
            tv_daily_observations.typeface = typefaceRegular
            tv_baby_wight.typeface = typefaceRegular
            tv_selected_title.text = "Record diaper changes per day"
            showSessionsSecond(sessions2)
            isFirst = false

        }
        rl_daily_observation.setOnClickListener {
            tv_beast_feeding.typeface = typefaceRegular
            tv_diaper_change.typeface = typefaceRegular
            tv_daily_observations.typeface = typefaceBold
            tv_baby_wight.typeface = typefaceRegular
            addFragment(
                arguments?.getString(BreastfeedingFragment.ARG_TITLE)?.let {
                    it
                }?.let { it1 ->
                    DailyObservationFragment.newInstance(
                        it1,
                        ""

                    )
                }
            )
        }
        rl_baby_weight.setOnClickListener {
            tv_beast_feeding.typeface = typefaceRegular
            tv_diaper_change.typeface = typefaceRegular
            tv_daily_observations.typeface = typefaceRegular
            tv_baby_wight.typeface = typefaceBold
        }
        mcv_babyweight.setOnClickListener {
            addFragment(
                arguments?.getString(BreastfeedingFragment.ARG_TITLE)?.let {
                    it
                }?.let { it1 ->
                    BabyWeightFragment.newInstance(
                        it1,
                        ""

                    )
                }
            )
        }
    }

    private fun showSessions(sessions: List<BreastFeedingSessionData>) {
        rv_breastfeeding_sessions.layoutManager = GridLayoutManager(activity, 4)
        rv_breastfeeding_sessions!!.addItemDecoration(SpaceGridDecoration(4, 8, false))
        rv_breastfeeding_sessions.adapter = BreastFeedingSessionAdapter(sessions,this)
    }
    private fun showSessionsSecond(sessions: List<BreastFeedingSessionData>) {
        rv_breastfeeding_sessions.removeAllViews()
        rv_breastfeeding_sessions.layoutManager = null
        rv_breastfeeding_sessions.layoutManager = GridLayoutManager(activity, 4)
        //rv_breastfeeding_sessions!!.addItemDecoration(SpaceGridDecoration(4, 8, false))
        rv_breastfeeding_sessions.adapter = BreastFeedingSessionAdapter(sessions,this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_breastfeeding, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BreastfeedingFragment().apply {
                arguments = Bundle().apply {
                    putString(BreastfeedingFragment.ARG_TITLE, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
        private const val ARG_TITLE = "arg_title"
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
    }

    fun openFragment(fragment: Fragment?) {
        val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.replace(R.id.container, fragment!!)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    fun addFragment(fragment: Fragment?) {
        val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.add(R.id.container, fragment!!)
        transaction.addToBackStack(HomeScreenFragment.toString())
        transaction.commit()
    }

    override fun onCellClickListener() {
        if (isFirst){
            addFragment(
                arguments?.getString(BreastfeedingFragment.ARG_TITLE)?.let {
                    it
                }?.let { it1 ->
                    GoalBreastfeedFragment.newInstance(
                        it1,
                        ""

                    )
                }
            )
        }else{
            addFragment(
                arguments?.getString(BreastfeedingFragment.ARG_TITLE)?.let {
                    it
                }?.let { it1 ->
                    DiaperChangeFragment.newInstance(
                        it1,
                        ""

                    )
                }
            )
        }

    }
}