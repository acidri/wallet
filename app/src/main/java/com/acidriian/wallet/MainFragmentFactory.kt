package com.acidriian.wallet

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.acidriian.wallet.fragments.PaymentFragment
import javax.inject.Inject

class MainFragmentFactory
@Inject
constructor(): FragmentFactory()
{
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            PaymentFragment::class.java.name -> { PaymentFragment() }
            else -> super.instantiate(classLoader, className)
        }
    }
}