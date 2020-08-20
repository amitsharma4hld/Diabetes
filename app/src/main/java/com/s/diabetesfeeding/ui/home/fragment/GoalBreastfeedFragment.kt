package com.s.diabetesfeeding.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.s.diabetesfeeding.R
import kotlinx.android.synthetic.main.fragment_goal_breastfeed.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class GoalBreastfeedFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(GoalBreastfeedFragment.ARG_PARAM1)
            param2 = it.getString(GoalBreastfeedFragment.ARG_PARAM2)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var titleName: String =""
        arguments?.getString(GoalBreastfeedFragment.ARG_TITLE)?.let {
            titleName  = it
        }
        cv_start.setOnClickListener {
            if (tv_start_stop_done.text == "START"){
                tv_start_stop_done.text = "STOP"
            }else if (tv_start_stop_done.text == "STOP"){
                tv_start_stop_done.text = "DONE +5"

            }else if (tv_start_stop_done.text == "DONE +5"){
                addFragment(
                    BreastfeedingFragment.newInstance(
                        titleName,
                        ""
                    )
                )
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

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GoalBreastfeedFragment().apply {
                arguments = Bundle().apply {
                    putString(GoalBreastfeedFragment.ARG_PARAM1, param1)
                    putString(GoalBreastfeedFragment.ARG_PARAM2, param2)
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