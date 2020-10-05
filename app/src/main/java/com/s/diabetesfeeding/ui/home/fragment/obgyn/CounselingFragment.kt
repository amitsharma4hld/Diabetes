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
import com.s.diabetesfeeding.data.db.entities.obgynentities.*
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.ui.home.fragment.HomeScreenFragment
import com.s.diabetesfeeding.util.Coroutines
import com.s.diabetesfeeding.util.snackbar
import kotlinx.android.synthetic.main.fragment_counseling.*
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime


class CounselingFragment : Fragment() {
    // val current_date = OffsetDateTime.now()
    private var progressAllTrimester:List<ProgressAllTrimester> = ArrayList()

    private val trimesterTopicsOne = listOf(
        TrimesterDataOne(1, "HIV and Other Routine Parental Test", "", "", false),
        TrimesterDataOne(2, "Risk Factors Identified by Parental History", "", "", false),
        TrimesterDataOne(3, "Anticipated Course of Parental Care", "", "", false),
        TrimesterDataOne(4, "Nutrition and Weight Gain Counseling", "", "", false),
        TrimesterDataOne(5, "Toxoplasmosis Precautions", "", "", false),
        TrimesterDataOne(6, "Sexual Activity", "", "", false)
    )

    private val trimesterTopicsTwo = listOf(
        TrimesterDataTwo(1, "Signs and Symptoms of Preterm Labour", "", "", false),
        TrimesterDataTwo(2, "Abnormal Lab Values", "", "", false),
        TrimesterDataTwo(3, "Influenza Vaccine", "", "", false),
        TrimesterDataTwo(4, "Selecting a Newborn Care Provider", "", "", false),
        TrimesterDataTwo(5, "Toxoplasmosis Precautions", "", "", false),
        TrimesterDataTwo(6, "Smoking Counseling", "", "", false)
    )

    private val trimesterTopicsThree = listOf(
        TrimesterDataThree(1, "Anesthesia/Analgesia Plans", "", "", false),
        TrimesterDataThree(2, "Fetal Movement Monitoring", "", "", false),
        TrimesterDataThree(3, "Labour Signs", "", "", false),
        TrimesterDataThree(4, "VBAC Counseling", "", "", false),
        TrimesterDataThree(5, "Signs and Symptoms of Pregnancy-Induced", "", "", false),
        TrimesterDataThree(6, "Post term Counseling", "", "", false)
    )
    private val postPartumData = listOf(
        PostPartumData(1, "Depression/Anxiety", "", "", false),
        PostPartumData(2, "Infant Feeding Problems", "", "", false),
        PostPartumData(3, "Birth Experience", "", "", false),
        PostPartumData(4, "Glucose Screen(if GDM)", "", "", false),
        PostPartumData(5, "Toxoplasmosis Precautions", "", "", false),
        PostPartumData(6, "Zika Assessment, Testing (When indicated), and Counseling", "", "", false)
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
        if (!prefs.getOffsetDateTime().isNullOrEmpty()) {
            val fromDate = OffsetDateTime.parse(prefs.getFromOffsetDateTime())
            val toDate = OffsetDateTime.parse(prefs.getOffsetDateTime())
            Coroutines.io {
                // TrimesterOne
                context?.let {
                    progressAllTrimester = emptyList()
                    progressAllTrimester = AppDatabase(it).getObgynDao()
                        .getAllTrimesterProgressBetweenDates(1,fromDate,toDate)
                }
                context?.let {
                    if (progressAllTrimester.isEmpty()) {
                        AppDatabase(it).getObgynDao().saveAllTrimesterOne(trimesterTopicsOne)
                    }else {
                        AppDatabase(it).getObgynDao().saveAllTrimesterOne(trimesterTopicsOne)
                        for (i in progressAllTrimester.indices){
                            AppDatabase(it).getObgynDao()
                                .updateTrimesterOneColumn(
                                    progressAllTrimester[i].title,
                                    progressAllTrimester[i].comment,
                                    progressAllTrimester[i].isChecked,
                                    progressAllTrimester[i].date)
                        }
                    }
                }
                // TrimesterTwo
                context?.let {
                    progressAllTrimester = emptyList()
                    progressAllTrimester = AppDatabase(it).getObgynDao()
                        .getAllTrimesterProgressBetweenDates(2,fromDate,toDate)
                }
                context?.let {
                    if (progressAllTrimester.isEmpty()) {
                        AppDatabase(it).getObgynDao().saveAllTrimesterTwo(trimesterTopicsTwo)
                    }else {
                        AppDatabase(it).getObgynDao().saveAllTrimesterTwo(trimesterTopicsTwo)
                        for (i in progressAllTrimester.indices){
                            AppDatabase(it).getObgynDao()
                                .updateTrimesterTwoColumn(
                                    progressAllTrimester[i].title,
                                    progressAllTrimester[i].comment,
                                    progressAllTrimester[i].isChecked,
                                    progressAllTrimester[i].date)
                        }
                    }
                }

                // TrimesterThree
                context?.let {
                    progressAllTrimester = emptyList()
                    progressAllTrimester = AppDatabase(it).getObgynDao()
                        .getAllTrimesterProgressBetweenDates(3,fromDate,toDate)
                }
                context?.let {
                    if (progressAllTrimester.isEmpty()) {
                        AppDatabase(it).getObgynDao().saveAllTrimesterThree(trimesterTopicsThree)
                    }else {
                        AppDatabase(it).getObgynDao().saveAllTrimesterThree(trimesterTopicsThree)
                        for (i in progressAllTrimester.indices){
                            AppDatabase(it).getObgynDao()
                                .updateTrimesterThreeColumn(
                                    progressAllTrimester[i].title,
                                    progressAllTrimester[i].comment,
                                    progressAllTrimester[i].isChecked,
                                    progressAllTrimester[i].date)
                        }
                    }
                }

                // PostPartum (Trimester Four)
                context?.let {
                    progressAllTrimester = emptyList()
                    progressAllTrimester = AppDatabase(it).getObgynDao()
                        .getAllTrimesterProgressBetweenDates(4,fromDate,toDate)
                }
                context?.let {
                    if (progressAllTrimester.isEmpty()) {
                        AppDatabase(it).getObgynDao().saveAllPostPartumData(postPartumData)
                    }else {
                        AppDatabase(it).getObgynDao().saveAllPostPartumData(postPartumData)
                        for (i in progressAllTrimester.indices){
                            AppDatabase(it).getObgynDao()
                                .updateTrimesterPostPartumColumn(
                                    progressAllTrimester[i].title,
                                    progressAllTrimester[i].comment,
                                    progressAllTrimester[i].isChecked,
                                    progressAllTrimester[i].date)
                        }
                    }
                }
            }

        }else{
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