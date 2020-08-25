package com.s.diabetesfeeding.ui.home.fragment.obgyn

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.s.diabetesfeeding.R
import kotlinx.android.synthetic.main.fragment_pre_pregnancy_input.*


class PrePregnancyInputFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pre_pregnancy_input, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        username.setOnClickListener {
            requireActivity().onBackPressed()
        }
        mcv_weight_done.setOnClickListener {
            requireActivity().onBackPressed()
        }
        et_digit_one.requestFocus()
        // show the keyboard if has focus
        showSoftKeyboard(et_digit_one)
    }
    private fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            // here is one more tricky issue
            // imm.showSoftInputMethod doesn't work well
            // and imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0) doesn't work well for all cases too
            imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }

}