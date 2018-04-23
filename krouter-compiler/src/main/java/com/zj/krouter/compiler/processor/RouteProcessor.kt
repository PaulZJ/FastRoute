package com.zj.krouter.compiler.processor

import com.squareup.kotlinpoet.FileSpec
import com.zj.krouter.compiler.Logger
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

/**
 * Created by zhangjun on 2018/4/23.
 */

abstract class BaseProcessor: AbstractProcessor() {

    protected lateinit var mElements: Elements
    protected lateinit var mTypes: Types
    protected lateinit var mLogger: Logger
    protected lateinit var mFormatModuleName: String
    protected lateinit var mOriginalModuleName: String

    override fun init(p0: ProcessingEnvironment?) {
        super.init(p0)
        mElements = p0!!.elementUtils
        mTypes = p0!!.typeUtils
        mLogger = Logger(p0!!.messager)

        val options = p0.options
        if (options.isNotEmpty()) {
            mOriginalModuleName = options["moduleName"] ?: ""
            mFormatModuleName = mOriginalModuleName.replace("[^0-9a-zA-Z]+".toRegex(), "")
        }
        mLogger.info("[$mOriginalModuleName] ${this::class.java.simpleName} init")
    }

    override fun process(p0: MutableSet<out TypeElement>?, p1: RoundEnvironment?): Boolean {
        if (p0!!.isEmpty()) {
            return false
        }

        if (mOriginalModuleName.isBlank()) {
            mLogger.warning("this module name is null!!! skip this module")
            return false
        }

        try {
            mLogger.info("[$mOriginalModuleName] ${this::class.java.simpleName} process!!!")
            collectInfo(p1!!)
        }catch (e: Exception) {
            mLogger.error(e)
        }

        return true
    }

    fun FileSpec.writeFile() {
        val kaptKotlinGenerateDir = processingEnv.options["kapt.kotlin.generated"]
        val outputFile = File(kaptKotlinGenerateDir).apply {
            mkdirs()
        }
        writeTo(outputFile.toPath())
    }

    abstract fun collectInfo(roundEnv: RoundEnvironment)
}