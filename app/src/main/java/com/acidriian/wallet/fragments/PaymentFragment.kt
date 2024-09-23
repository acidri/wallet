package com.acidriian.wallet.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.acidriian.wallet.common.makeToast
import com.acidriian.wallet.databinding.FragmentPaymentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PaymentFragment : Fragment() {

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!
    private val screenName: String = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(screenName, "Payment fragment")
        clickEvents()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun clickEvents(){
        binding.fabWalletIcon1.setOnClickListener {
            dialogUi(binding.packageDaily.text.toString(), binding.packageDailyAmount.text.toString())
        }
        binding.fabWalletIcon2.setOnClickListener {
            dialogUi(binding.packageWeekly.text.toString(), binding.packageWeeklyAmount.text.toString())
        }
        binding.fabWalletIcon3.setOnClickListener {
            dialogUi(binding.packageMonthly.text.toString(), binding.packageMonthlyAmount.text.toString())
        }
        binding.fabWalletIcon4.setOnClickListener {
            dialogUi(binding.packageAnnually.text.toString(), binding.packageAnnuallyAmount.text.toString())
        }
    }

    private fun dialogUi(packageName: String, packageAmount: String){
        val payDialog = MaterialAlertDialogBuilder(requireContext())
        payDialog.setTitle("Subscribe to Our Packages")
            .setMessage("You have selected $packageName with amount $packageAmount")
            .setPositiveButton("Pay"){
                    _, _ ->
                this.makeToast("Paying for $packageName at $packageAmount")
            }
            .setNegativeButton("Cancel"){
                    _, _ ->
                this.makeToast("Transaction canceled")
            }.show()

    }
}