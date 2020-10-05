package com.s.diabetesfeeding.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.entities.breastfeeding.BabyPoopData
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.ui.CellClickListener
import com.s.diabetesfeeding.ui.home.fragment.breastfeeding.BreastfeedingFragmentDirections
import com.s.diabetesfeeding.util.snackbar
import kotlinx.android.synthetic.main.item_breastfeeding_sessions_list.view.cv_breastfeeding
import kotlinx.android.synthetic.main.item_breastfeeding_sessions_list.view.cv_breastfeeding_blank
import kotlinx.android.synthetic.main.item_breastfeeding_sessions_list.view.iv_session_time_blank
import kotlinx.android.synthetic.main.item_diaperchange_session_list_item.view.*


class DiaperChangeSessionAdapter(val sessions : List<BabyPoopData>, private val cellClickListener: CellClickListener) :
    RecyclerView.Adapter<DiaperChangeSessionAdapter.SessionViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        return SessionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_diaperchange_session_list_item, parent, false)
        )
    }

    override fun getItemCount()= sessions.size

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        val session = sessions[position]
        if (sessions[0].poop_pee_time.isNotEmpty()){
            if (session.poop_pee_time.isEmpty() && sessions[position-1].poop_pee_time.isNotEmpty()){
                holder.view.iv_session_time_blank.setImageResource(R.drawable.ic_session_add)
                holder.view.iv_session_time_blank.setOnClickListener {
                    if (prefs.getSavedIsPreviousDate()) {
                        it.snackbar("Previous data can not be edited")
                    }else{
                        val action = BreastfeedingFragmentDirections.actionBreastfeedingFragmentToDiaperChangeFragment()
                        action.babyPoopData = session
                        Navigation.findNavController(it).navigate(action)
                    }

                }
            }
            if (session.poop_pee_time !=""){
                holder.view.tv_session_value.visibility = View.VISIBLE
                holder.view.tv_session_time.visibility = View.VISIBLE
                holder.view.iv_session_time_blank.visibility = View.GONE
                holder.view.cv_breastfeeding_blank.visibility = View.GONE
                holder.view.cv_breastfeeding.visibility = View.VISIBLE
                var reason = ""
                if (session.isPoop && session.isPee){
                    reason = "Poop Pee"
                }else{
                    if (session.isPoop){
                        reason = "Poop"
                    }else if (session.isPee){
                        reason = "Pee"
                    }
                }
                holder.view.tv_session_value.text = reason
                holder.view.tv_session_time.text = session.poop_pee_time
            }else{
                holder.view.tv_session_value.visibility = View.GONE
                holder.view.tv_session_time.visibility = View.GONE
                holder.view.iv_session_time_blank.visibility = View.VISIBLE
                holder.view.cv_breastfeeding_blank.visibility = View.VISIBLE
            }
        }else{
            if (position==0){
                holder.view.iv_session_time_blank.setImageResource(R.drawable.ic_session_add)
                holder.view.iv_session_time_blank.setOnClickListener {
                    if (prefs.getSavedIsPreviousDate()) {
                        it.snackbar("Previous data can not be edited")
                    }else{
                        val action = BreastfeedingFragmentDirections.actionBreastfeedingFragmentToDiaperChangeFragment()
                        action.babyPoopData = session
                        Navigation.findNavController(it).navigate(action)
                    }

                }
            }else{
                holder.view.tv_session_value.visibility = View.GONE
                holder.view.tv_session_time.visibility = View.GONE
                holder.view.iv_session_time_blank.visibility = View.VISIBLE
                holder.view.cv_breastfeeding_blank.visibility = View.VISIBLE
            }
        }


    }

    class SessionViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}