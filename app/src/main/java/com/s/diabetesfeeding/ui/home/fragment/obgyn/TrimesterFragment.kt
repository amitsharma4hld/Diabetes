package com.s.diabetesfeeding.ui.home.fragment.obgyn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.SymptomsData
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.obgynentities.PostPartumData
import com.s.diabetesfeeding.data.db.entities.obgynentities.TrimesterDataOne
import com.s.diabetesfeeding.data.db.entities.obgynentities.TrimesterDataThree
import com.s.diabetesfeeding.data.db.entities.obgynentities.TrimesterDataTwo
import com.s.diabetesfeeding.ui.adapter.PostpartumAdapter
import com.s.diabetesfeeding.ui.adapter.TrimesterAdapter
import com.s.diabetesfeeding.ui.adapter.TrimesterThreeAdapter
import com.s.diabetesfeeding.ui.adapter.TrimesterTwoAdapter
import kotlinx.android.synthetic.main.fragment_trimester.*
import kotlinx.coroutines.launch


class TrimesterFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    var trimesterOneList: List<TrimesterDataOne> = ArrayList()
    var trimesterTwoList: List<TrimesterDataTwo> = ArrayList()
    var trimesterThreeList: List<TrimesterDataThree> = ArrayList()
    var trimesterPostpartumList: List<PostPartumData> = ArrayList()

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
        if (tv_title.text == "1st Trimester Topic"){
            viewLifecycleOwner.lifecycleScope.launch {
                trimesterOneList =  AppDatabase(requireActivity().applicationContext).getObgynDao().getAllTrimesterOne()
                showTrimesterOneTopics(trimesterOneList)
            }
        }
        if (tv_title.text == "2nd Trimester Topic"){
            viewLifecycleOwner.lifecycleScope.launch {
                trimesterTwoList = AppDatabase(requireActivity().applicationContext).getObgynDao().getAllTrimesterTwo()
                showTrimesterTwoTopics(trimesterTwoList)
            }
        }
        if (tv_title.text == "3rd Trimester Topic"){
            viewLifecycleOwner.lifecycleScope.launch {
                trimesterThreeList = AppDatabase(requireActivity().applicationContext).getObgynDao().getAllTrimesterThree()
                showTrimesterThreeTopics(trimesterThreeList)
            }
        }
        if (tv_title.text == "Postpartum Topic"){
            viewLifecycleOwner.lifecycleScope.launch {
                trimesterPostpartumList = AppDatabase(requireActivity().applicationContext).getObgynDao().getAllPostPartumData()
                showPostpartumTopics(trimesterPostpartumList)
            }
        }

        mcv_trimester_done.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }

    private fun showTrimesterOneTopics(trimesterTopics: List<TrimesterDataOne>) {
        rv_trimester_topic.layoutManager = LinearLayoutManager(activity)
        rv_trimester_topic.adapter = TrimesterAdapter(requireContext(),trimesterTopics)
    }
    private fun showTrimesterTwoTopics(trimesterTopics: List<TrimesterDataTwo>) {
        rv_trimester_topic.layoutManager = LinearLayoutManager(activity)
        rv_trimester_topic.adapter = TrimesterTwoAdapter(requireContext(),trimesterTopics)
    }
    private fun showTrimesterThreeTopics(trimesterTopics: List<TrimesterDataThree>) {
        rv_trimester_topic.layoutManager = LinearLayoutManager(activity)
        rv_trimester_topic.adapter = TrimesterThreeAdapter(requireContext(),trimesterTopics)
    }
    private fun showPostpartumTopics(trimesterTopics: List<PostPartumData>) {
        rv_trimester_topic.layoutManager = LinearLayoutManager(activity)
        rv_trimester_topic.adapter = PostpartumAdapter(requireContext(),trimesterTopics)
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