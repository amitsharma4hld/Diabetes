package com.s.diabetesfeeding.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.s.diabetesfeeding.R
import kotlinx.android.synthetic.main.fragment_diabetes.*
import kotlinx.android.synthetic.main.fragment_obgyn.*


class ObgynFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ObgynFragment.ARG_PARAM1)
            param2 = it.getString(ObgynFragment.ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_obgyn, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.getString(ObgynFragment.ARG_TITLE)?.let {
            username_obgy?.text  = "Hello "+it +","
        }
        mc_observation.setOnClickListener {
            addFragment(
                    ObservationFragment.newInstance(
                        "it1",
                        R.color.colorAccent
                    )
            )
        }
        mc_counseling.setOnClickListener {
            addFragment(
                CounselingFragment.newInstance(
                    "it1",
                    ""
                )
            )
        }
        mc_prenatal_visit.setOnClickListener {
            addFragment(
                PrentalVisitFragment.newInstance(
                    "it1",
                    ""
                )
            )
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(title: String, bgColorId: Int) =
            ObgynFragment().apply {
                arguments = Bundle().apply {
                    putString(ObgynFragment.ARG_TITLE, title)
                    putInt(ObgynFragment.ARG_BG_COLOR, bgColorId)
                }
            }
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        private const val ARG_TITLE = "arg_title"
        private const val ARG_BG_COLOR = "arg_bg_color"
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