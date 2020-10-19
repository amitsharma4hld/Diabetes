package com.s.diabetesfeeding.ui.adapter

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.entities.auth.Data
import com.s.diabetesfeeding.ui.auth.OnRoleUpdateListener
import com.s.diabetesfeeding.util.shortToast
import kotlinx.android.synthetic.main.clinical_patient_list_item.view.*
import kotlinx.android.synthetic.main.item_admin_user_list.view.*

class AdminAdapter (private val context: Context,
                    private val onRoleUpdateListener: OnRoleUpdateListener,
                    private val patientsDataList : List<com.s.diabetesfeeding.data.db.entities.auth.Data>
) : RecyclerView.Adapter<AdminAdapter.UsersDataListViewHolder>() {

    class UsersDataListViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersDataListViewHolder {
        return UsersDataListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_admin_user_list, parent, false)
        )
    }

    override fun getItemCount()= patientsDataList.size
    override fun onBindViewHolder(holder: UsersDataListViewHolder, position: Int) {
        var data = patientsDataList[position]
        holder.view.tv_user_name.text = data.display_name
        holder.view.tv_role.text = data.role
        val url: String = data.profileImage
        if (!url.isNullOrEmpty()){
            Glide.with(context)
                .load(url)
                .centerCrop()
                .transform(CircleCrop())
                .error(R.drawable.ic_baseline_account_circle_24)
                .placeholder(R.drawable.ic_baseline_account_circle_24)
                .into(holder.view.iv_image_user)

        }

        holder.view.tv_view_role_button.setOnClickListener {
            showSelectRoleDialog(data)
        }
    }

    private fun showSelectRoleDialog(data: Data) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.custome_select_role_dialog)

        val endocrinologist = dialog.findViewById(R.id.tv_endocrinologist) as TextView
        val obstetricsGynecology = dialog.findViewById(R.id.tv_obstetrics_gynecology) as TextView
        val neo = dialog.findViewById(R.id.tv_neo) as TextView
        val lactationSpecialist = dialog.findViewById(R.id.tv_lactation_specialist) as TextView
        val socialWorker = dialog.findViewById(R.id.tv_social_worker) as TextView
        val clinicalAssistant = dialog.findViewById(R.id.tv_clinical_assistant) as TextView
        val dayCareProvider = dialog.findViewById(R.id.tv_day_care_provider) as TextView
        val patient = dialog.findViewById(R.id.tv_patient) as TextView
        val administrator = dialog.findViewById(R.id.tv_administrator) as TextView

        val mBtn = dialog.findViewById(R.id.tv_close_button) as TextView

        endocrinologist.setOnClickListener {
            data.role = "endocrinologist"
            updateRole(data.ID,data.role)
            dialog.dismiss()
        }
        obstetricsGynecology.setOnClickListener {
            data.role = "obstetricsGynecology"
            updateRole(data.ID,data.role)
            dialog.dismiss()
        }
        neo.setOnClickListener {
            data.role = "neo"
            updateRole(data.ID,data.role)
            dialog.dismiss()
        }
        lactationSpecialist.setOnClickListener {
            data.role = "lactationSpecialist"
            updateRole(data.ID,data.role)
            dialog.dismiss()
        }
        socialWorker.setOnClickListener {
            data.role = "socialWorker"
            updateRole(data.ID,data.role)
            dialog.dismiss()
        }
        clinicalAssistant.setOnClickListener {
            data.role = "clinicalAssistant"
            updateRole(data.ID,data.role)
            dialog.dismiss()
        }
        dayCareProvider.setOnClickListener {
            data.role = "dayCareProvider"
            updateRole(data.ID,data.role)
            dialog.dismiss()
        }
        patient.setOnClickListener {
            data.role = "patient"
            updateRole(data.ID,data.role)
            dialog.dismiss()
        }
        administrator.setOnClickListener {
            data.role = "administrator"
            updateRole(data.ID,data.role)
            dialog.dismiss()
        }

        mBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun updateRole(id:String, role:String){
        if (id.isNotEmpty() && role.isNotEmpty()){
            onRoleUpdateListener.onRoleUpdateCall(id,role)
            notifyDataSetChanged()
        }else{
            context.shortToast("Invalid User or Role")
        }
    }
}