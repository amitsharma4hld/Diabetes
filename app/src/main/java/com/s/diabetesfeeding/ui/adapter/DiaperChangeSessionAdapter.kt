package com.s.diabetesfeeding.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.entities.breastfeeding.BabyPoopData
import com.s.diabetesfeeding.data.db.entities.breastfeeding.BreastFeedingSessionData
import com.s.diabetesfeeding.ui.CellClickListener
import com.s.diabetesfeeding.ui.home.fragment.breastfeeding.BreastfeedingFragmentDirections
import kotlinx.android.synthetic.main.item_breastfeeding_sessions_list.view.*


class DiaperChangeSessionAdapter(val sessions : List<BabyPoopData>, private val cellClickListener: CellClickListener) :
    RecyclerView.Adapter<DiaperChangeSessionAdapter.SessionViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        return SessionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_breastfeeding_sessions_list, parent, false)
        )
    }

    override fun getItemCount()= sessions.size

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        val session = sessions[position]
        if (sessions[0].poop_pee_time.isNotEmpty()){
            if (session.poop_pee_time.isEmpty() && sessions[position-1].poop_pee_time.isNotEmpty()){
                holder.view.iv_session_time_blank.setImageResource(R.drawable.ic_session_add)
                holder.view.iv_session_time_blank.setOnClickListener {
                    val action = BreastfeedingFragmentDirections.actionBreastfeedingFragmentToDiaperChangeFragment()
                    action.babyPoopData = session
                    Navigation.findNavController(it).navigate(action)
                }
            }
            if (session.poop_pee_time !=""){
                holder.view.tv_session_time_value.visibility = View.VISIBLE
                holder.view.iv_session_time_blank.visibility = View.GONE
                holder.view.cv_breastfeeding_blank.visibility = View.GONE
                holder.view.cv_breastfeeding.visibility = View.VISIBLE
                var reason: String = ""
                if (session.isPoop){
                    reason = "Poop"
                }else if (session.isPee){
                    reason = "pee"
                }
                holder.view.tv_session_time_value.text = reason + "\n" +session.poop_pee_time
            }else{
                holder.view.tv_session_time_value.visibility = View.GONE
                holder.view.iv_session_time_blank.visibility = View.VISIBLE
                holder.view.cv_breastfeeding_blank.visibility = View.VISIBLE
            }
        }else{
            if (position==0){
                holder.view.iv_session_time_blank.setImageResource(R.drawable.ic_session_add)
                holder.view.iv_session_time_blank.setOnClickListener {
                    val action = BreastfeedingFragmentDirections.actionBreastfeedingFragmentToDiaperChangeFragment()
                    action.babyPoopData = session
                    Navigation.findNavController(it).navigate(action)
                }
            }else{
                holder.view.tv_session_time_value.visibility = View.GONE
                holder.view.iv_session_time_blank.visibility = View.VISIBLE
                holder.view.cv_breastfeeding_blank.visibility = View.VISIBLE
            }
        }


    }

    class SessionViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}