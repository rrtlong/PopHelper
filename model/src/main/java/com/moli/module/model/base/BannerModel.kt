package com.moli.module.model.base

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/9/21 下午5:44
 * 修改人：yuliyan
 * 修改时间：2018/9/21 下午5:44
 * 修改备注：
 * @version
 */
data class BannerModel(
    @JvmField
    val imgUrl: String? = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2908440987,1551648475&fm=27&gp=0.jpg",     //图片地址
    @JvmField
    val downloadUrl: String? = null,  //下载地址
    @JvmField
    val contentUrl: String? = null,  //内容地址
    @JvmField
    val bannerType: Int = 0  //类型，为0返回下载地址

)
