package com.example.myapplication.ui.onboarding

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.DimenRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentOnboardingBinding
import com.example.myapplication.onboardingTextAdapterDelegate
import com.example.myapplication.ui.likes.LikesViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.abs

class OnBoardingFragment : Fragment(R.layout.fragment_onboarding) {

    private val viewBinding by viewBinding(FragmentOnboardingBinding::bind)

    val viewModel: OnBoardingViewModel by viewModels()

    private lateinit var player: ExoPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setPlayer()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.playerView.player = player
        viewBinding.viewPager.setTextPages()
        viewBinding.viewPager.attachDots(viewBinding.onboardingTextTabLayout)


        // Adapted from https://stackoverflow.com/questions/10098040/android-viewpager-show-preview-of-page-on-left-and-right
        viewBinding.viewPager.offscreenPageLimit = 1

        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.25f * abs(position))

        }
        viewBinding.viewPager.setPageTransformer(pageTransformer)

        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        viewBinding.viewPager.addItemDecoration(itemDecoration)

        viewBinding.signInButton.setOnClickListener {
            // TODO: Go to SignInFragment.
            Toast.makeText(requireContext(), "Нажата кнопка войти", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_onBoardingFragment_to_signInFragment)
        }
        viewBinding.signUpButton.setOnClickListener {
            // TODO: Go to SignUpFragment.
            Toast.makeText(requireContext(), "Нажата кнопка зарегистрироваться", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(R.id.action_onBoardingFragment_to_signUpFragment)
        }

        viewBinding.volumeControlButton.setOnClickListener {
            if (viewModel.isVolume) {
                player.volume = 0F
                viewModel.isVolume = false
                viewBinding.volumeControlButton.setImageResource(R.drawable.ic_volume_off_white_24dp)
            } else {
                player.volume = 1F
                viewModel.isVolume = true
                viewBinding.volumeControlButton.setImageResource(R.drawable.ic_volume_up_white_24dp)
            }
        }

        viewBinding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    viewModel.userTouchTime = System.currentTimeMillis()
                }
            }
        )

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect {
                        viewState ->
                    Log.d("OnBoarding", "Fragment collect wake up")
                    viewBinding.viewPager.scrollCycle(viewBinding.viewPager.adapter?.itemCount ?: 0)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        player.pause()
    }

    override fun onResume() {
        super.onResume()
        player.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }

    private fun setPlayer() {
        player = SimpleExoPlayer.Builder(requireContext()).build().apply {
            addMediaItem(MediaItem.fromUri("asset:///onboarding.mp4"))
            repeatMode = Player.REPEAT_MODE_ALL
            prepare()
        }
    }

    private fun ViewPager2.setTextPages() {
        adapter =
            ListDelegationAdapter(onboardingTextAdapterDelegate()).apply {
                items =
                    listOf(
                        getString(R.string.onboarding_view_pager_text_1),
                        getString(R.string.onboarding_view_pager_text_2),
                        getString(R.string.onboarding_view_pager_text_3)
                    )
            }
    }

    private fun ViewPager2.attachDots(tabLayout: TabLayout) {
        TabLayoutMediator(tabLayout, this) { _, _ -> }.attach()
    }

    private fun ViewPager2.scrollCycle(size: Int) {
        if (this.currentItem < size - 1) {
            this.setCurrentItem(this.currentItem + 1, true);
        } else {
            this.setCurrentItem(0, true);
        }
    }

    class HorizontalMarginItemDecoration(context: Context, @DimenRes horizontalMarginInDp: Int) :
        RecyclerView.ItemDecoration() {

        private val horizontalMarginInPx: Int =
            context.resources.getDimension(horizontalMarginInDp).toInt()

        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
        ) {
            outRect.right = horizontalMarginInPx
            outRect.left = horizontalMarginInPx
        }

    }

}