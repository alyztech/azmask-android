/*
 * Copyright 2022 Alyz Tech
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tech.alyz.android.azmask

import tech.alyz.android.azmask.MaskType.*

class AzMask(var masks: List<Mask>): AzMaskFormatter {
    private var textCache = ""
    var cleanTextCache = ""

    init {
        this.masks = masks.sortedBy { it.index }
    }

    private fun updateCache(result: StringBuilder, cleanResult: StringBuilder): String {
        textCache = result.toString()
        cleanTextCache = cleanResult.toString()
        return textCache
    }

    override fun formatValue(text: String): String {
        val result = StringBuilder("")
        val cleanResult = StringBuilder("")
        var index = 0
        var maskIndex = 0

        while (index < text.length && maskIndex < masks.size) {
            val mask = masks[maskIndex]
            if(index < textCache.length && textCache[index] == text[index]){
                result.append(text[index])
                if (mask.maskType == REGEX) {
                    cleanResult.append(text[index])
                }
                index++
            } else {
                if (mask.maskType == REGEX) {
                    validateRegex(text, mask.value.toRegex(), index)?.let {
                        result.append(text[it])
                        cleanResult.append(text[it])
                        index = it + 1
                    } ?: run {
                        return updateCache(result, cleanResult)
                    }
                } else {
                    if (index == text.length - 1 && text[index].toString() == mask.value) {
                        // Remove fixed char
                        result.drop(result.length - 1)
                        return updateCache(result, cleanResult)
                    }
                    result.append(mask.value)
                }
            }
            maskIndex++
        }
        return updateCache(result, cleanResult)
    }

    private fun validateRegex(text: String, pattern: Regex, index: Int): Int? {
        return if (pattern.matches(text[index].toString())) {
            index
        } else {
            if (index < text.length - 1) validateRegex(text, pattern, index + 1) else null
        }
    }
}