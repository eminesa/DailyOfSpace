package com.eminesa.dailyofspace.presenters.dailyPhoto

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.eminesa.dailyofspace.BuildConfig
import com.eminesa.dailyofspace.R
import com.eminesa.dailyofspace.adapter.PhotoAdapter
import com.eminesa.dailyofspace.databinding.FragmentDailyPhotoBinding
import com.eminesa.dailyofspace.model.DailyImage
import com.eminesa.dailyofspace.util.UiText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DailyPhotoFragment : Fragment() {

    private var downloadId = 0L

    private var binding: FragmentDailyPhotoBinding? = null
    private var photoAdapter: PhotoAdapter? = null
    private var imageUrl: String? = null
    private val viewModel: DailyImageFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null)
            binding = FragmentDailyPhotoBinding.inflate(inflater)

        setupObservers()
        viewModel.getDailyImage(BuildConfig.API_KEY)

        requireActivity().registerReceiver(
            onDownloadComplete,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )

        initialAdapter()

        binding?.initRecyclerView()
        binding?.setOnClickListener()

        return binding?.root
    }

    private fun initialAdapter() {
        if (photoAdapter == null)
            photoAdapter = PhotoAdapter()
    }

    private fun setupObservers() {
        viewModel.getViewState().flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }.launchIn(lifecycleScope)
    }

    private fun handleStateChange(state: DailyImageViewState) {
        when (state) {
            is DailyImageViewState.Init -> Unit
            is DailyImageViewState.Loading -> handleLoading(state.isLoading)
            is DailyImageViewState.Success -> handleSuccess(state.data)
            is DailyImageViewState.SuccessWithEmptyData -> Unit
            is DailyImageViewState.Error -> handleError(state.error)
        }
    }

    private fun handleLoading(loading: Boolean) {
        binding?.apply {
            progressBar.isVisible = loading
            imgDownload.isVisible = !loading
        }
    }

    private fun handleSuccess(dailyImage: DailyImage) {
        imageUrl = dailyImage.url
        val list = mutableListOf(
            DailyImage(
                date = dailyImage.date,
                explanation = dailyImage.explanation,
                title = dailyImage.title,
                mediaType = dailyImage.mediaType,
                url = dailyImage.url
            )
        )
        photoAdapter?.submitList(list)

    }

    private fun handleError(error: UiText) =
        Toast.makeText(requireContext(), error.asString(requireContext()), Toast.LENGTH_SHORT)
            .show()

    private fun FragmentDailyPhotoBinding.setOnClickListener() {

        imgDownload.setOnClickListener {
            imgDownload.isEnabled = false
            binding?.progressBar?.visibility = View.VISIBLE
            downloadFile(imageUrl)
        }
    }

    private fun downloadFile(url: String?) {

        val uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
             .setTitle("Space Of Daily")
            .setDescription("Your space image is loading...")
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

    private fun FragmentDailyPhotoBinding.initRecyclerView() {
        recyclerViewPhoto.apply {

            setHasFixedSize(false)
            adapter = photoAdapter

            // stay on more visible item
            val snapHelper: SnapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(this)
        }
    }

    override fun onDestroy() {
        binding = null
        photoAdapter = null
        imageUrl = null
        findNavController().currentBackStackEntry?.viewModelStore?.clear()
        requireActivity().unregisterReceiver(onDownloadComplete)
        super.onDestroy()
    }

    override fun onDestroyView() {
        binding = null
        imageUrl = null
        findNavController().currentBackStackEntry?.viewModelStore?.clear()
        photoAdapter = null
        super.onDestroyView()
    }
}