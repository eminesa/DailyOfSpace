package com.eminesa.dailyofspace.clouddb

import android.content.Context
import android.util.Log
import com.huawei.agconnect.AGCRoutePolicy
import com.huawei.agconnect.AGConnectInstance
import com.huawei.agconnect.AGConnectOptionsBuilder
import com.huawei.agconnect.auth.AGConnectAuth
import com.huawei.agconnect.cloud.database.AGConnectCloudDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CloudDBModule {

    @Singleton
    @Provides
    fun createCloudDBConnection(
        @ApplicationContext context: Context,
    ): AGConnectCloudDB {
        AGConnectCloudDB.initialize(context)

        val agcConnectOptions =
            AGConnectOptionsBuilder().setRoutePolicy(AGCRoutePolicy.GERMANY).build(context)
        val instance = AGConnectInstance.buildInstance(agcConnectOptions)

        AGConnectAuth.getInstance(instance).signInAnonymously().addOnSuccessListener {
            Log.d("Tag", "Sign in successful")
        }.addOnFailureListener {
            Log.d("Tag", it.message.toString())
        }

        return AGConnectCloudDB.getInstance(instance, AGConnectAuth.getInstance(instance))
    }

    @Singleton
    @Provides
    fun provideCloudDBManager(@ApplicationContext context: Context): CloudDBManager {
        return CloudDBManager(createCloudDBConnection(context))
    }

}