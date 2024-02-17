package com.harshul.shoesapp.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.harshul.shoesapp.data.db.ShoeDao
import com.harshul.shoesapp.data.models.Shoe
import kotlinx.coroutines.delay
import javax.inject.Inject

class ShoesPagingSource @Inject constructor(
    private val shoesDAO: ShoeDao
) : PagingSource<Int, Shoe>() {

    override fun getRefreshKey(state: PagingState<Int, Shoe>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Shoe> {
        val page = params.key ?: 0

        return try {
            val entities = shoesDAO.getPagedList(params.loadSize, page * params.loadSize)

            // simulate page loading
            if (page != 0) delay(1000)

            LoadResult.Page(
                data = entities,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (entities.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}