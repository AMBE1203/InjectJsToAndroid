package com.example.demowebviewandroid

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MovieFragment : Fragment() {

    public var mSwipeRefresh: SwipeRefreshLayout? = null
    private var mWebview: WebView? = null


    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater!!.inflate(R.layout.fragment_movie, container, false)

        mSwipeRefresh = view.findViewById<SwipeRefreshLayout>(R.id.mSwipeRefreshX)
        mWebview = view.findViewById<WebView>(R.id.mWebview)



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
        val xx = ScrollableWebViewJSInterfaceObject()
        xx.setT(object : Test {
            override fun enableSwipe(boolean: Boolean) {
                mSwipeRefresh?.isEnabled = boolean

            }
        })

        mWebview?.webViewClient = object :
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
        }



        mSwipeRefresh?.setOnRefreshListener {
            if (mWebview != null) {
                mWebview?.reload()
            }
        }
        mWebview?.addJavascriptInterface(xx, "scrollableWebViewJSInterface")


        return view


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
        val test = """javascript:(function f() {
        var btns = document.getElementById('next-link');
            btns.setAttribute('onClick', 'scrollableWebViewJSInterface.vaoDay()');
          
        
      })()"""
        view.evaluateJavascript(javascript, null)
    }


    inner class ScrollableWebViewJSInterfaceObject {

        private var t: Test? = null

        fun setT(test: Test) {
            this.t = test
        }

        @JavascriptInterface
        fun vaoDay() {


            t?.enableSwipe(false)
        }


        @JavascriptInterface
        @Suppress("UNUSED")
        fun touchOnScrollableWebElement(
            scrollLeft: Int,
            scrollTop: Int,
            scrollLeftMax: Int,
            scrollTopMax: Int
        ) {


            t?.enableSwipe(false)


        }

        @JavascriptInterface
        @Suppress("UNUSED")
        fun touchOnNotScrollableWebElement() {

            t?.enableSwipe(true)


        }
    }

}