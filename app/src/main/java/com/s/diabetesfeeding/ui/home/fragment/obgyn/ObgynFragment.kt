package com.s.diabetesfeeding.ui.home.fragment.obgyn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.ui.home.fragment.HomeScreenFragment
import com.s.diabetesfeeding.ui.home.fragment.diabetes.DiabetesFragmentArgs
import com.s.diabetesfeeding.ui.home.fragment.diabetes.DiabetesFragmentDirections
import kotlinx.android.synthetic.main.fragment_diabetes.*
import kotlinx.android.synthetic.main.fragment_obgyn.*
import kotlinx.android.synthetic.main.fragment_prental_visit.*


class ObgynFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val currentDate: String = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(
        java.util.Date())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        tv_today_date_obgyn.text = currentDate
        arguments?.let {
            username_obgy?.text = "Hello "+ DiabetesFragmentArgs.fromBundle(it).name +","
            tv_week_obyn?.text = DiabetesFragmentArgs.fromBundle(it).weekofpreg.toString()
            tv_doctorname_obgyn?.text = DiabetesFragmentArgs.fromBundle(it).doctorname
            tv_time_obgyn?.text = DiabetesFragmentArgs.fromBundle(it).time
        }
        mc_observation.setOnClickListener {
            val action = ObgynFragmentDirections.actionObgynFragmentToObservationFragment()
            Navigation.findNavController(it).navigate(action)
        }
        mc_counseling.setOnClickListener {
            val action = ObgynFragmentDirections.actionObgynFragmentToCounselingFragment()
            Navigation.findNavController(it).navigate(action)
        }
        mc_prenatal_visit.setOnClickListener {
            val action = ObgynFragmentDirections.actionObgynFragmentToPrentalVisitFragment()
            Navigation.findNavController(it).navigate(action)
        }

    }


}