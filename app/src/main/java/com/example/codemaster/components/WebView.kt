package com.example.codemaster.components

import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebViewPager(
    url : String
){
    var backEnabled by remember { mutableStateOf(false) }
    var webView: WebView? = null
    AndroidView(factory = {
        WebView(it).apply {
            settings.javaScriptEnabled = true
            settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
            settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            settings.domStorageEnabled = true
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            settings.useWideViewPort = true
            settings.supportZoom()
            settings.displayZoomControls = false
            settings.loadWithOverviewMode = true
            setInitialScale(1)
            scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            settings.userAgentString = "Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3"
            webViewClient =object :  WebViewClient(){
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    if (view != null) {
                        backEnabled = view.canGoBack()
                    }
                }
            }
            loadUrl(url)
            webView = this
        }
    },
        update = {
            webView = it
//            it.loadUrl(url)
        }
    )
    BackHandler(enabled = backEnabled){
        webView?.goBack()
    }
}