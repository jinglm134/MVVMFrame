package com.project.mvvmframe.entity

/**
 * Response返回数据,主要用于外层数据,code,message的统一处理
 * @CreateDate 2020/5/21 11:52
 * @Author jaylm
 */
data class BaseBean<T>(val error_code: Int, val reason: String, val result: T) : IBaseBean