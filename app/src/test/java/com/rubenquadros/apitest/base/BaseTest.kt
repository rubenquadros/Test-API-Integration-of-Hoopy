package com.rubenquadros.apitest.base

import com.rubenquadros.apitest.di.DaggerTestAppComponent
import com.rubenquadros.apitest.di.TestApiModule
import com.rubenquadros.apitest.di.TestAppComponent
import com.rubenquadros.apitest.di.TestRxJavaModule
import com.rubenquadros.apitest.factory.ViewModelFactory
import com.rubenquadros.apitest.utils.ApplicationConstants
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import java.io.File
import javax.inject.Inject

abstract class BaseTest {
    private lateinit var mockServer: MockWebServer
    private lateinit var testAppComponent: TestAppComponent

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Before
    open fun setup() {
        this.configureMockServer()
        this.configureDi()
    }

    @After
    fun tearDown() {
        this.stopMockServer()
    }

    abstract fun isMockServerEnabled(): Boolean

    open fun configureMockServer() {
        if(isMockServerEnabled()) {
            mockServer = MockWebServer()
            mockServer.start()
        }
    }

    open fun mockResponse(fileName: String, responseCode: Int) = mockServer.enqueue(
        MockResponse()
            .setResponseCode(responseCode)
            .setBody(getJson(fileName)))

    private fun getJson(path: String): String {
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path)
        return String(file.readBytes())
    }

    open fun configureDi() {
        this.testAppComponent = DaggerTestAppComponent.builder()
            .testApiModule(TestApiModule(if (isMockServerEnabled()) mockServer.url("/").toString() else ApplicationConstants.BASE_URL))
            .testRxJavaModule(TestRxJavaModule())
            .build()
        this.testAppComponent.inject(this)
    }

    open fun stopMockServer() {
        if(isMockServerEnabled()) {
            mockServer.shutdown()
        }
    }
}