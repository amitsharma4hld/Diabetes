package com.s.diabetesfeeding.ui.home.fragment.breastfeeding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.ui.home.fragment.HomeScreenFragment
import kotlinx.android.synthetic.main.fragment_goal_breastfeed.*


class GoalBreastfeedFragment : Fragment() {

    val currentDate: String = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(
        java.util.Date())
    val currentTime: String = java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault()).format(
        java.util.Date())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var titleName: String =""
        tv_date.text = currentDate
        tv_time.text = currentTime
        cv_start.setOnClickListener {
            if (tv_start_stop_done.text == "START"){
                tv_start_stop_done.text = "STOP"
            }else if (tv_start_stop_done.text == "STOP"){
                tv_start_stop_done.text = "DONE +5"

            }else if (tv_start_stop_done.text == "DONE +5"){
                requireActivity().onBackPressed()
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goal_breastfeed, container, false)
    }




}