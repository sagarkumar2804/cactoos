/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cactoos.text;

import java.io.IOException;
import java.util.Calendar;
import java.util.IllegalFormatConversionException;
import java.util.Locale;
import java.util.UnknownFormatConversionException;
import org.cactoos.TextHasString;
import org.cactoos.list.IterableAsList;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Test case for {@link FormattedText}.
 *
 * @author Andriy Kryvtsun (kontiky@gmail.com)
 * @version $Id$
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class FormattedTextTest {

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void formatsText() {
        MatcherAssert.assertThat(
            "Can't format a text",
            new FormattedText(
                "%d. Formatted %s", 1, "text"
            ),
            new TextHasString("1. Formatted text")
        );
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void formatsTextWithObjects() {
        MatcherAssert.assertThat(
            "Can't format a text",
            new FormattedText(
                new StringAsText("%d. Formatted %s"),
                new Integer(1),
                new String("text")
            ),
            new TextHasString("1. Formatted text")
        );
    }

    @Test(expected = UnknownFormatConversionException.class)
    @SuppressWarnings("PMD.EmptyCatchBlock")
    public void failsForInvalidPattern() {
        try {
            new FormattedText(
                new StringAsText("%%. Formatted %$"),
                new IterableAsList<>(1, "text")
            ).asString();
        } catch (final IOException ex) {
        }
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void formatsTextWithCollection() {
        MatcherAssert.assertThat(
            "Can't format a text with a collection",
            new FormattedText(
                new StringAsText("%d. Formatted %s"),
                new IterableAsList<>(1, "text")
            ),
            new TextHasString("1. Formatted text")
        );
    }

    @Test(expected = IllegalFormatConversionException.class)
    @SuppressWarnings("PMD.EmptyCatchBlock")
    public void ensuresThatFormatterFails() {
        try {
            new FormattedText(
                new StringAsText("Local time: %d"),
                Locale.ROOT,
                Calendar.getInstance()
            ).asString();
        } catch (final IOException ex) {
        }
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void formatsWithLocale() {
        MatcherAssert.assertThat(
            "Can't format a text with Locale",
            new FormattedText(
                // @checkstyle MagicNumber (1 line)
                "%,d", Locale.GERMAN, 1234567890
            ),
            new TextHasString("1.234.567.890")
        );
    }
}
