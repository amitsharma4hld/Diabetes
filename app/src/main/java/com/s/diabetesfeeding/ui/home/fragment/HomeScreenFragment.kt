package com.s.diabetesfeeding.ui.home.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.databinding.FragmentHomeScreenBinding
import com.s.diabetesfeeding.ui.auth.LoginActivity
import com.s.diabetesfeeding.ui.home.HomeViewModel
import com.s.diabetesfeeding.ui.home.HomeViewModelFactory
import com.s.diabetesfeeding.ui.home.InsulinFragment
import kotlinx.android.synthetic.main.fragment_home_screen.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_TITLE = "arg_title"
private const val ARG_BG_COLOR = "arg_bg_color"


class HomeScreenFragment : Fragment(), KodeinAware {
    override val kodein by kodein()
    private lateinit var viewModel: HomeViewModel
    private var param1: String? = null
    private var param2: String? = null
    private val factory:HomeViewModelFactory by instance()
    var isOptionSelected = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        // This callback will only be called when MyFragment is at least Started.
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            // Handle the back button event
            if (isOptionSelected){
                ll_options.visibility = View.VISIBLE
                diabetes_options.visibility = View.GONE
                isOptionSelected = false
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentHomeScreenBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home_screen,container,false)
        viewModel = ViewModelProvider(this,factory).get(HomeViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mc_logout.setOnClickListener {
            activity?.let{
                val intent = Intent (it, LoginActivity::class.java)
                it.startActivity(intent)
                activity?.finish()
            }
        }
        tv_today.setOnClickListener {
            mc_week.visibility = View.GONE
            mc_month.visibility = View.GONE
            mc_all.visibility = View.GONE
            mc_today.visibility = View.VISIBLE
        }

        tv_week.setOnClickListener {
            mc_week.visibility = View.VISIBLE
            mc_month.visibility = View.GONE
            mc_all.visibility = View.GONE
            mc_today.visibility = View.GONE
        }
        tv_month.setOnClickListener {
            mc_week.visibility = View.GONE
            mc_month.visibility = View.VISIBLE
            mc_all.visibility = View.GONE
            mc_today.visibility = View.GONE
        }
        tv_all.setOnClickListener {
            mc_week.visibility = View.GONE
            mc_month.visibility = View.GONE
            mc_all.visibility = View.VISIBLE
            mc_today.visibility = View.GONE
        }
        Diabetes.setOnClickListener {
            addFragment(
                viewModel.user.value?.display_name?.let { it1 ->
                    DiabetesFragment.newInstance(
                        it1,
                        R.color.colorAccent
                    )
                }
            )
        }
        OBGYN.setOnClickListener {
            addFragment(
                viewModel.user.value?.display_name?.let { it1 ->
                    ObgynFragment.newInstance(
                        it1,
                        R.color.colorAccent
                    )
                }
            )
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

    companion object {
        @JvmStatic
        fun newInstance(title: String, bgColorId: Int) =
            HomeScreenFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putInt(ARG_BG_COLOR, bgColorId)
                }
            }
    }

}