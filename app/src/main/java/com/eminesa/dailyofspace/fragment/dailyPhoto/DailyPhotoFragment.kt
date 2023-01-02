package com.eminesa.dailyofspace.fragment.dailyPhoto

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.eminesa.dailyofspace.R
import com.eminesa.dailyofspace.adapter.PhotoAdapter
import com.eminesa.dailyofspace.clouddb.ObjPhoto
import com.eminesa.dailyofspace.databinding.FragmentDailyPhotoBinding
import com.huawei.agconnect.auth.AGConnectAuth
import com.huawei.hms.ads.AdListener
import com.huawei.hms.ads.AdParam
import com.huawei.hms.ads.BannerAdSize
import com.huawei.hms.ads.InterstitialAd
import com.huawei.hms.ads.banner.BannerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DailyPhotoFragment : Fragment() {

    private var downloadId = 0L

    private var binding: FragmentDailyPhotoBinding? = null
    private var photoAdaper: PhotoAdapter? = null
    private var imageUrl: String? = null
    private val viewModel: DailyPhotoFragmentViewModel by viewModels()
    private val user = ObjPhoto()
    private var interstitialAd: InterstitialAd? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null)
            binding = FragmentDailyPhotoBinding.inflate(inflater)

        viewModel.getSpots()
        observeLiveData()

        //banner ads
        val bannerView = BannerView(requireContext())
        bannerView.adId = "testw6vs28auh3"
        bannerView.bannerAdSize = BannerAdSize.BANNER_SIZE_360_57
        val adParam = AdParam.Builder().build()
        bannerView.loadAd(adParam)

        binding?.hwBannerView?.addView(bannerView)

        requireActivity().registerReceiver(
            onDownloadComplete,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )

        if (photoAdaper == null)
            photoAdaper = PhotoAdapter(onShowMoreClickListener = { txtDescription, photoInfo ->

                if (txtDescription.maxLines < 2) {
                    txtDescription.ellipsize = null
                    txtDescription.maxLines = Integer.MAX_VALUE
                } else {
                    txtDescription.ellipsize = TextUtils.TruncateAt.END
                    txtDescription.maxLines = 1
                }
            }, itemClickListener = { view, item ->
                //imageUrl = item.photoUrl
            })

        if (arguments != null) {

            val date = arguments?.getString("date")
            val explanation = arguments?.getString("explanation")
            val title = arguments?.getString("title")
            val mediaType = arguments?.getString("media_type")
            val url = arguments?.getString("url")

            user.userId = url
            user.userName = "Emine"
            user.photoAddDate = date
            user.photoTitle = title
            user.photoDesc =
                if (explanation!!.length > 200) explanation.take(199) else explanation //for cloud db string limitation
            user.urlType = mediaType
            user.photoUrl = url

            binding?.setOnClickListener(user)

        }
        binding?.recyclerViewPhoto?.apply {
            setHasFixedSize(false)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = photoAdaper
        }

        val user = AGConnectAuth.getInstance().currentUser
        val snapHelper: SnapHelper = LinearSnapHelper() // stay on one item
        snapHelper.attachToRecyclerView(binding?.recyclerViewPhoto)

        return binding?.root
    }

    private fun observeLiveData() {
        viewModel.getSpotListLiveData().observe(requireActivity()) {
            it.add(0, user)
            photoAdaper?.submitList(it)
        }
    }

    private fun FragmentDailyPhotoBinding.setOnClickListener(user: ObjPhoto) {
        imgSave.setOnClickListener {
            loadInterstitialAd()
        }

        imgDownload.setOnClickListener {
            imgDownload.isEnabled = false
            binding?.progressBar?.visibility = View.VISIBLE
            downloadFile(imageUrl)
        }
    }

    private fun loadInterstitialAd() {
        interstitialAd = InterstitialAd(requireContext())
        interstitialAd?.adId = "testb4znbuh3n2"
        interstitialAd?.adListener = adListener
        val adParam = AdParam.Builder().build()
        interstitialAd?.loadAd(adParam)
    }

    private val adListener: AdListener = object : AdListener() {
        override fun onAdLoaded() {
            super.onAdLoaded()
            Log.d("TAG_ADS", "onAdLoaded")
            showInterstitial() // Display an interstitial ad.
        }

        override fun onAdFailed(errorCode: Int) {
            Toast.makeText(
                requireContext(),
                "Ad load failed with error code: $errorCode",
                Toast.LENGTH_SHORT
            ).show()
            Log.d("TAG_ADS", "Ad load failed with error code: $errorCode")
        }

        override fun onAdClosed() {
            super.onAdClosed()
            viewModel.saveUser(user, requireContext())
            Log.d("TAG_ADS", "onAdClosed")
        }

        override fun onAdClicked() {
            Log.d("TAG_ADS", "onAdClicked")
            super.onAdClicked()
        }

        override fun onAdOpened() {
            Log.d("TAG_ADS", "onAdOpened")
            super.onAdOpened()
        }
    }

    private fun showInterstitial() { // Display an interstitial ad.
        if (interstitialAd?.isLoaded == true) {
            interstitialAd?.show()
        } else {
            Toast.makeText(requireContext(), "Ad did not load", Toast.LENGTH_SHORT).show()
        }
    }

    private fun downloadFile(url: String?) {

        val uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            //.setTitle("SpaceDaily")
            .setDescription("Your space image is downloading...")
            .setAllowedOverMetered(true)

        val manager =
            requireContext().getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as DownloadManager

        downloadId = manager.enqueue(request)
    }

    //now checking if download complete
    private val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (downloadId == id) {

                binding?.imgDownload?.setImageResource(R.drawable.ic_success)
                binding?.progressBar?.visibility = View.GONE

                Toast.makeText(
                    requireContext(),
                    getString(R.string.daily_photo_downloaded),
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }

    override fun onDestroy() {
        binding = null
        photoAdaper = null
        imageUrl = null
        interstitialAd = null
        requireActivity().unregisterReceiver(onDownloadComplete)
        super.onDestroy()
    }

    override fun onDestroyView() {
        binding = null
        imageUrl = null
        photoAdaper = null
        interstitialAd = null
        super.onDestroyView()
    }
}