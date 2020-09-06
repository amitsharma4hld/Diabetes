package com.s.diabetesfeeding.ui.scoreboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.ScoreboardData
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.DistinctDateTimeList
import com.s.diabetesfeeding.data.db.entities.FilterScoreTable
import com.s.diabetesfeeding.data.db.entities.ScoreBoardFinalData
import com.s.diabetesfeeding.data.db.entities.ScoreHashMapData
import com.s.diabetesfeeding.ui.adapter.ScoreBoardAdapter
import kotlinx.android.synthetic.main.fragment_score_board.*
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
val scores = listOf(
    ScoreboardData(1,"","Visit","Record","Observe","Breastfeed",""),
    ScoreboardData(2,"jun 19, 2020","5","10","30","20","65"),
    ScoreboardData(3,"jun 20, 2020","10","20","30","20","80"),
    ScoreboardData(4,"jun 21, 2020","5","20","20","10","55"),
    ScoreboardData(5,"jun 22, 2020","6","14","30","20","70"),
    ScoreboardData(6,"jun 23, 2020","5","10","30","20","65")
)
var ScoresWithCategory: List<FilterScoreTable> = ArrayList()
var DistinctDateTimeList: List<DistinctDateTimeList> = ArrayList()
val listDate: MutableList<String> = ArrayList()
var distinctDate: List<String> = ArrayList()
//var ScoreHashMapDataList: MutableList<ScoreHashMapData> = ArrayList()
val scoreHashMap:HashMap<String,MutableList<ScoreHashMapData>> = HashMap()


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

        viewLifecycleOwner.lifecycleScope.launch {
            context.let {
                ScoresWithCategory =  AppDatabase(it!!).getHomeMenusDao().getFilterScoreTable()
            }
            context.also {
                DistinctDateTimeList =  AppDatabase(it!!).getHomeMenusDao().getDistinctDateTime()
                listDate.clear()
                distinctDate = emptyList()
                for (i in DistinctDateTimeList.indices) {
                    val date = AppDatabase(it).getHomeMenusDao().getDistinctDateTime()[i].date_time
                    val dateToString = date.toString().split("T")
                    listDate.add(dateToString[0])
                }
                distinctDate = listDate.distinct()
            }
            context.also {
                val arraylist=ArrayList<ScoreBoardFinalData>()
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
                    arraylist.add(ScoreBoardFinalData(i,false,ScoreHashMapDataList))
                    showScores(arraylist)
                    //scoreHashMap[distinctDate[i]] = ScoreHashMapDataList
                }
            }
        }
        // showScores(scores)
    }
    private fun showScores(scores: ArrayList<ScoreBoardFinalData>) {
        recyclerViewScores.layoutManager = LinearLayoutManager(activity)
        recyclerViewScores.adapter =
            ScoreBoardAdapter(scores)
    }

    fun splitDateTime(date:String): String {
        val dateToString = date.split("T")
        return dateToString[0]
    }


}