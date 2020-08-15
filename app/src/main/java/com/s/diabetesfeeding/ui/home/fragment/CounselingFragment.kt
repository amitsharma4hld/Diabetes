package com.s.diabetesfeeding.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.s.diabetesfeeding.R
import kotlinx.android.synthetic.main.fragment_counseling.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class CounselingFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_counseling, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ib_first_trimester.setOnClickListener {
            addFragment(
                TrimesterFragment.newInstance(
                    "1st Trimester Topic",
                    ""
                )
            )
        }
        ib_second_trimester.setOnClickListener {
            addFragment(
                TrimesterFragment.newInstance(
                    "2nd Trimester Topic",
                    ""
                )
            )
        }
        ib_third_trimester.setOnClickListener {
            addFragment(
                TrimesterFragment.newInstance(
                    "3rd Trimester Topic",
                    ""
                )
            )
        }
        ib_fourth_trimester.setOnClickListener {
            addFragment(
                TrimesterFragment.newInstance(
                    "Postpartum Topic",
                    ""
                )
            )
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CounselingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
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
}