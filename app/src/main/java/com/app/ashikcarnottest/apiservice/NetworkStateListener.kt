package com.app.ashikcarnottest.apiservice

interface NetworkStateListener {

    /**
     * Indicate network availability
     *  @param isAvailable true if network available, false if not.
     */
    fun networkState(isAvailable: Boolean)
}