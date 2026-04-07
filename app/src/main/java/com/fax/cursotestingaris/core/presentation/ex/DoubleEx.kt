package com.fax.cursotestingaris.core.presentation.ex

import kotlin.math.roundToInt

fun Double.roundTo2Decimals(): Double{
    return (this * 100).roundToInt() / 100.0
}