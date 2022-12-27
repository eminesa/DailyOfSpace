package com.eminesa.dailyofspace.fragment.welcome

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.eminesa.dailyofspace.R
import com.eminesa.dailyofspace.databinding.FragmentWelcomeBinding
import com.huawei.hmf.tasks.Task
import com.huawei.hms.common.ApiException
import com.huawei.hms.support.hwid.HuaweiIdAuthManager
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper
import com.huawei.hms.support.hwid.result.AuthHuaweiId
import com.huawei.hms.support.hwid.service.HuaweiIdAuthService
import com.yerli.sosyal.utils.storage.LocaleStorageManager


class WelcomeFragment : Fragment() {

    private var binding: FragmentWelcomeBinding? = null
    private var authParams: HuaweiIdAuthParams? = null
    private var authService: HuaweiIdAuthService? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        if (binding == null) binding = FragmentWelcomeBinding.inflate(inflater)
        authParams = HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM).setIdToken().createParams()
        authService = HuaweiIdAuthManager.getService(requireContext(), authParams)
        val signOutTask: Task<Void> = authService!!.signOut()

        signOutTask.addOnCompleteListener { //Processing after the sign-out.
            Log.i("TAG", "signOut complete")
        }
        binding?.btnLoginWithHuawei?.setOnClickListener {
            // startActivityForResult(authService?.signInIntent, Const.REQUEST_SIGN_IN_LOGIN_CODE);
            openSomeActivityForResult()
        }

        return binding?.root
    }

    private fun openSomeActivityForResult() {
        val intent = Intent(authService?.signInIntent)
        resultLauncher.launch(intent)
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                val authHuaweiIdTask: Task<AuthHuaweiId> = HuaweiIdAuthManager.parseAuthResultFromIntent(result.data)

                if (authHuaweiIdTask.isSuccessful) {
                    //The sign-in is successful, and the user's HUAWEI ID information and ID token are obtained.
                    val huaweiAccount =  authHuaweiIdTask.result
                    Log.i("Account Kit Id: ", "Account Id:${huaweiAccount.idToken}")
                    sendArgument()
                }else {
                    Toast.makeText(requireContext(), "signIn get code failed: " +(authHuaweiIdTask.exception as ApiException).statusCode, Toast.LENGTH_LONG)
                }
            }
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
                R.id.action_welcomeFragment_to_dailyPhotoFragment,
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

    override fun onDestroyView() {
        binding = null
        authParams = null
        authService = null
        super.onDestroyView()
    }

}