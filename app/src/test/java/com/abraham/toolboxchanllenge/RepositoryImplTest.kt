package com.abraham.toolboxchanllenge
import com.abraham.toolboxchanllenge.data.api.ApiService
import com.abraham.toolboxchanllenge.data.model.PicsumPhoto
import com.abraham.toolboxchanllenge.data.repository.RepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryImplTest {

    private lateinit var api: ApiService
    private lateinit var repository: RepositoryImpl

    @Before
    fun setup() {
        api = mock()
        repository = RepositoryImpl(api)
    }

    @Test
    fun `fetchPhotos emits Loading then Success`() = runTest {
        val mockPhotos = listOf(PicsumPhoto(
            id="1",
            author = "author",
            url = "url",
            width = 0,
            height = 0,
            download_url = ""))
        whenever(api.getPhotos()).thenReturn(mockPhotos)

        val results = repository.fetchPhotos().toList()

        // El primer resultado debe ser Loading
        assertTrue(results[0] is RepositoryImpl.Result.Loading)

        // El segundo resultado debe ser Success con URL modificada
        val success = results[1] as RepositoryImpl.Result.Success
        assertEquals("https://picsum.photos/id/1/600/400", success.data[0].download_url)
    }

    @Test
    fun `fetchPhotos emits Error when api throws RuntimeException`() = runTest {
        whenever(api.getPhotos()).thenThrow(RuntimeException("Network down"))

        val results = repository.fetchPhotos().toList()

        assertTrue(results[0] is RepositoryImpl.Result.Loading)

        val error = results[1] as RepositoryImpl.Result.Error
        assertEquals("Network down", error.message)
    }

}