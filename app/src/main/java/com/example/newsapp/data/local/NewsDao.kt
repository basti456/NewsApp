package com.example.newsapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow


@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article)

    @Delete
    suspend fun delete(article: Article)

    @Query("SELECT * FROM Article")
    fun getArticle(): Flow<List<Article>>

    @Query("SELECT * FROM Article where url=:url")
    suspend fun getArticle(url: String): Article?

    @Query("SELECT COUNT(*) from Article where url=:url")
    suspend fun getIsBookmarked(url: String):Int
}