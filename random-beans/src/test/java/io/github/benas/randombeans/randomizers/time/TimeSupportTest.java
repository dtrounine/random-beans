/*
 * The MIT License
 *
 *   Copyright (c) 2016, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */

package io.github.benas.randombeans.randomizers.time;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.beans.TimeBean;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TimeSupportTest {

    private EnhancedRandom enhancedRandom;

    @Before
    public void setUp() {
        enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build();
    }

    @Test
    public void threeTenTypesShouldBePopulated() {
        TimeBean timeBean = enhancedRandom.nextObject(TimeBean.class);

        assertThat(timeBean.getDuration()).isNotNull();
        assertThat(timeBean.getInstant()).isNotNull();
        assertThat(timeBean.getLocalDate()).isNotNull();
        assertThat(timeBean.getLocalTime()).isNotNull();
        assertThat(timeBean.getLocalDateTime()).isNotNull();
        assertThat(timeBean.getMonth()).isNotNull();
        assertThat(timeBean.getMonthDay()).isNotNull();
        assertThat(timeBean.getOffsetDateTime()).isNotNull();
        assertThat(timeBean.getOffsetTime()).isNotNull();
        assertThat(timeBean.getPeriod()).isNotNull();
        assertThat(timeBean.getYearMonth()).isNotNull();
        assertThat(timeBean.getYear()).isNotNull();
        assertThat(timeBean.getZonedDateTime()).isNotNull();
        assertThat(timeBean.getZoneOffset()).isNotNull();
    }
}
