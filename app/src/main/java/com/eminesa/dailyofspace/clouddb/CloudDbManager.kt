package com.eminesa.dailyofspace.clouddb

import android.util.Log
import com.huawei.agconnect.cloud.database.AGConnectCloudDB
import com.huawei.agconnect.cloud.database.CloudDBZone
import com.huawei.agconnect.cloud.database.CloudDBZoneConfig
import com.huawei.agconnect.cloud.database.CloudDBZoneQuery
import com.huawei.agconnect.cloud.database.exceptions.AGConnectCloudDBException
import java.util.ArrayList
import javax.inject.Inject

class CloudDBManager @Inject constructor(
    private val cloudDB: AGConnectCloudDB
) {

    private var mCloudDBZone: CloudDBZone? = null

    init {
        initialize()
    }

    private fun initialize() {
        if (mCloudDBZone == null) {

            cloudDB.createObjectType(ObjectTypeInfoHelper.getObjectTypeInfo())

            val mConfig = CloudDBZoneConfig(
                "DailySpaceZone",
                CloudDBZoneConfig.CloudDBZoneSyncProperty.CLOUDDBZONE_CLOUD_CACHE,
                CloudDBZoneConfig.CloudDBZoneAccessProperty.CLOUDDBZONE_PUBLIC
            )

            mConfig.persistenceEnabled = true
            val task = cloudDB.openCloudDBZone2(mConfig, true)

            task.addOnSuccessListener {
                Log.i("CloudDB", "Open cloudDBZone success")
                mCloudDBZone = it

            }.addOnFailureListener {
                Log.w("CloudDB", "Open cloudDBZone failed for " + it.message)
            }
        } else {
            Log.e("Cloud DB", "error")
        }
    }

    fun saveUser(spot: ObjPhoto, callback: (isSuccessful: Boolean) -> Unit) {
        if (mCloudDBZone == null) {
            Log.w("TAG", "CloudDBZone is null, try re-open it")
            return
        }
        val upsertTask = mCloudDBZone!!.executeUpsert(spot)
        upsertTask.addOnSuccessListener { cloudDBZoneResult ->
            Log.i("TAG", "Upsert $cloudDBZoneResult records")
            callback(true)
        }.addOnFailureListener {
            Log.e("TAG", "Fail processUpsertResult: " + it.message)
            callback(false)
        }
    }

    fun getSpots(spotList: (ArrayList<ObjPhoto>) -> Unit) {
        val queryUsers = CloudDBZoneQuery.where(ObjPhoto::class.java)

        val queryTask = mCloudDBZone?.executeQuery(
            queryUsers,
            CloudDBZoneQuery.CloudDBZoneQueryPolicy.POLICY_QUERY_FROM_CLOUD_ONLY
        )

        queryTask?.addOnSuccessListener { snapshot ->
            val spotsList = arrayListOf<ObjPhoto>()
            try {
                while (snapshot.snapshotObjects.hasNext()) {
                    val user = snapshot.snapshotObjects.next()
                    spotsList.add(user)
                }
            } catch (e: AGConnectCloudDBException) {
                Log.e("TAG", "processQueryResultExc: " + e.message)
            } finally {
                spotList(spotsList)
                snapshot.release()
            }
        }?.addOnFailureListener {
            Log.e("TAG", "Fail processQueryResult: " + it.message)
        }
    }
}