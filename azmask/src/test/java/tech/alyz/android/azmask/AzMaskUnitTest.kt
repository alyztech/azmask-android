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

import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class AzMaskUnitTest {

    @Test
    fun maskValidation_isCorrect() {
        val numberFactory = MaskFactory.getMaskFactory(MaskFactory.MaskFactoryType.NUMBER)
        val masks = arrayListOf(
            numberFactory.createMask(0),
            numberFactory.createMask(1),
            numberFactory.createMask(2),
            Mask(MaskType.FIXED, ".", 3),
            numberFactory.createMask(4),
            numberFactory.createMask(5),
            numberFactory.createMask(6),
            Mask(MaskType.FIXED, ".", 7),
            numberFactory.createMask(8),
            numberFactory.createMask(9),
            numberFactory.createMask(10),
            Mask(MaskType.FIXED, "-", 11),
            numberFactory.createMask(12),
            numberFactory.createMask(13)
        )

        val azMask = AzMask(masks)
        val formattedValue = azMask.formatValue("a7117a7638011")

        assertEquals("711.776.380-11", formattedValue)

    }

    @Test
    fun groupMaskValidation_isCorrect() {
        val numberFactory = MaskFactory.getMaskFactory(MaskFactory.MaskFactoryType.NUMBER)
        val cpfMasks = arrayListOf(
            numberFactory.createMask(0),
            numberFactory.createMask(1),
            numberFactory.createMask(2),
            Mask(MaskType.FIXED, ".", 3),
            numberFactory.createMask(4),
            numberFactory.createMask(5),
            numberFactory.createMask(6),
            Mask(MaskType.FIXED, ".", 7),
            numberFactory.createMask(8),
            numberFactory.createMask(9),
            numberFactory.createMask(10),
            Mask(MaskType.FIXED, "-", 11),
            numberFactory.createMask(12),
            numberFactory.createMask(13)
        )

        val cnpjMasks = arrayListOf(
            numberFactory.createMask(0),
            numberFactory.createMask(1),
            Mask(MaskType.FIXED, ".", 2),
            numberFactory.createMask(3),
            numberFactory.createMask(4),
            numberFactory.createMask(5),
            Mask(MaskType.FIXED, ".", 6),
            numberFactory.createMask(7),
            numberFactory.createMask(8),
            numberFactory.createMask(9),
            Mask(MaskType.FIXED, "/", 10),
            numberFactory.createMask(11),
            numberFactory.createMask(12),
            numberFactory.createMask(13),
            numberFactory.createMask(14),
            Mask(MaskType.FIXED, "-", 15),
            numberFactory.createMask(16),
            numberFactory.createMask(17),
        )
        val azMaskGroup = AzMaskGroup(arrayListOf(cpfMasks, cnpjMasks))
        var formattedValue = azMaskGroup.formatValue("a711776a38011")

        assertEquals("711.776.380-11", formattedValue)
        formattedValue = azMaskGroup.formatValue("07.44a2.741/0001-71")
        assertEquals("07.442.741/0001-71", formattedValue)
        assertEquals("07442741000171", azMaskGroup.azMasks[1].cleanTextCache)

    }
}