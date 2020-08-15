package com.s.diabetesfeeding.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.entities.MonitorbloodGlucose
import com.s.diabetesfeeding.ui.home.MonitorBloodGlucoseViewModelFactory
import com.s.diabetesfeeding.ui.home.MonitorBloodGlucoseViewModel
import com.s.diabetesfeeding.util.Coroutines
import com.s.diabetesfeeding.util.longToast
import com.s.diabetesfeeding.util.shortToast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.monitor_blood_glucose_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class MonitorBloodGlucoseFragment : Fragment(), KodeinAware {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewModel : MonitorBloodGlucoseViewModel
    override val kodein by kodein()
    private val factory: MonitorBloodGlucoseViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        //requireActivity()
     /*   val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            // Handle the back button event
        remove()

        }*/
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.monitor_blood_glucose_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,factory).get(MonitorBloodGlucoseViewModel::class.java)
        buildUI()
     /*   Coroutines.main {
            val monitorbloodGlucose = viewModel.monitorbloodGlucose.await()
            monitorbloodGlucose.observe(viewLifecycleOwner, Observer {
                println(it.toString())
                context?.shortToast(it.size.toString())
            })
        }*/
  /*      Coroutines.main {
            val symptom = viewModel.symptom.await()
            symptom.observe(viewLifecycleOwner, Observer {
                println(it.toString())
                context?.shortToast(it.size.toString())
            })
        }*/
    }
    private fun buildUI() = Coroutines.main {
        viewModel.monitorbloodGlucose.await().observe(viewLifecycleOwner, Observer {
            initRecycleView(it.toMonitorBloodGlucoseItem())
            context?.shortToast(it.size.toString())
        })
    }

    private fun initRecycleView(MonitorBloodGlucoseItem: List<MonitorBloodGlucoseChildItem>) {
        val mAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(MonitorBloodGlucoseItem)
        }
        recyclerViewMonitorBloodGlucose.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    private fun List<MonitorbloodGlucose>.toMonitorBloodGlucoseItem() : List<MonitorBloodGlucoseChildItem>{
        return this.map {
            MonitorBloodGlucoseChildItem(it)
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(title: String, bgColorId: Int) =
            MonitorBloodGlucoseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putInt(ARG_BG_COLOR, bgColorId)
                }
            }

        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        private const val ARG_TITLE = "arg_title"
        private const val ARG_BG_COLOR = "arg_bg_color"
    }
}