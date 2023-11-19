package com.srhan.foodapp.di

import android.content.Context
import androidx.room.Room
import com.srhan.foodapp.db.MealsDao
import com.srhan.foodapp.db.MealsDatabase
import com.srhan.foodapp.remote.FoodApiService
import com.srhan.foodapp.repository.GetCategoriesRepo
import com.srhan.foodapp.repository.GetMealByCategoryRepo
import com.srhan.foodapp.repository.GetMealInfoRepo
import com.srhan.foodapp.repository.GetPopularMealRepo
import com.srhan.foodapp.repository.GetRandomMealRepo
import com.srhan.foodapp.repository.MealsLocalRepository
import com.srhan.foodapp.repository.SearchMealRepo
import com.srhan.foodapp.repository.repositoryimp.GetCategoriesRepoImp
import com.srhan.foodapp.repository.repositoryimp.GetMealByCategoryRepoImp
import com.srhan.foodapp.repository.repositoryimp.GetMealInfoRepoImp
import com.srhan.foodapp.repository.repositoryimp.GetPopularMealRepoImp
import com.srhan.foodapp.repository.repositoryimp.GetRandomMealRepoImp
import com.srhan.foodapp.repository.repositoryimp.MealsLocalRepositoryImp
import com.srhan.foodapp.repository.repositoryimp.SearchMealRepoImp
import com.srhan.foodapp.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMealsDatabase(@ApplicationContext context: Context): MealsDatabase {

        return Room.databaseBuilder(
            context,
            MealsDatabase::class.java,
            "meals_db"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideMealsDao(mealsDatabase: MealsDatabase): MealsDao {
        return mealsDatabase.mealsDao()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): FoodApiService {
        return retrofit.create(FoodApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideRandomMealRepo(foodApiService: FoodApiService): GetRandomMealRepo {
        return GetRandomMealRepoImp(foodApiService)
    }

    @Provides
    @Singleton
    fun provideCategoriesRepo(foodApiService: FoodApiService): GetCategoriesRepo {
        return GetCategoriesRepoImp(foodApiService)
    }

    @Provides
    @Singleton
    fun provideMealInfoRepo(foodApiService: FoodApiService): GetMealInfoRepo {
        return GetMealInfoRepoImp(foodApiService)
    }

    @Provides
    @Singleton
    fun providePopularMealRepo(foodApiService: FoodApiService): GetPopularMealRepo {
        return GetPopularMealRepoImp(foodApiService)
    }

    @Provides
    @Singleton
    fun provideMealByCategoryRepo(foodApiService: FoodApiService): GetMealByCategoryRepo {
        return GetMealByCategoryRepoImp(foodApiService)
    }

    @Provides
    @Singleton
    fun provideSearchMealRepo(foodApiService: FoodApiService): SearchMealRepo {
        return SearchMealRepoImp(foodApiService)
    }

    @Provides
    @Singleton
    fun provideMealsLocalRepo(mealsDao: MealsDao): MealsLocalRepository {
        return MealsLocalRepositoryImp(mealsDao)
    }

}