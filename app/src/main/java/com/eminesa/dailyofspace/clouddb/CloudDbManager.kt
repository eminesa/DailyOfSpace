package com.eminesa.dailyofspace.clouddb

import android.util.Log
import com.huawei.agconnect.cloud.database.AGConnectCloudDB
import com.huawei.agconnect.cloud.database.CloudDBZone
import com.huawei.agconnect.cloud.database.CloudDBZoneConfig
import com.huawei.agconnect.cloud.database.CloudDBZoneQuery
import com.huawei.agconnect.cloud.database.exceptions.AGConnectCloudDBException
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
                "SpaceOfDaily",
                CloudDBZoneConfig.CloudDBZoneSyncProperty.CLOUDDBZONE_CLOUD_CACHE,
                CloudDBZoneConfig.CloudDBZoneAccessProperty.CLOUDDBZONE_PUBLIC
            )

            mConfig.persistenceEnabled = true
            val task = cloudDB.openCloudDBZone2(mConfig, true)

            task.addOnSuccessListener {
                Log.i("CloudDB", "Open cloudDBZone success")
                mCloudDBZone = it
                //cloudDB.closeCloudDBZone(mCloudDBZone)
            }.addOnFailureListener {
                Log.w("CloudDB", "Open cloudDBZone failed for " + it.message)
            }
        } else {
            Log.e("Cloud DB", "error")
        }
    }

    fun saveUser(user: ObjPhoto, loginListener: LoginListener) {
        if (mCloudDBZone == null) {
            Log.d("TAG", "Cloud DB Zone is null, try re-open it")
            return
        }

        mCloudDBZone!!.runTransaction {
            return@runTransaction try {
                val query = CloudDBZoneQuery.where(ObjPhoto::class.java).orderByDesc("userId")
                    .limit(1)
                val result = it.executeQuery(query)
                val newID: String = if (result.size > 0)
                    result[0].userId
                else
                    ""
                user.userId = newID
                it.executeUpsert(mutableListOf(user))
                loginListener.onSuccess(user)
                true
            } catch (e: AGConnectCloudDBException) {
                Log.w("TAG", "Chat Entry upsert is failed ${e.message}")
                loginListener.onFailure(e)
                false
            }
        }
    }
}