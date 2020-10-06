package com.s.diabetesfeeding.ui.scoreboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.DistinctDateTimeList
import com.s.diabetesfeeding.data.db.entities.FilterScoreTable
import com.s.diabetesfeeding.data.db.entities.ScoreBoardFinalData
import com.s.diabetesfeeding.data.db.entities.ScoreHashMapData
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.ui.adapter.ScoreBoardAdapter
import kotlinx.android.synthetic.main.fragment_score_board.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


var ScoresWithCategory: List<FilterScoreTable> = ArrayList()
var DistinctDateTimeList: List<DistinctDateTimeList> = ArrayList()
val listDate: MutableList<String> = ArrayList()
var distinctDate: List<String> = ArrayList()


class ScoreBoardFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_score_board, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var currentDate:String = ""
        currentDate = if (!prefs.getOffsetDateTime().isNullOrEmpty()){
            val dateToString = prefs.getOffsetDateTime()!!.toString().split("T")
            dateToString[0]
        }else{
            getCurrentDateInString()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            context.let {
                ScoresWithCategory =  AppDatabase(it!!).getHomeMenusDao().getFilterScoreTable()
            }
            context.also {
                pb_loading.visibility=View.VISIBLE
                DistinctDateTimeList =  AppDatabase(it!!).getHomeMenusDao().getDistinctDateTime()
                listDate.clear()
                distinctDate = emptyList()
                for (i in DistinctDateTimeList.indices) {
                    val date = AppDatabase(it).getHomeMenusDao().getDistinctDateTime()[i].date_time
                    val dateToString = date.toString().split("T")
                    if (currentDate == dateToString[0]) {
                        listDate.add(dateToString[0])
                    }
                }
                distinctDate = listDate.distinct()
                pb_loading.visibility=View.GONE
            }
            context.also {
                if (listDate.isNullOrEmpty()) {
                    tv_no_data_found.visibility = View.VISIBLE
                    return@also
                }else{
                    tv_no_data_found.visibility = View.GONE
                    pb_loading.visibility=View.VISIBLE
                    val arraylist=ArrayList<ScoreBoardFinalData>()
                    var totalScore: Int = 0
                    for (i in distinctDate.indices){
                        val ScoreHashMapDataList: MutableList<ScoreHashMapData> = ArrayList()
                        for (j in ScoresWithCategory.indices){
                            if (distinctDate[i] == splitDateTime(ScoresWithCategory[j].date_time.toString())){
                                val data = ScoreHashMapData(ScoresWithCategory[j].homeMenuId,ScoresWithCategory[j].menuName,ScoresWithCategory[j].sub_menuId,
                                    ScoresWithCategory[j].subMenuName,ScoresWithCategory[j].score_type_id,ScoresWithCategory[j].score_type,
                                    ScoresWithCategory[j].date_time,distinctDate[i],ScoresWithCategory[j].score)

                                ScoreHashMapDataList.add(data)
                            }
                        }

                        totalScore += ScoreHashMapDataList.map { it.subMenuScore }.sum()
                        arraylist.add(ScoreBoardFinalData(i,false,ScoreHashMapDataList))
                        tv_current_streat_days_count.text = arraylist.size.toString() + " Days"
                        your_total_score_value.text = totalScore.toString()
                        showScores(arraylist)
                        ll_scoretype_title.visibility=View.VISIBLE
                        pb_loading.visibility=View.GONE
                    }
                }
            }
        }
    }
    private fun showScores(scores: ArrayList<ScoreBoardFinalData>) {
        recyclerViewScores.layoutManager = LinearLayoutManager(activity)
        (recyclerViewScores.layoutManager as LinearLayoutManager).reverseLayout = true
        (recyclerViewScores.layoutManager as LinearLayoutManager).stackFromEnd = true
        recyclerViewScores.adapter = ScoreBoardAdapter(requireContext(), scores)
    }

    fun splitDateTime(date:String): String {
        val dateToString = date.split("T")
        return dateToString[0]
    }
    private fun getCurrentDateInString():String{
        val currentDate: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        return currentDate
    }

}