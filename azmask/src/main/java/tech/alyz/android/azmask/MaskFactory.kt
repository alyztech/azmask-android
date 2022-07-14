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

/**
 * Abstract class that creates [Mask] instances by [MaskFactoryType].
 * @author Caio Mansho
 */
abstract class MaskFactory {

    /** Describes a MaskFactory type. */
    enum class MaskFactoryType { LETTER, NUMBER, ALPHANUMERIC }

    /**
     * Abstracts the creation of a [Mask].
     * @param index position of the string.
     * @return [Mask] created.
     */
    abstract fun createMask(index: Int): Mask

    companion object {
        /**
         * Get [MaskFactory]  implementation by [MaskFactoryType].
         *
         * @param maskFactoryType with the type of the [Mask] to be created.
         * @return [MaskFactory] implementation of the [Mask] creation.
         */
        fun getMaskFactory(maskFactoryType: MaskFactoryType): MaskFactory {
            return when (maskFactoryType) {
                MaskFactoryType.LETTER -> LetterMaskFactory()
                MaskFactoryType.NUMBER -> NumberMaskFactory()
                MaskFactoryType.ALPHANUMERIC -> AlphanumericMaskFactory()
            }
        }
    }

    /**
     * Inner implementation class that creates char only mask mask.
     */
    class LetterMaskFactory : MaskFactory() {
        override fun createMask(index: Int): Mask = Mask(MaskType.REGEX, "^[a-zA-Z]+$", index)

    }

    /**
     * Inner implementation class that creates numbers only mask.
     */
    class NumberMaskFactory : MaskFactory() {
        override fun createMask(index: Int): Mask = Mask(MaskType.REGEX, "^[0-9]*$", index)

    }

    /**
     * Inner implementation class that creates alphanumeric mask.
     */
    class AlphanumericMaskFactory : MaskFactory() {
        override fun createMask(index: Int): Mask = Mask(MaskType.REGEX, "^[a-zA-Z0-9]*$", index)

    }

}

