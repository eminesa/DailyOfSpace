package com.eminesa.dailyofspace.util

import androidx.lifecycle.MutableLiveData

class Const{

    companion object {
        var youtubeApiKey: String = "AIzaSyDuEL7hFHfLtAzd8_2M9drmsMLkRHrIBA4"
        var nasaKey: String = "da2QThPK0PiunpjTKv6NUE67Od0L8E78ntl3GuOR"

        var isRepostedPostDeleted = MutableLiveData<Boolean>()

    }

}