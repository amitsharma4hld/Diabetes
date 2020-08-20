package com.s.diabetesfeeding.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.s.diabetesfeeding.R
import kotlinx.android.synthetic.main.fragment_baby_weight.*
import kotlinx.android.synthetic.main.fragment_daily_observation.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class BabyWeightFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var titleName: String =""
        arguments?.getString(BabyWeightFragment.ARG_TITLE)?.let {
            titleName  = it
        }
        done.setOnClickListener {
            addFragment(
                BreastfeedingFragment.newInstance(
                    titleName,
                    ""
                )
            )
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_baby_weight, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BabyWeightFragment().apply {
                arguments = Bundle().apply {
                    putString(BabyWeightFragment.ARG_TITLE, param1)
                    putString(BabyWeightFragment.ARG_PARAM2, param2)
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