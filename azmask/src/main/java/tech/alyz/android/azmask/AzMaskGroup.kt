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

class AzMaskGroup(private var azMasks: List<AzMask>): AzMaskFormatter {

    var textCache = ""

    init {
        this.azMasks = azMasks.sortedBy { it.masks.size }
    }

    override fun formatValue(text: String): String {
        return if(text.isEmpty() || textCache == text) return text else {
            var cleanText = ""
            azMasks.forEach {
                val formatResult = it.formatValue(text)
                if(cleanText.length < it.cleanTextCache.length){
                    cleanText = it.cleanTextCache
                    textCache = formatResult
                }
            }
            textCache
        }
    }
}