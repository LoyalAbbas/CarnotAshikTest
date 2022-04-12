package com.app.ashikcarnottest.apiservice


interface CommonView {

    /**
     * Show progress when api is being called
     *
     * @param showProgress Flag to show hide progress view
     */
    fun showProgress(showProgress: Boolean)
}