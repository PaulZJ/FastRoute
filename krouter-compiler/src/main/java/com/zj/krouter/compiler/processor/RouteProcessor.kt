package com.zj.krouter.compiler.processor

import com.squareup.kotlinpoet.*
import com.zj.krouter.compiler.Logger
import com.zj.krouter.compiler.PROVIDER_LOADER
import com.zj.krouter.compiler.ROUTE_LOADER
import com.zj.krouter.compiler.WARNINGS
import com.zj.krouter_annotation.*
import com.zj.krouter_annotation.model.RouteMetadata
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

class RouteProcessor : BaseProcessor() {
    private val routeMap = HashMap<String, RouteMetadata>()
    override fun collectInfo(roundEnv: RoundEnvironment) {
        routeMap.clear()
        val elements = roundEnv.getElementsAnnotatedWith(Route::class.java)
        if (elements.isEmpty()) {
            return
        }
        mLogger.info("Found ${elements.size} routes in [$mOriginalModuleName]")

        val tmActivity = mElements.getTypeElement(RouteType.ACTIVITY.className).asType()
        val tmService = mElements.getTypeElement(RouteType.SERVICE.className).asType()
        val tmFragment = mElements.getTypeElement(RouteType.FRAGMENT.className).asType()
        val tmFragmentV4 = mElements.getTypeElement(RouteType.FRAGMENT_V4.className).asType()
        val tmContentProvider = mElements.getTypeElement(RouteType.CONTENT_PROVIDER.className).asType()

        val mapTypeOfRouteLoader = ParameterizedTypeName.get(ClassName("kotlin.conllections", "MutableMap"),
                String::class.asClassName(), RouteMetadata::class.asClassName())

        val routeLoaderFunSpecBuild = FunSpec.builder("loadInto")
                .addParameter("map", mapTypeOfRouteLoader)
                .addModifiers(KModifier.OVERRIDE)

        elements.forEach {
            val routeAnn = it.getAnnotation(Route::class.java)
            val routeType = when {
                mTypes.isSubtype(it.asType(), tmActivity) -> {
                    mLogger.info("Found Activity ${it.asType()}")
                }
                mTypes.isSubtype(it.asType(), tmService) -> {
                    mLogger.info("Found Service ${it.asType()}")
                }
                mTypes.isSubtype(it.asType(), tmFragment) -> {
                    mLogger.info("Found Fragment ${it.asType()}")
                }
                mTypes.isSubtype(it.asType(), tmFragmentV4) -> {
                    mLogger.info("Found Fragment_v4 ${it.asType()}")
                }
                mTypes.isSubtype(it.asType(), tmContentProvider) -> {
                    mLogger.info("Found Content Provider ${it.asType()}")
                }
                else -> {
                    mLogger.info("Unknown route ${it.asType()}")
                }
            }

            if (routeAnn.path.isNotBlank()) {
                if (routeMap.containsKey(routeAnn.path)) {
                    mLogger.warning("The route ${routeMap[routeAnn.path]?.name} already has Path {${routeAnn.path}}," +
                            " so skip route ${it.asType()}")
                    return@forEach
                }
                routeMap[routeAnn.path] = RouteMetadata(name = it.asType().toString())

                routeLoaderFunSpecBuild.addStatement(
                        "map[%S] = %T(%T.%L, %L, %S, %S, %S, %S, %T::class.java)",
                        routeAnn.path,
                        RouteMetadata::class,
                        RouteType::class,
                        routeType,
                        routeAnn.priority,
                        routeAnn.name,
                        routeAnn.path,
                        routeAnn.pathPrefix,
                        routeAnn.pathPattern,
                        it.asType()
                )
            }
        }

        val typeIRouteLoader = TypeSpec.classBuilder("$ROUTE_LOADER_NAME$SEPARATOR$mFormatModuleName")
                .addSuperinterface(ClassName.bestGuess(ROUTE_LOADER))
                .addKdoc(WARNINGS)
                .addFunction(routeLoaderFunSpecBuild.build())
                .build()

        val kotlinFile = FileSpec.builder(PACKAGE, "$ROUTE_LOADER_NAME.$SEPARATOR$mFormatModuleName")
                .addType(typeIRouteLoader)
                .build()

        kotlinFile.writeFile()
    }

}

