package com.app.ashikcarnottest.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.ashikcarnottest.R
import com.app.ashikcarnottest.apiservice.NetworkStateListener
import com.app.ashikcarnottest.apiservice.RetrofitApiCallBack
import com.app.ashikcarnottest.databinding.ActivityMainBinding
import com.app.ashikcarnottest.utils.AppUtils

class MainActivity : AppCompatActivity(), NetworkStateListener {

    private var marketApiResponse : MarketApiResponse? = null
    private lateinit var activityView : ActivityMainBinding
    private var linearLayoutManager: LinearLayoutManager? = null
    private lateinit var marketItemListAdapter : MarketItemListAdapter
    private var filterFieldName : String = ""
    private lateinit var itemDecoration : DividerItemDecoration
    private var isNetworkAvailable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityView = DataBindingUtil.setContentView(this,R.layout.activity_main)
        AppUtils.isNetworkAvailable(this)
        activityView.rvMarketItemList.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == RecyclerView.SCROLL_STATE_IDLE && linearLayoutManager!!.itemCount ==
                    linearLayoutManager?.findLastVisibleItemPosition()!!+1){
                    if (activityView.loadMoreProgressbar.visibility!=View.VISIBLE &&
                        linearLayoutManager?.itemCount != marketApiResponse?.total) {
                        if(isNetworkAvailable)
                            HomeInteractor().callMarketMandiListApi(10,
                            linearLayoutManager!!.itemCount,"", homeInteractorApiCallBack)
                        else
                            AppUtils.showToast(this@MainActivity, getString(R.string.check_internet))

                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

            }
        })

        activityView.spinnerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if(!filterFieldName.equals(marketApiResponse!!.field.get(position).name)){
                    filterFieldName = marketApiResponse!!.field.get(position).name
                    if (isNetworkAvailable)
                        HomeInteractor().callMarketMandiListApi(10,0,
                        filterFieldName, filterApiCallBack)
                    else
                        AppUtils.showToast(this@MainActivity, getString(R.string.check_internet))
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

    }

    var homeInteractorApiCallBack  =  object : RetrofitApiCallBack<MarketApiResponse>{
        override fun showProgress(showProgress: Boolean) {
            if (marketApiResponse!=null){
                if (showProgress)
                    activityView.loadMoreProgressbar.visibility = View.VISIBLE
                else
                    activityView.loadMoreProgressbar.visibility = View.GONE
            }
            else{
                if (showProgress)
                    activityView.mainProgressbar.visibility = View.VISIBLE
                else
                    activityView.mainProgressbar.visibility = View.GONE
            }
        }

        override fun onApiSuccess(apiResponse : MarketApiResponse) {
            if (marketApiResponse==null){
                marketApiResponse = apiResponse
                activityView.txtListTitle.text = marketApiResponse?.title
                setMarketItemInAdapter()
                setFilterItem()
            }
            else {
                marketApiResponse!!.records.addAll(apiResponse.records)
                marketItemListAdapter.notifyDataSetChanged()
            }
        }

        override fun onApiError(errorMsg: String, errorCode: Int) {
        }

    }


    var filterApiCallBack  =  object : RetrofitApiCallBack<MarketApiResponse>{
        override fun showProgress(showProgress: Boolean) {
            if (showProgress)
                activityView.mainProgressbar.visibility = View.VISIBLE
            else
                activityView.mainProgressbar.visibility = View.GONE
        }

        override fun onApiSuccess(apiResponse : MarketApiResponse) {
            marketApiResponse!!.records.clear()
            marketApiResponse!!.records.addAll(apiResponse.records)
            marketItemListAdapter.notifyDataSetChanged()
        }

        override fun onApiError(errorMsg: String, errorCode: Int) {

        }

    }

    fun setMarketItemInAdapter() : Unit {
        marketItemListAdapter = MarketItemListAdapter(
            this@MainActivity,
            marketApiResponse!!.records)
        if (::itemDecoration.isInitialized)
            activityView.rvMarketItemList.removeItemDecoration(itemDecoration)
        linearLayoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        itemDecoration = DividerItemDecoration(this@MainActivity,
            linearLayoutManager!!.orientation)

        itemDecoration.setDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.item_divider)!!)
        activityView.rvMarketItemList.addItemDecoration(itemDecoration)
        activityView.rvMarketItemList.layoutManager = linearLayoutManager
        activityView.rvMarketItemList.adapter = marketItemListAdapter
    }

    fun setFilterItem() : Unit {
        marketApiResponse!!.field.add(0,FilterItem("","select","select"))
        var filterAdapter = ArrayAdapter(
            this@MainActivity,
            android.R.layout.simple_spinner_item,marketApiResponse!!.field)

        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        activityView.spinnerFilter.adapter = filterAdapter
    }

    override fun networkState(isAvailable: Boolean) {
        isNetworkAvailable = isAvailable
        if (isAvailable && marketApiResponse==null)
            HomeInteractor().callMarketMandiListApi(10,0,"",
                homeInteractorApiCallBack)
        if (!isNetworkAvailable)
            AppUtils.showToast(this, getString(R.string.check_internet))

    }
}