package com.example.demowebviewandroid

import android.os.Bundle
import android.util.Log
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {

    public var mSwipeRefresh: SwipeRefreshLayout? = null
    private var mWebview: WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mSwipeRefresh = findViewById<SwipeRefreshLayout>(R.id.mSwipeRefreshX)
        mWebview = findViewById<WebView>(R.id.mWebview)

        mWebview?.loadUrl("https://vnext5-test3.care-wing.jp/smart/?mode=my_page&kaigosya_id=6")
        val webSettings = mWebview?.settings
        webSettings?.setSupportMultipleWindows(true)
        webSettings?.loadsImagesAutomatically = true
        webSettings?.builtInZoomControls = true
        webSettings?.setSupportZoom(true)
        webSettings?.loadWithOverviewMode = true
        webSettings?.useWideViewPort = true
        webSettings?.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
        webSettings?.javaScriptEnabled = true
        webSettings?.setSupportMultipleWindows(true)
        val xx = ScrollableWebViewJSInterfaceObject()
        xx.setT(object : Test {
            override fun enableSwipe(boolean: Boolean) {
                mSwipeRefresh?.isEnabled = boolean

                Log.e("AMBE1203", "1234: ${mSwipeRefresh?.isEnabled}")
            }

        })

        mWebview?.addJavascriptInterface(
            xx,
            "scrollableWebViewJSInterface"
        )


        mWebview?.setWebViewClient(object :
            WebViewClient() {


            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                registerWebElementsTouchCallback(view)

                mSwipeRefresh?.let {
                    it.isRefreshing = false
                }
            }

            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
            ) {

            }

            override fun onReceivedError(
                view: WebView, errorCode: Int,
                description: String, failingUrl: String
            ) {

            }
        })



        mSwipeRefresh?.setOnRefreshListener {
            if (mWebview != null) {
                mWebview?.reload()
            }
        }
    }

    private fun registerWebElementsTouchCallback(view: WebView) {

        val javascript = "function onTouchStart(event) { \n" +
                "if (event.targetTouches.length >= 1) {  \n" +
                "var touch = event.targetTouches[0]; \n" +
                "if (touch.target) { \n" +
                "var node = touch.target; \n" +
                "while(node) { \n" +
                "var scrollLeftMax = node.scrollWidth - node.clientWidth; \n" +
                "var scrollTopMax = node.scrollHeight - node.clientHeight; \n" +
                "if (scrollLeftMax > 0 || scrollTopMax > 0) { \n" +
                "window.scrollableWebViewJSInterface.touchOnScrollableWebElement(node.scrollLeft, " +
                "node.scrollTop, scrollLeftMax, scrollTopMax); \n" +
                "break; \n" +
                "} \n" +
                "node = node.parentNode; \n" +
                "} \n" +
                "if (!node) { \n" +
                "window.scrollableWebViewJSInterface.touchOnNotScrollableWebElement(); \n" +
                "} \n" +
                "} \n" +
                "} \n" +
                "} \n" +
                "window.addEventListener('touchstart', onTouchStart, true);\n"
        view.evaluateJavascript(javascript, null)
    }

    inner class ScrollableWebViewJSInterfaceObject {

        private var t: Test? = null

        fun setT(test: Test) {
            this.t = test
        }


        @JavascriptInterface
        @Suppress("UNUSED") // Used by javascript callback
        fun touchOnScrollableWebElement(
            scrollLeft: Int,
            scrollTop: Int,
            scrollLeftMax: Int,
            scrollTopMax: Int
        ) {

            Log.e("AMBE1203", " ($scrollTopMax - $scrollTop)  a")
//
            if (scrollTop == 0) {
                Log.e("AMBE1203", "aaaaa")
                t?.enableSwipe(true)


            } else {
                Log.e("AMBE1203", "cccccc")
                t?.enableSwipe(false)

            }

        }

        @JavascriptInterface
        @Suppress("UNUSED") // Used by javascript callback
        fun touchOnNotScrollableWebElement() {

            Log.e("AMBE1203", "bbbbb")
            t?.enableSwipe(true)


        }
    }
}

interface Test {
    fun enableSwipe(boolean: Boolean)
}

