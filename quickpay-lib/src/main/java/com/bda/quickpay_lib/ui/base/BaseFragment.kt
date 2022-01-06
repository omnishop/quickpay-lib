package com.bda.quickpay_lib.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bda.quickpay_lib.R
import com.bda.quickpay_lib.utils.QuickPayUtils
import com.bda.quickpay_lib.utils.convertToBaseException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseFragment : Fragment() {

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        applyBackground()
//        return super.onCreateView(inflater, container, savedInstanceState)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyBackground()
    }

    fun handleError(e: Throwable) {
        var errMessage = ""
        when (e) {
            // case no internet connection
            is UnknownHostException -> {
                errMessage = resources.getString(R.string.txt_err_no_internet)
            }
            // case request time out
            is SocketTimeoutException -> {
                errMessage = resources.getString(R.string.txt_err_request_timeout)
            }
            else -> {
                // convert throwable to base exception to get error information
                val baseException = convertToBaseException(e)
                when (baseException.httpCode) {
                    HttpURLConnection.HTTP_UNAUTHORIZED -> {
                        errMessage = resources.getString(R.string.txt_err_unauthorized)
                    }
                    HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                        errMessage = baseException.serverErrorResponse?.message.toString()
                    }
                    else -> {
                        val invalidParam = baseException.serverErrorResponse?.validations
                        if (invalidParam != null) {
                            errMessage = invalidParam.get(0).message.toString()
                        } else {
                            val errorTmpMessage = baseException.serverErrorResponse?.message
                            if (errorTmpMessage.isNullOrEmpty()) {
                                errMessage = resources.getString(R.string.txt_err_unknown)
                            } else {
                                errMessage = errorTmpMessage
                            }
                        }
                    }
                }
            }
        }
        Toast.makeText(requireContext(), errMessage, Toast.LENGTH_SHORT).show()
    }

    private fun applyTheme() {
        val theme = if (QuickPayUtils.isProductEnv()) R.style.AppThemeDark else R.style.AppTheme
        requireActivity().setTheme(theme)
    }

    private fun applyBackground() {
        if (QuickPayUtils.isDarkMode()) enableDarkMode() else disableDarkMode()
    }

    abstract fun enableDarkMode()
    abstract fun disableDarkMode()
}