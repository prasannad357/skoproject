package com.prasanna.skoproject.data.remote

import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: UserApi

    @Before
    fun setup(){
        mockWebServer = MockWebServer()
        api = Retrofit
            .Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(UserApi::class.java)
    }

    @Test
    fun testGetUsersEmptyList_expectedTrue() = runTest{
        val mockResponse = MockResponse()
        mockResponse.setBody("{\"page\":3,\"per_page\":6,\"total\":12,\"total_pages\":2,\"data\":[],\"support\":{\"url\":\"https://reqres.in/#support-heading\",\"text\":\"To keep ReqRes free, contributions towards server costs are appreciated!\"}}")
        mockWebServer.enqueue(mockResponse)
        val response = api.getUsers(3).body()
        mockWebServer.takeRequest()
        Assert.assertEquals(true, response?.users?.isEmpty())
    }


    @Test
    fun testGetUsers_expectedSixUsers() = runTest{
        val mockResponse = MockResponse()
        mockResponse.setBody("{\"page\":2,\"per_page\":6,\"total\":12,\"total_pages\":2,\"data\":[{\"id\":7,\"email\":\"michael.lawson@reqres.in\",\"first_name\":\"Michael\",\"last_name\":\"Lawson\",\"avatar\":\"https://reqres.in/img/faces/7-image.jpg\"},{\"id\":8,\"email\":\"lindsay.ferguson@reqres.in\",\"first_name\":\"Lindsay\",\"last_name\":\"Ferguson\",\"avatar\":\"https://reqres.in/img/faces/8-image.jpg\"},{\"id\":9,\"email\":\"tobias.funke@reqres.in\",\"first_name\":\"Tobias\",\"last_name\":\"Funke\",\"avatar\":\"https://reqres.in/img/faces/9-image.jpg\"},{\"id\":10,\"email\":\"byron.fields@reqres.in\",\"first_name\":\"Byron\",\"last_name\":\"Fields\",\"avatar\":\"https://reqres.in/img/faces/10-image.jpg\"},{\"id\":11,\"email\":\"george.edwards@reqres.in\",\"first_name\":\"George\",\"last_name\":\"Edwards\",\"avatar\":\"https://reqres.in/img/faces/11-image.jpg\"},{\"id\":12,\"email\":\"rachel.howell@reqres.in\",\"first_name\":\"Rachel\",\"last_name\":\"Howell\",\"avatar\":\"https://reqres.in/img/faces/12-image.jpg\"}],\"support\":{\"url\":\"https://reqres.in/#support-heading\",\"text\":\"To keep ReqRes free, contributions towards server costs are appreciated!\"}}")
        mockWebServer.enqueue(mockResponse)

        val response = api.getUsers(2).body()
        mockWebServer.takeRequest()
        Assert.assertEquals(6, response?.users?.size)
    }

    @Test
    fun testGetUsersCodeFourZeroFour_expectedNotFound() = runTest{
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(404)
        mockResponse.setBody("Not Found")
        mockWebServer.enqueue(mockResponse)

        val response = api.getUsers(2)
        mockWebServer.takeRequest()
        Assert.assertEquals(404, response.code())
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }
}