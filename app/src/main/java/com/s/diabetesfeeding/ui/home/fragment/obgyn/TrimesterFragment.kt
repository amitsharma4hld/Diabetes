package com.s.diabetesfeeding.ui.home.fragment.obgyn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.TrimesterData
import com.s.diabetesfeeding.ui.adapter.TrimesterAdapter
import kotlinx.android.synthetic.main.fragment_trimester.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class TrimesterFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    val trimesterTopics = listOf(
        TrimesterData(1,"HIV and Other Routine Parental Test","comments","Date",true),
        TrimesterData(2,"Risk Factors Identified by Parental History","comments","Date",true),
        TrimesterData(3,"Anticipated Course of Parental Care","comments","Date",true),
        TrimesterData(4,"Nutrition and Weight Gain Counseling","comments","Date",true),
        TrimesterData(5,"HIV and Other Routine Parental Test","comments","Date",true),
        TrimesterData(6,"Anticipated Course of Parental Care","comments","Date",true),
        TrimesterData(7,"Risk Factors Identified by Parental History","comments","Date",true)
    )

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
        return inflater.inflate(R.layout.fragment_trimester, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.getString(ARG_PARAM1)?.let {
            tv_title?.text  = it
        }
        showTrimesterTopics(trimesterTopics)
    }

    private fun showTrimesterTopics(trimesterTopics: List<TrimesterData>) {
        rv_trimester_topic.layoutManager = LinearLayoutManager(activity)
        rv_trimester_topic.adapter =
            TrimesterAdapter(trimesterTopics)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TrimesterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
    }
}