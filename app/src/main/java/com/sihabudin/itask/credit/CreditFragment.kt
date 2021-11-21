package com.sihabudin.itask.credit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sihabudin.itask.R
import com.sihabudin.itask.databinding.FragmentCreditBinding


class CreditFragment : DialogFragment() {
    private var _binding: FragmentCreditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val creditToast = """
            Thanks to pranavpandey
            github.com/pranavpandey/dynamic-toasts
        """.trimIndent()
        binding.tvCreditToast.text = creditToast

        binding.imageCloseAddTask.setOnClickListener {
            dismiss()
        }
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }
}