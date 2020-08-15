package com.s.diabetesfeeding.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.s.diabetesfeeding.R



class InsulinFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(InsulinFragment.ARG_PARAM1)
            param2 = it.getString(InsulinFragment.ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insulin, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(title: String, bgColorId: Int) =
            InsulinFragment().apply {
                arguments = Bundle().apply {
                    putString(InsulinFragment.ARG_TITLE, title)
                    putInt(InsulinFragment.ARG_BG_COLOR, bgColorId)
                }
            }
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        private const val ARG_TITLE = "arg_title"
        private const val ARG_BG_COLOR = "arg_bg_color"
    }
}