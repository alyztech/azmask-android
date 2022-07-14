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

/** Describes one char mask */
data class Mask(

    /** Type of the mask chars, [MaskType.FIXED] or [MaskType.REGEX] */
    val maskType: MaskType,
    /** If the [maskType] is [MaskType.FIXED] it returns the value it self else if is [MaskType.REGEX] returns a regex validation for the input char */
    val value: String,
    /** The index position of the validation */
    val index: Int

)
