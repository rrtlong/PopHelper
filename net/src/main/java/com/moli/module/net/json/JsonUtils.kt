package com.moli.module.net.json

import com.google.gson.GsonBuilder
import io.victoralbertos.jolyglot.GsonSpeaker
import io.victoralbertos.jolyglot.JolyglotGenerics

/**
 * 项目名称：Platformer
 * 类描述：
 * 创建人：liujun
 * 创建时间：2017/12/11 15:33
 * 修改人：liujun
 * 修改时间：2017/12/11 15:33
 * 修改备注：
 * @version
 */
val jolyglot: JolyglotGenerics by lazy { GsonSpeaker(GsonBuilder()
        .setLenient().create()) }
