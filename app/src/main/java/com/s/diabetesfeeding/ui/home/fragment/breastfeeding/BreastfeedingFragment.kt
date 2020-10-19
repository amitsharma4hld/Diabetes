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
import androidx.room.Query
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.breastfeeding.*
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.ui.CellClickListener
import com.s.diabetesfeeding.ui.adapter.BreastFeedingSessionAdapter
import com.s.diabetesfeeding.ui.adapter.DiaperChangeSessionAdapter
import com.s.diabetesfeeding.ui.home.fragment.diabetes.DiabetesFragmentArgs
import com.s.diabetesfeeding.util.Coroutines
import com.s.diabetesfeeding.util.SpaceGridDecoration
import com.s.diabetesfeeding.util.getDateFromOffsetDateTime
import kotlinx.android.synthetic.main.fragment_breastfeeding.*
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime
import java.util.ArrayList


class BreastfeedingFragment : Fragment(), CellClickListener {
    var isFirst = true
    val currentDate: String = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(
        java.util.Date())
    private var progressBreastFeeding:List<ProgressBreastFeeding> = ArrayList()
    private var progressDiaperChange:List<ProgressBabyPoopDiaperChange> = ArrayList()
    private var progressDailyObservation : List<ProgressDailyObservation> = ArrayList()
    var sessions = listOf(
        BreastFeedingSessionData(1,1, "","",""),
        BreastFeedingSessionData(2,2, "","",""),
        BreastFeedingSessionData(3,3, "","",""),
        BreastFeedingSessionData(4,4, "","",""),
        BreastFeedingSessionData(5,5, "","",""),
        BreastFeedingSessionData(6,6, "","",""),
        BreastFeedingSessionData(7,6, "","",""),
        BreastFeedingSessionData(8,7, "","",""),
        BreastFeedingSessionData(9,8, "","",""),
        BreastFeedingSessionData(10,9, "","",""),
        BreastFeedingSessionData(11,10, "","",""),
        BreastFeedingSessionData(12,11, "","","")
    )
    var sessions2 = listOf(
        BabyPoopData(1,1,"", false, false),
        BabyPoopData(2,2,"", false, false),
        BabyPoopData(3,3,"", false, false),
        BabyPoopData(4,4,"", false, false),
        BabyPoopData(5,5,"", false, false),
        BabyPoopData(6,6,"", false, false),
        BabyPoopData(7,7,"", false, false),
        BabyPoopData(8,8,"", false, false),
        BabyPoopData(9,9,"", false, false),
        BabyPoopData(10,10,"", false, false),
        BabyPoopData(11,11,"", false, false),
        BabyPoopData(12,12,"", false, false)
    )
    val observationList = listOf(
        ObservationBreastFeed(1,"Sleep Hours","",false,""),
        ObservationBreastFeed(2,"Rate your fatigue","",false,""),
        ObservationBreastFeed(3,"Glass of water per day","",false,""),
        ObservationBreastFeed(4,"Meals per day","",false,""),
        ObservationBreastFeed(5,"Helps with chores","",true,""),
        ObservationBreastFeed(6,"Glass of alcohol per day?","",false,""),
        ObservationBreastFeed(7,"Smoking","",true,""),
        ObservationBreastFeed(8,"Rate your stress","",false,"")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (prefs.getSavedFormattedDate().isNullOrEmpty()){
            tv_today_date_breastfeed.text = currentDate
        }else{
            tv_today_date_breastfeed.text = prefs.getSavedFormattedDate()
        }
        arguments?.let {
            username_breastfeeding?.text = "Hello "+ DiabetesFragmentArgs.fromBundle(it).name +","
        }


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
            tv_selected_title.visibility = View.VISIBLE
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
            tv_selected_title.visibility = View.GONE

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

        cv_history.setOnClickListener {
            val action = BreastfeedingFragmentDirections.actionBreastfeedingFragmentToBreastFeedingHistoryFragment()
            Navigation.findNavController(it).navigate(action)
        }
        cv_resources.setOnClickListener {
            val action = BreastfeedingFragmentDirections.actionBreastfeedingFragmentToResourcesFragment()
            Navigation.findNavController(it).navigate(action)
        }

        if (!prefs.getOffsetDateTime().isNullOrEmpty()) {
            val fromDate = OffsetDateTime.parse(prefs.getFromOffsetDateTime())
            val toDate = OffsetDateTime.parse(prefs.getOffsetDateTime())
            Coroutines.io {
                // breastfeeding
                context?.let {
                    progressBreastFeeding = emptyList()
                    progressBreastFeeding = AppDatabase(it).getBreastFeedingDao()
                        .getProgressDataBetweenDates( fromDate, toDate)
                }
                context?.let {
                    if (progressBreastFeeding.isEmpty()) {
                        AppDatabase(it).getBreastFeedingDao().saveAllBreastFeedSession(sessions)
                    }else{
                        AppDatabase(it).getBreastFeedingDao().saveAllBreastFeedSession(sessions)
                        for (i in progressBreastFeeding.indices){
                            AppDatabase(it).getBreastFeedingDao()
                                .updateBreastFeedingSessionColumn(
                                    progressBreastFeeding[i].breastfeedingTime,
                                    progressBreastFeeding[i].breastfeedingTimerCount,
                                    progressBreastFeeding[i].breastfeedingType,
                                    progressBreastFeeding[i].tempId)
                        }
                    }
                }
                // DiaperChange
                context?.let {
                    progressDiaperChange = emptyList()
                    progressDiaperChange = AppDatabase(it).getBreastFeedingDao()
                        .getProgressDiaperChange( fromDate, toDate)
                }
                context?.let {
                    if (progressDiaperChange.isEmpty()) {
                        AppDatabase(it).getBreastFeedingDao().saveAllDiaperChangeSession(sessions2)
                    }else{
                        AppDatabase(it).getBreastFeedingDao().saveAllDiaperChangeSession(sessions2)
                        for (i in progressDiaperChange.indices){
                            AppDatabase(it).getBreastFeedingDao()
                                .updateDiaperChangeColumn(
                                    progressDiaperChange[i].poop_pee_time,
                                    progressDiaperChange[i].isPoop,
                                    progressDiaperChange[i].isPee,
                                    progressDiaperChange[i].temId)
                        }
                    }
                }
                // ProgressDailyObservation
                context?.let {
                    progressDailyObservation = emptyList()
                    progressDailyObservation = AppDatabase(it).getBreastFeedingDao()
                        .getProgressDailyObservation( fromDate, toDate)
                }
                context?.let {
                    if (progressDailyObservation.isEmpty()){
                        AppDatabase(it).getBreastFeedingDao().saveAllObservation(observationList)
                    }else{
                        AppDatabase(it).getBreastFeedingDao().saveAllObservation(observationList)
                        for (i in progressDailyObservation.indices){
                            AppDatabase(it).getBreastFeedingDao()
                                .updateDailyObservationColumn(
                                    progressDailyObservation[i].title,
                                    progressDailyObservation[i].value,
                                    progressDailyObservation[i].isBoolean,
                                    progressDailyObservation[i].unit)
                        }
                    }
                }
            }
        }
            viewLifecycleOwner.lifecycleScope.launch {
                context?.let {
                    if (AppDatabase(it).getBreastFeedingDao().getAllObservation().isNullOrEmpty()) {
                        AppDatabase(it).getBreastFeedingDao().saveAllObservation(observationList)
                    }
                }
                // New Line Update
                context?.let {
                    if (AppDatabase(it).getBreastFeedingDao().getAllBreastFeedSession()
                            .isNullOrEmpty()
                    ) {
                        AppDatabase(it).getBreastFeedingDao().saveAllBreastFeedSession(sessions)
                    }
                }
                // New Line
                context?.let {
                    if (AppDatabase(it).getBreastFeedingDao().getAllDiaperChangeSession()
                            .isNullOrEmpty()
                    ) {
                        AppDatabase(it).getBreastFeedingDao().saveAllDiaperChangeSession(sessions2)
                    }
                }
                context?.let {
                    if (AppDatabase(it).getBreastFeedingDao().getAllBreastFeedSession()
                            .isNotEmpty()
                    ) {
                        sessions = AppDatabase(it).getBreastFeedingDao().getAllBreastFeedSession()
                        showSessions(sessions)
                    }
                }
                context?.let {
                    if (AppDatabase(it).getBreastFeedingDao().getAllDiaperChangeSession()
                            .isNotEmpty()
                    ) {
                        sessions2 =
                            AppDatabase(it).getBreastFeedingDao().getAllDiaperChangeSession()
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
        // rv_breastfeeding_sessions!!.addItemDecoration(SpaceGridDecoration(4, 8, false))
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