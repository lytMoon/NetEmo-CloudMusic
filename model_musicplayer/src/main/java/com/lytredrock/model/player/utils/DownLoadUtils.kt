package com.lytredrock.model.player.utils

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.FileOutputStream
import java.net.URL

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/24 20:53
 * version: 1.0
 */
object DownLoadUtils {


    fun downloadMusic(url: String, fileName: String): Observable<Boolean> {
        return Observable.create<Boolean?> { emitter ->
            try {
                val connection = URL(url).openConnection()
                connection.connect()

                val inputStream = connection.getInputStream()
                val outputStream = FileOutputStream(fileName)

                val buffer = ByteArray(1024)
                var bytesRead = 0
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }

                outputStream.close()
                inputStream.close()

                emitter.onNext(true)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    }

}