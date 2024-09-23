package com.acidriian.wallet

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.acidriian.wallet.adapters.WalletCardAdapter
import com.acidriian.wallet.databinding.ActivityMainBinding
import com.acidriian.wallet.fragments.PaymentFragment
import com.acidriian.wallet.model.WalletCard
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var walletCardAdapter: WalletCardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpWalletCards()
        binding.walletCardViewPager.adapter = walletCardAdapter

        setupWalletCardIndicators()
        setCurrentWalletCardIndicator(0)

        binding.walletCardViewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    setCurrentWalletCardIndicator(position)
                }
            }
        )

        binding.closeMenuButton.setOnClickListener {
            if (binding.walletCardViewPager.currentItem + 1 < walletCardAdapter.itemCount){
                binding.walletCardViewPager.currentItem += 1
            }
        }

        val pagerAdapter = ViewPagerAdapter(this)
        binding.subscriptionPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.subscriptionPager){ tab, position ->
            when(position){
                0 -> tab.icon = ResourcesCompat.getDrawable(resources, R.drawable.icons8_pay_60px, null)
                1 -> tab.icon = ResourcesCompat.getDrawable(resources, R.drawable.icons8_stack_of_coins_60px, null)
                2 -> tab.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_menu_book_24, null)
                3 -> tab.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_info_24, null)
            }
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

        })
    }

    private fun setUpWalletCards(){
        val walletCardItems: MutableList<WalletCard> = ArrayList()
        val card01 = WalletCard()
        card01.name = "Balance"
        card01.amount = 154000
        card01.image = R.drawable.card_background
        card01.companyLogo = R.drawable.icons8_mastercard_logo_96px

        val card02 = WalletCard()
        card02.name = "Balance"
        card02.amount = 400000
        card02.image = R.drawable.card_background_blue
        card02.companyLogo = R.drawable.icons8_visa_96px

        val card03 = WalletCard()
        card03.name = "Balance"
        card03.amount = 200000
        card03.image = R.drawable.card_background
        card03.companyLogo = R.drawable.icons8_mastercard_logo_96px

        walletCardItems.add(card01)
        walletCardItems.add(card02)
        walletCardItems.add(card03)

        walletCardAdapter.dataList = walletCardItems
        
    }

    private fun setupWalletCardIndicators(){
        val indicators: Array<ImageView?> =
            arrayOfNulls(walletCardAdapter.itemCount)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8,0,8,0)
        for( i in indicators.indices){
            indicators[i] = ImageView(this)
            indicators[i]!!.setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.onboarding_indicator_inactive))
            indicators[i]!!.layoutParams = layoutParams
            binding.walletLinearLayout.addView(indicators[i])
        }
    }

    private fun setCurrentWalletCardIndicator(index: Int){
        val childCount = binding.walletLinearLayout.childCount
        for(i in 0 until childCount){
            val imageView = binding.walletLinearLayout.getChildAt(1) as ImageView
            if(i == index){
                imageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.onboarding_indicator_active))
            }else {
                imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.onboarding_indicator_inactive))
            }
        }
        if(index == walletCardAdapter.itemCount - 1 ){
            binding.closeMenuButton.text = "Swipe"
        }else{
            binding.closeMenuButton.text = "Shuffle"
        }
    }

    class ViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity){
        override fun getItemCount(): Int {
            return CARD_ITEM_SIZE
        }

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> PaymentFragment()
                1 -> PaymentFragment()
                2 -> PaymentFragment()
                3 -> PaymentFragment()
                else -> PaymentFragment()
            }
        }

        companion object {
            private const val CARD_ITEM_SIZE = 4
        }
    }
}