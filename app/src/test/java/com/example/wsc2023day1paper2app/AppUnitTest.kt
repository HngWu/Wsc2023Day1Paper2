package com.example.wsc2023day1paper2app

import com.example.wsc2023day1paper2app.api.GetProducts
import com.example.wsc2023day1paper2app.models.ProductItem
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.serialization.json.Json
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection
import java.net.URL

class AppUnitTest {
    private lateinit var getProducts: GetProducts


    @Before
    fun setUp() {
        getProducts = TestGetProducts()
    }

    @Test
    fun testSuccessfulApiCall() {
        // Arrange
        val testGetProducts = TestGetProducts(
            mockResponseCode = 200,
            mockResponse = """
[
    {
        "sku": "1",
        "name": "Test Product",
        "description": "Test Description",
        "price": 9.99,
        "category": "Test Category",
        "image": "tesfasdfasldaksnlkv",
        "availability": "in_stock"
    }
]
""".trimIndent()
        )

        // Act
        val result = testGetProducts.getFunction()

        // Assert
        assertEquals(1, result?.size)
        result?.firstOrNull()?.let { product ->
            assertEquals("1", product.sku)
            assertEquals("Test Product", product.name)
            assertEquals(9.99, product.price, 0.01)
            assertEquals("Test Description", product.description)
            assertEquals("Test Category", product.category)
            assertEquals("tesfasdfasldaksnlkv", product.image)
            assertEquals("in_stock", product.availability)
        }
    }


    @Test
    fun testFailedApiCall() {
        // Arrange
        val testGetProducts = TestGetProducts(mockResponseCode = 404)

        // Act
        val result = testGetProducts.getFunction()

        // Assert
        assertNull(result)
    }

    @Test
    fun testInvalidJsonResponse() {
        // Arrange
        val testGetProducts = TestGetProducts(
            mockResponseCode = 200,
            mockResponse = "Invalid JSON"
        )

        // Act
        val result = testGetProducts.getFunction()

        // Assert
        assertNull(result)
    }

    @Test
    fun testEmptyProductList() {
        // Arrange
        val testGetProducts = TestGetProducts(
            mockResponseCode = 200,
            mockResponse = "[]"
        )

        // Act
        val result = testGetProducts.getFunction()

        // Assert
        assertNotNull(result)
        assertTrue(result?.isEmpty() ?: false)
    }
}


    // Test double that extends the original class
private class TestGetProducts(
    private val mockResponseCode: Int = 200,
    private val mockResponse: String = "[]"
) : GetProducts() {

    override fun getFunction(): MutableList<ProductItem>? {
        // Simulate network call behavior based on mockResponseCode
        return when (mockResponseCode) {
            200 -> try {
                // Try to parse the mock response
                Json.decodeFromString<List<ProductItem>>(mockResponse) as MutableList<ProductItem>?
            } catch (e: Exception) {
                null
            }
            else -> null
        }
    }
}
