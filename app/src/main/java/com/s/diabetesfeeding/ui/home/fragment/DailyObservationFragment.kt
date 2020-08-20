package com.s.diabetesfeeding.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.DailyObservationData
import com.s.diabetesfeeding.ui.DailyObservationsAdapter
import com.s.diabetesfeeding.ui.SymptomsAdapter
import kotlinx.android.synthetic.main.fragment_breastfeeding.*
import kotlinx.android.synthetic.main.fragment_daily_observation.*
import kotlinx.android.synthetic.main.fragment_symptoms.*


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
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(DailyObservationFragment.ARG_PARAM1)
            param2 = it.getString(DailyObservationFragment.ARG_PARAM2)
        }
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
        var titleName: String =""
        arguments?.getString(DailyObservationFragment.ARG_TITLE)?.let {
            titleName  = it
        }
        showObservations(observations)
        mcv_done.setOnClickListener {
            addFragment(
                BreastfeedingFragment.newInstance(
                    titleName,
                    ""
                )
            )
        }
    }

    private fun showObservations(observations: List<DailyObservationData>) {
        rv_dailyObservation.layoutManager = LinearLayoutManager(activity)
        rv_dailyObservation.adapter = DailyObservationsAdapter(observations)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DailyObservationFragment().apply {
                arguments = Bundle().apply {
                    putString(DailyObservationFragment.ARG_TITLE, param1)
                    putString(DailyObservationFragment.ARG_PARAM2, param2)
                }
            }
        private const val ARG_TITLE = "arg_title"
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
    }

    fun addFragment(fragment: Fragment?) {
        val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.add(R.id.container, fragment!!)
        transaction.addToBackStack(HomeScreenFragment.toString())
        transaction.commit()
    }
}