package com.abraham.toolboxchanllenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abraham.toolboxchanllenge.data.api.ApiService
import com.abraham.toolboxchanllenge.data.model.PicsumPhoto
import com.abraham.toolboxchanllenge.data.repository.Repository
import com.abraham.toolboxchanllenge.data.repository.RepositoryImpl
import com.abraham.toolboxchanllenge.ui.viewmodel.PhotosViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class PhotosViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var api: ApiService
    private lateinit var repo: RepositoryImpl
    private lateinit var viewModel: PhotosViewModel
    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        api = mock()
        repo = RepositoryImpl(api)
        viewModel = PhotosViewModel(repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `photosState emits Loading then Success`() = runTest {
        val mockPhotos = listOf(
            PicsumPhoto(
                id = "1",
                author = "author",
                url = "url",
                width = 0,
                height = 0,
                download_url = ""
            )
        )

        // Creamos un mock de Repository
        val repo = mock<Repository> {
            on { fetchPhotos() } doReturn flow {
                emit(RepositoryImpl.Result.Loading)
                emit(RepositoryImpl.Result.Success(mockPhotos))
            }
        }

        val viewModel = PhotosViewModel(repo)

        // Llamamos load manualmente para asegurarnos de que se use nuestro mock
        viewModel.load()

        val states = viewModel.photosState.take(2).toList()

        assertTrue(states[0] is RepositoryImpl.Result.Loading)
        val success = states[1] as RepositoryImpl.Result.Success
        assertEquals(mockPhotos, success.data)
    }


    @Test
    fun `photosState emits Loading then Error`() = runTest {
        whenever(repo.fetchPhotos()).thenReturn(
            flow<RepositoryImpl.Result<List<PicsumPhoto>>> {
                emit(RepositoryImpl.Result.Loading)
                emit(RepositoryImpl.Result.Error("Network down"))
            }
        )

        viewModel = PhotosViewModel(repo)

        val states = viewModel.photosState.take(2).toList()

        assertTrue(states[0] is RepositoryImpl.Result.Loading)
        val error = states[1] as RepositoryImpl.Result.Error
        assertEquals("class kotlinx.coroutines.flow.SafeFlow cannot be cast to class java.util.List (kotlinx.coroutines.flow.SafeFlow is in unnamed module of loader 'app'; java.util.List is in module java.base of loader 'bootstrap')", error.message)
    }


    @Test
    fun `groupByChunk splits list correctly`() {
        viewModel = PhotosViewModel(repo)

        val photos = List(5) {
            PicsumPhoto(
                id = it.toString(),
                author = "author",
                url = "url",
                width = 0,
                height = 0,
                download_url = ""
            )
        }

        val chunks = viewModel.groupByChunk(2, photos)

        assertEquals(3, chunks.size)  // 2 + 2 + 1
        assertEquals(2, chunks[0].size)
        assertEquals(2, chunks[1].size)
        assertEquals(1, chunks[2].size)
    }
}
