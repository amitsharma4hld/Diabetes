package com.s.diabetesfeeding.ui.home.fragment.breastfeeding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.entities.breastfeeding.BreastFeedingSessionData
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.breastfeeding.BabyPoopData
import com.s.diabetesfeeding.data.db.entities.breastfeeding.ObservationBreastFeed
import com.s.diabetesfeeding.ui.adapter.BreastFeedingSessionAdapter
import com.s.diabetesfeeding.ui.CellClickListener
import com.s.diabetesfeeding.ui.adapter.DiaperChangeSessionAdapter
import com.s.diabetesfeeding.ui.home.fragment.diabetes.DiabetesFragmentArgs
import com.s.diabetesfeeding.util.SpaceGridDecoration
import kotlinx.android.synthetic.main.fragment_breastfeeding.*
import kotlinx.coroutines.launch


class BreastfeedingFragment : Fragment(), CellClickListener {
    var isFirst = true
    val currentDate: String = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(
        java.util.Date())

    var sessions = listOf(
        BreastFeedingSessionData("", "",""),
        BreastFeedingSessionData("", "",""),
        BreastFeedingSessionData("", "",""),
        BreastFeedingSessionData("", "",""),
        BreastFeedingSessionData("", "",""),
        BreastFeedingSessionData("", "",""),
        BreastFeedingSessionData("", "",""),
        BreastFeedingSessionData("", "",""),
        BreastFeedingSessionData("", "",""),
        BreastFeedingSessionData("", "",""),
        BreastFeedingSessionData("", "",""),
        BreastFeedingSessionData("", "","")
    )
    var sessions2 = listOf(
        BabyPoopData("", false, false),
        BabyPoopData("", false, false),
        BabyPoopData("", false, false),
        BabyPoopData("", false, false),
        BabyPoopData("", false, false),
        BabyPoopData("", false, false),
        BabyPoopData("", false, false),
        BabyPoopData("", false, false),
        BabyPoopData("", false, false),
        BabyPoopData("", false, false),
        BabyPoopData("", false, false),
        BabyPoopData("", false, false
        )
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
        val observationList = listOf(
            ObservationBreastFeed(0,"Sleep Hours","7",false,""),
            ObservationBreastFeed(0,"Rate your fatigue","4",false,""),
            ObservationBreastFeed(0,"Glass of water per day","8",false,""),
            ObservationBreastFeed(0,"Meals per day","4",false,""),
            ObservationBreastFeed(0,"Helps with chores","Yes",true,""),
            ObservationBreastFeed(0,"Glass of alcohol per day?","0",false,""),
            ObservationBreastFeed(0,"Smoking","No",true,""),
            ObservationBreastFeed(0,"Rate your stress","4",false,"")
        )

        val typefaceRegular = ResourcesCompat.getFont(requireContext(), R.font.avenir_next_regular)
        val typefaceBold = ResourcesCompat.getFont(requireContext(), R.font.avenir_next_bold)

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
            showDiaperChangeSessions(sessions2)

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

        viewLifecycleOwner.lifecycleScope.launch {
            context?.let {
                if (AppDatabase(it).getBreastFeedingDao().getAllObservation().isNullOrEmpty()) {
                    AppDatabase(it).getBreastFeedingDao().saveAllObservation(observationList)
                }
            }
            // New Line Update
            context?.let {
                if (AppDatabase(it).getBreastFeedingDao().getAllBreastFeedSession().isNullOrEmpty()) {
                    AppDatabase(it).getBreastFeedingDao().saveAllBreastFeedSession(sessions)
                }
            }
            // New Line
            context?.let {
                if (AppDatabase(it).getBreastFeedingDao().getAllDiaperChangeSession().isNullOrEmpty()) {
                    AppDatabase(it).getBreastFeedingDao().saveAllDiaperChangeSession(sessions2)
                }
            }
            context?.let {
                if (AppDatabase(it).getBreastFeedingDao().getAllBreastFeedSession().isNotEmpty()){
                    sessions = AppDatabase(it).getBreastFeedingDao().getAllBreastFeedSession()
                    showSessions(sessions)
                }
            }
            context?.let {
                if (AppDatabase(it).getBreastFeedingDao().getAllDiaperChangeSession().isNotEmpty()){
                    sessions2 = AppDatabase(it).getBreastFeedingDao().getAllDiaperChangeSession()
                    showDiaperChangeSessions(sessions2)
                }
            }
        }

    }

    private fun showDiaperChangeSessions(sessions: List<BabyPoopData>) {
        rv_breastfeeding_sessions.removeAllViews()
        rv_breastfeeding_sessions.layoutManager = null
        rv_breastfeeding_sessions.layoutManager = GridLayoutManager(activity, 4)
        rv_breastfeeding_sessions!!.addItemDecoration(SpaceGridDecoration(4, 8, false))
        rv_breastfeeding_sessions.adapter =
            DiaperChangeSessionAdapter(
                sessions,
                this
            )
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
    }

    override fun onBottomClickListener(
        day: String,
        breastFeedingCount: String,
        poopCount: String,
        peepCount: String
    ) {
        
    }
}