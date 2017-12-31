package org.laxio.piston.protocol.v340.stream.compression;

        /*-
         * #%L
         * Protocol
         * %%
         * Copyright (C) 2017 Laxio
         * %%
         * This file is part of Piston, licensed under the MIT License (MIT).
         *
         * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
         *
         * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
         *
         * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
         * #L%
         */

/**
 * Represents the compression settings for the channel
 */
public class CompressionState {

    private int threshold = -1;

    public CompressionState(int threshold) {
        this.threshold = threshold;
    }

    /**
     * Gets the threshold at which a packet becomes compressed
     *
     * @return The threshold at which a packet becomes compressed
     */
    public int getThreshold() {
        return threshold;
    }

    /**
     * Sets the threshold for compression, negative values disable compression
     *
     * @param threshold The threshold value to set
     */
    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    /**
     * Returns if the channel should be compressed
     *
     * @return True if compression is enabled, false otherwise
     */
    public boolean isEnabled() {
        return this.threshold >= 0;
    }

}
