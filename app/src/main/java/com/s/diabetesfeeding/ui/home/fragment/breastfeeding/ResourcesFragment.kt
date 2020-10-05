package com.s.diabetesfeeding.ui.home.fragment.breastfeeding

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.s.diabetesfeeding.R
import kotlinx.android.synthetic.main.fragment_resources.*


class ResourcesFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mcv_done_resources.setOnClickListener {
            requireActivity().onBackPressed()
        }
        tv_link_first.movementMethod = LinkMovementMethod.getInstance()
        tv_link_second.movementMethod = LinkMovementMethod.getInstance()
        tv_link_third.movementMethod = LinkMovementMethod.getInstance()
        tv_link_fourth.movementMethod = LinkMovementMethod.getInstance()
        tv_link_fifth.movementMethod = LinkMovementMethod.getInstance()
        tv_link_sixth.movementMethod = LinkMovementMethod.getInstance()
        tv_link_seventh.movementMethod = LinkMovementMethod.getInstance()
        tv_link_eighth.movementMethod = LinkMovementMethod.getInstance()
        tv_link_ninth.movementMethod = LinkMovementMethod.getInstance()
        tv_link_tenth.movementMethod = LinkMovementMethod.getInstance()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resources, container, false)
    }


}