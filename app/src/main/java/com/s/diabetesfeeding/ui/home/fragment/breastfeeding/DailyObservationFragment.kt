package com.s.diabetesfeeding.ui.home.fragment.breastfeeding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.DailyObservationData
import com.s.diabetesfeeding.ui.adapter.DailyObservationsAdapter
import com.s.diabetesfeeding.ui.home.fragment.HomeScreenFragment
import kotlinx.android.synthetic.main.fragment_daily_observation.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

val observations = listOf(
    DailyObservationData(1,"Sleep Hours","8"),
    DailyObservationData(2,"Rate your fatigue","4"),
    DailyObservationData(3,"Glass of water per day","7"),
    DailyObservationData(4,"Meals per day","3"),
    DailyObservationData(5,"Helps with chores","Yes"),
    DailyObservationData(6,"Glass of alcohol per day?","0"),
    DailyObservationData(7,"Smoking","No"),
    DailyObservationData(8,"Rate your stress","4")
)

class DailyObservationFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily_observation, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        showObservations(observations)
        mcv_done.setOnClickListener {
           requireActivity().onBackPressed()
        }
    }

    private fun showObservations(observations: List<DailyObservationData>) {
        rv_dailyObservation.layoutManager = LinearLayoutManager(activity)
        rv_dailyObservation.adapter =
            DailyObservationsAdapter(
                observations
            )
    }

}