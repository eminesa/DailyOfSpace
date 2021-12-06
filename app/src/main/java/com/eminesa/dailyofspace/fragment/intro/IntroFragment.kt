package com.eminesa.dailyofspace.fragment.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.eminesa.dailyofspace.R
import com.eminesa.dailyofspace.adapter.IntroAdapter
import com.eminesa.dailyofspace.databinding.FragmentIntroBinding
import com.eminesa.dailyofspace.enum.Behavior
import com.eminesa.dailyofspace.model.IntroModel
import com.eminesa.dailyofspace.util.attachSnapHelperWithListener
import com.yerli.sosyal.utils.storage.LocaleStorageManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroFragment : Fragment(), OnSnapPositionChangeListener {

    private var introBinding: FragmentIntroBinding? = null
    private var getPosition: Int = 0
    private var adapter: IntroAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (introBinding == null)
            introBinding = FragmentIntroBinding.inflate(inflater)

        createDots(0)

        introBinding?.recyclerOnBoarding?.attachSnapHelperWithListener(
            PagerSnapHelper(),
            Behavior.NOTIFY_ON_SCROLL_STATE_IDLE,
            this
        )
        setRecyclerView()

        introBinding?.onClickListener()
        return introBinding?.root
    }

    /**
     * Bu fonksiyon kullanılan recyclerview'i ve IntroAdapter'ı initial eder.
     */
    private fun setRecyclerView() {
         adapter = IntroAdapter()

        introBinding?.apply {
            recyclerOnBoarding.adapter = adapter
            recyclerOnBoarding.setHasFixedSize(true)
        }

        val onBoardList = mutableListOf<IntroModel>()
        onBoardList.add(
            IntroModel(
                getString(R.string.onboarding_title_1),
                R.drawable.ic_discover_page,
                getString(R.string.onboarding_desc_1),
            )
        )
        onBoardList.add(
            IntroModel(
                getString(R.string.onboarding_title_2),
                R.drawable.ic_notice_page,
                getString(R.string.onboarding_desc_2),
            )
        )
        onBoardList.add(
            IntroModel(
                getString(R.string.onboarding_title_3),
                R.drawable.ic_download_page,
                getString(R.string.onboarding_desc_3),
            )
        )
        adapter?.submitList(onBoardList)
    }

    private fun sendArgument() {

        if (arguments != null) {

            val date = arguments?.getString("date")
            val explanation = arguments?.getString("explanation")
            val title = arguments?.getString("title")
            val mediaType = arguments?.getString("media_type")
            val url = arguments?.getString("url")
            LocaleStorageManager.setPreferences("intro", true)

            findNavController().navigate(
                R.id.action_introFragment_to_dailyPhotoFragment,
                bundleOf(
                    "date" to date,
                    "explanation" to explanation,
                    "title" to title,
                    "mediaType" to mediaType,
                    "url" to url,
                )
            )
        }
    }

    override fun onSnapPositionChange(position: Int) {
        getPosition = position
        introBinding?.apply {
            if (position == 2) {
                btnListItemNext.text = getString(R.string.welcome_to_space)
                btnListItemJump.visibility = View.INVISIBLE
            } else {
                btnListItemNext.text = getString(R.string.button_continue)
                btnListItemJump.visibility = View.VISIBLE
            }
        }
        createDots(position)
    }

    /**
     * Bu fonksiyon ana sayfanın alt kısımda bulunan noktaların active inactive durumunun kkontolünü sağlar.
     * @param currentPosition bulunan sayfanın pozisyonu
     */
    private fun createDots(currentPosition: Int) {
        introBinding?.apply {
            dotLayout.removeAllViews()

            val dots: Array<ImageView?> = arrayOfNulls(3)

            for (i in 0..2) {
                dots[i] = ImageView(requireContext())
                if (i == currentPosition) {
                    dots[i]?.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_active_dot
                        )
                    )
                } else {
                    dots[i]!!.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_default_dot
                        )
                    )
                }
                val params = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(5, 0, 5, 0)

                dotLayout.addView(dots[i], params)
            }
        }
    }

    /**
     * Bu fonksiyon sayfada bulunan tüm clickleri yönetir
     */
    private fun FragmentIntroBinding.onClickListener() {

        btnListItemNext.setOnClickListener {

            recyclerOnBoarding.post {
                recyclerOnBoarding.smoothScrollToPosition(
                    getPosition + 1
                )
            }

            if (getPosition == 2) {
                btnListItemNext.text = getString(R.string.onboarding_title_1)
                sendArgument()
            }
        }
        btnListItemJump.setOnClickListener {
            sendArgument()
        }
    }

    override fun onDestroy() {
        introBinding?.recyclerOnBoarding?.adapter = null
        adapter = null
        introBinding = null
        super.onDestroy()
    }

    override fun onDestroyView() {
        introBinding?.recyclerOnBoarding?.adapter = null
        adapter = null
        introBinding = null
        super.onDestroyView()
    }
}


