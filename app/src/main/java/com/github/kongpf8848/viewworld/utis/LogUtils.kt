package com.github.kongpf8848.viewworld.utis

import android.text.TextUtils
import android.util.Log

object LogUtils {

    var TAG_DEFAULT = "VIEWWORLD"
    private const val MAX_LENGTH=4000

    private var isDebug = false

    fun init(debug: Boolean) {
        isDebug = debug
    }

    fun v(msg: String?) {
        v(TAG_DEFAULT, msg)
    }

    fun v(tag: String, msg: String?) {
        log(Log.VERBOSE, tag, msg)
    }

    fun d(msg: String?) {
        d(TAG_DEFAULT, msg)
    }

    fun d(tag: String, msg: String?) {
        log(Log.DEBUG, tag, msg)
    }

    fun i(msg: String?) {
        i(TAG_DEFAULT, msg)
    }

    fun i(tag: String, msg: String?) {
        log(Log.INFO, tag, msg)
    }

    fun w(msg: String?) {
        w(TAG_DEFAULT, msg)
    }

    fun w(tag: String, msg: String?) {
        log(Log.WARN, tag, msg)
    }

    fun e(msg: String?) {
        e(TAG_DEFAULT, msg)
    }

    fun e(tag: String, msg: String?) {
        log(Log.ERROR, tag, msg)
    }

    private fun log(tag:String,msg:String){
        val totalLength=msg.length
        var i=0
        while (i<totalLength){
            if(i+ MAX_LENGTH<totalLength){
                Log.d(tag,msg.substring(i, i+MAX_LENGTH))
                i+= MAX_LENGTH
            }
            else{
                Log.d(tag,msg.substring(i))
                break
            }
        }
    }

    private fun log(level: Int, tag: String, content: String?) {
        if (!isDebug) {
            return
        }
        if (TextUtils.isEmpty(content)) {
            return
        }
        val msg=content!!

        /**
         * 分段输出Log日志
         */
        val totalLength=msg.length
        var i=0
        while (i<totalLength){
            if(i+ MAX_LENGTH<totalLength){
                val subMsg=msg.substring(i, i+MAX_LENGTH)
                print(level, tag, subMsg)
                i+= MAX_LENGTH
            }
            else{
                print(level,tag,msg.substring(i))
                break
            }
        }

    }

    /**
     *Android原生Log方法打印日志
     */
    private fun print(level: Int, tag: String, msg: String){
        if (level == Log.VERBOSE) {
            Log.v(tag, msg)
        } else if (level == Log.DEBUG) {
            Log.d(tag, msg)
        } else if (level == Log.INFO) {
            Log.i(tag, msg)
        } else if (level == Log.WARN) {
            Log.w(tag, msg)
        } else if (level == Log.ERROR) {
            Log.e(tag, msg)
        }
    }
}