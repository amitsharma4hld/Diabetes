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
import com.s.diabetesfeeding.data.db.entities.CategoryWithItems
import com.s.diabetesfeeding.data.db.entities.ScoreWithCategory
import com.s.diabetesfeeding.ui.adapter.ScoreBoardAdapter
import com.s.diabetesfeeding.util.longToast
import kotlinx.android.synthetic.main.fragment_score_board.*
import kotlinx.coroutines.launch

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
var ScoresWithCategory: List<ScoreWithCategory> = ArrayList()


class ScoreBoardFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_score_board, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Move this to model class with Live Data
        viewLifecycleOwner.lifecycleScope.launch {
            ScoresWithCategory =  AppDatabase(requireActivity().applicationContext).getHomeMenusDao().getScoreWithCategory()

            requireActivity().longToast(ScoresWithCategory[0].ScoreTable.size.toString())
        }

        showScores(scores)
    }
    private fun showScores(scores: List<ScoreboardData>) {
        recyclerViewScores.layoutManager = LinearLayoutManager(activity)
        recyclerViewScores.adapter =
            ScoreBoardAdapter(scores)
    }
    companion object {
        @JvmStatic
        fun newInstance(title: String, bgColorId: Int) =
            ScoreBoardFragment().apply {
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