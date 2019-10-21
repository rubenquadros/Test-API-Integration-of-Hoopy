package com.rubenquadros.apitest.tests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.rubenquadros.apitest.base.BaseTest
import com.rubenquadros.apitest.utils.MockUtils
import com.rubenquadros.apitest.viewmodel.EditViewModel
import com.rubenquadros.apitest.viewmodel.FetchViewModel
import com.rubenquadros.apitest.viewmodel.InsertViewModel
import junit.framework.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.net.HttpURLConnection

@Suppress("DEPRECATION")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class ApiTests: BaseTest() {

    private lateinit var activity: FragmentActivity
    private lateinit var insertViewModel: InsertViewModel
    private lateinit var fetchViewModel: FetchViewModel
    private lateinit var editViewModel: EditViewModel

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    override fun setup() {
        super.setup()
        this.activity = Robolectric.setupActivity(FragmentActivity::class.java)
        this.insertViewModel = ViewModelProviders.of(this.activity, viewModelFactory)[InsertViewModel::class.java]
        this.fetchViewModel = ViewModelProviders.of(this.activity, viewModelFactory)[FetchViewModel::class.java]
        this.editViewModel = ViewModelProviders.of(this.activity, viewModelFactory)[EditViewModel::class.java]
    }

    @Test
    fun insertDataSuccess() {
        this.mockResponse("InsertSuccess.json", HttpURLConnection.HTTP_OK)
        assertEquals("Response should be null because stream is not started yet",null, this.insertViewModel.getInsertResponse().value)
        this.insertViewModel.insertRequest = MockUtils.getInsertRequest()
        this.insertViewModel.insertData()
        assertEquals("Response must be fetched", MockUtils.getInsertResponse().metadata?.responseCode, this.insertViewModel.getInsertResponse().value?.metadata?.responseCode)
        assertEquals("Should be reset to 'false' because stream ended",false, this.insertViewModel.isLoading.value)
        assertEquals("No error must be founded",null, this.insertViewModel.getInsertError().value)
    }

    @Test
    fun insertDataFail() {
        this.mockResponse("InsertSuccess.json", HttpURLConnection.HTTP_BAD_GATEWAY)
        assertEquals("Response should be null because stream is not started yet",null, this.insertViewModel.getInsertResponse().value)
        this.insertViewModel.insertRequest = MockUtils.getInsertRequest()
        this.insertViewModel.insertData()
        assertEquals("Response must be null because of HTTP error",null, this.insertViewModel.getInsertResponse().value)
        assertEquals("Should be reset to 'false' because stream ended",false, this.insertViewModel.isLoading.value)
        assertNotEquals("Error value must not be empty", null, this.insertViewModel.getInsertError().value)
    }

    @Test
    fun fetchDataSuccess() {
        this.mockResponse("FetchSuccess.json", HttpURLConnection.HTTP_OK)
        assertEquals("Response should be null because stream is not started yet",null, this.fetchViewModel.getFetchResponse().value)
        this.fetchViewModel.fetchRequest = MockUtils.getFetchRequest()
        this.fetchViewModel.fetchData()
        assertEquals("Response must be fetched", MockUtils.getFetchDataResponse().metadata?.responseCode, this.fetchViewModel.getFetchResponse().value?.metadata?.responseCode)
        assertEquals("Should be reset to 'false' because stream ended",false, this.fetchViewModel.isLoading.value)
        assertEquals("No error must be founded",null, this.fetchViewModel.getFetchError().value)
    }

    @Test
    fun fetchDataFail() {
        this.mockResponse("FetchSuccess.json", HttpURLConnection.HTTP_BAD_GATEWAY)
        assertEquals("Response should be null because stream is not started yet",null, this.fetchViewModel.getFetchResponse().value)
        this.fetchViewModel.fetchRequest = MockUtils.getFetchRequest()
        this.fetchViewModel.fetchData()
        assertEquals("Response must be null because of HTTP error",null, this.fetchViewModel.getFetchResponse().value)
        assertEquals("Should be reset to 'false' because stream ended",false, this.fetchViewModel.isLoading.value)
        assertNotEquals("Error value must not be empty", null, this.fetchViewModel.getFetchError().value)
    }

    @Test
    fun updateDataSuccess()  {
        this.mockResponse("UpdateSuccess.json", HttpURLConnection.HTTP_OK)
        assertEquals("Response should be null because stream is not started yet",null, this.editViewModel.getUpdateResponse().value)
        this.editViewModel.updateData(MockUtils.getUpdateRequest())
        assertEquals("Response must be fetched", MockUtils.getUpdateResponse().metadata?.responseCode, this.editViewModel.getUpdateResponse().value?.metadata?.responseCode)
        assertEquals("Should be reset to 'false' because stream ended",false, this.editViewModel.isLoading.value)
        assertEquals("No error must be founded",null, this.editViewModel.getUpdateError().value)
    }

    @Test
    fun updateDataFail() {
        this.mockResponse("FetchSuccess.json", HttpURLConnection.HTTP_BAD_GATEWAY)
        assertEquals("Response should be null because stream is not started yet",null, this.fetchViewModel.getFetchResponse().value)
        this.fetchViewModel.fetchRequest = MockUtils.getFetchRequest()
        this.fetchViewModel.fetchData()
        assertEquals("Response must be null because of HTTP error",null, this.fetchViewModel.getFetchResponse().value)
        assertEquals("Should be reset to 'false' because stream ended",false, this.fetchViewModel.isLoading.value)
        assertNotEquals("Error value must not be empty", null, this.fetchViewModel.getFetchError().value)
    }

    @Test
    fun uploadImageSuccess() {

    }

    @Test
    fun uploadImageFail() {

    }

    override fun isMockServerEnabled(): Boolean = true

}