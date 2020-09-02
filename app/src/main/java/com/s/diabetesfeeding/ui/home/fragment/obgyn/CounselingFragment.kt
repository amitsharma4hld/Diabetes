package com.s.diabetesfeeding.ui.home.fragment.obgyn

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.obgynentities.PostPartumData
import com.s.diabetesfeeding.data.db.entities.obgynentities.TrimesterDataOne
import com.s.diabetesfeeding.data.db.entities.obgynentities.TrimesterDataThree
import com.s.diabetesfeeding.data.db.entities.obgynentities.TrimesterDataTwo
import com.s.diabetesfeeding.ui.home.fragment.HomeScreenFragment
import kotlinx.android.synthetic.main.fragment_counseling.*
import kotlinx.coroutines.launch


class CounselingFragment : Fragment() {
    // val current_date = OffsetDateTime.now()
    private val trimesterTopicsOne = listOf(
        TrimesterDataOne(0, "HIV and Other Routine Parental Test", "", null, false),
        TrimesterDataOne(0, "Risk Factors Identified by Parental History", "", null, false),
        TrimesterDataOne(0, "Anticipated Course of Parental Care", "", null, false),
        TrimesterDataOne(0, "Nutrition and Weight Gain Counseling", "", null, false),
        TrimesterDataOne(0, "HIV and Other Routine Parental Test", "", null, false),
        TrimesterDataOne(0, "Anticipated Course of Parental Care", "", null, false),
        TrimesterDataOne(0, "Risk Factors Identified by Parental History", "", null, false)
    )

    private val trimesterTopicsTwo = listOf(
        TrimesterDataTwo(0, "Signs and Symptoms of Preterm Labour", "", "", false),
        TrimesterDataTwo(0, "Abnormal Lab Values", "", "", false),
        TrimesterDataTwo(0, "Influenza Vaccine", "", "", false),
        TrimesterDataTwo(0, "Selecting a Newborn Care Provider", "", "", false),
        TrimesterDataTwo(0, "Toxoplasmosis Precautions", "", "", false),
        TrimesterDataTwo(0, "Smoking Counseling", "", "", false)
    )

    private val trimesterTopicsThree = listOf(
        TrimesterDataThree(0, "Anesthesia/Analgesia Plans", "comments", "", false),
        TrimesterDataThree(0, "Fetal Movement Monitoring", "comments", "", false),
        TrimesterDataThree(0, "Labour Signs", "comments", "", false),
        TrimesterDataThree(0, "VBAC Counseling", "comments", "", false),
        TrimesterDataThree(0, "Signs and Symptoms of Pregnancy-Induced", "comments", "", false),
        TrimesterDataThree(0, "Post term Counseling", "comments", "", false)
    )
    private val postPartumData = listOf(
        PostPartumData(0, "Depression/Anxiety", "", "", false),
        PostPartumData(0, "Infant Feeding Problems", "", "", false),
        PostPartumData(0, "Birth Experience", "", "", false),
        PostPartumData(0, "Glucose Screen(if GDM)", "", "", false),
        PostPartumData(0, "Toxoplasmosis Precautions", "", "", false),
        PostPartumData(0, "Zika Assessment, Testing (When indicated), and Counseling", "", "", false)
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        viewLifecycleOwner.lifecycleScope.launch {
            context?.let {
                if (AppDatabase(it).getObgynDao().getAllTrimesterOne().isNullOrEmpty()){
                    AppDatabase(it).getObgynDao().saveAllTrimesterOne(trimesterTopicsOne)
                }
            }
            context?.let {
                if (AppDatabase(it).getObgynDao().getAllTrimesterTwo().isNullOrEmpty()){
                    AppDatabase(it).getObgynDao().saveAllTrimesterTwo(trimesterTopicsTwo)
                }
            }
            context?.let {
                if (AppDatabase(it).getObgynDao().getAllTrimesterThree().isNullOrEmpty()){
                    AppDatabase(it).getObgynDao().saveAllTrimesterThree(trimesterTopicsThree)
                }
            }
            context?.let {
                if (AppDatabase(it).getObgynDao().getAllPostPartumData().isNullOrEmpty()){
                    AppDatabase(it).getObgynDao().saveAllPostPartumData(postPartumData)
                    Log.d("AppDatabase : ", "observationList Saved ${AppDatabase(it).getObgynDao().getAllPostPartumData().size} added")
                }
            }
        }

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

    fun addFragment(fragment: Fragment?) {
        val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.add(R.id.container, fragment!!)
        transaction.addToBackStack(HomeScreenFragment.toString())
        transaction.commit()
    }
}