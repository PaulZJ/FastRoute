package com.zj.api.core

import android.os.Bundle
import com.zj.api.interfaces.IInjector
import com.zj.annotation.getInjectorClass

/**
 * Created by zhangjun on 2018/4/29.
 *
 * find Injector of Route Params for the Route Instance</br>
 * using Reflection for class name by specific Name Pattern
 */
internal fun internalInject(instance: Any, bundle: Bundle?) {
    val injector = Class.forName(getInjectorClass(instance)).newInstance() as IInjector
    injector.inject(instance, bundle)
}