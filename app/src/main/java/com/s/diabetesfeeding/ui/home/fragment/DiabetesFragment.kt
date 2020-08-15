package com.s.diabetesfeeding.ui.home.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.ui.home.InsulinFragment
import kotlinx.android.synthetic.main.fragment_diabetes.*
import kotlinx.android.synthetic.main.fragment_home_screen.tv_blood_glucose
import kotlinx.android.synthetic.main.fragment_home_screen.tv_insulin
import kotlinx.android.synthetic.main.fragment_home_screen.tv_symptoms
import kotlinx.android.synthetic.main.fragment_home_screen.tv_weight


class DiabetesFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(DiabetesFragment.ARG_PARAM1)
            param2 = it.getString(DiabetesFragment.ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_diabetes, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.getString(DiabetesFragment.ARG_TITLE)?.let {
            username_diabetes?.text  = "Hello "+it +","
        }
        tv_blood_glucose.setOnClickListener {
            addFragment(
                MonitorBloodGlucoseFragment.newInstance(
                    "",
                    R.color.colorAccent
                )
            )
        }
        tv_insulin.setOnClickListener {
            openFragment(
                InsulinFragment.newInstance(
                    "",
                    R.color.colorAccent
                )
            )
        }
        tv_weight.setOnClickListener {
            openFragment(
                Weight_fragment.newInstance(
                    "",
                    R.color.colorAccent
                )
            )
        }
        tv_symptoms.setOnClickListener {
            openFragment(
                SymptomsFragment.newInstance(
                    "",
                    R.color.colorAccent
                )
            )
        }
        tv_progress.setOnClickListener {
            openFragment(
                ProgressBloodGlucoseFragment.newInstance(
                    "",
                    ""
                )
            )
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(title: String, bgColorId: Int) =
            DiabetesFragment().apply {
                arguments = Bundle().apply {
                    putString(DiabetesFragment.ARG_TITLE, title)
                    putInt(DiabetesFragment.ARG_BG_COLOR, bgColorId)
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