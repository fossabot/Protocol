package org.laxio.piston.protocol.v001.stream.compression;

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
     * @return The threshold at which a packet becomes compressed
     */
    public int getThreshold() {
        return threshold;
    }

    /**
     * Sets the threshold for compression, negative values disable compression
     * @param threshold The threshold value to set
     */
    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    /**
     * Returns if the channel should be compressed
     * @return True if compression is enabled, false otherwise
     */
    public boolean isEnabled() {
        return this.threshold >= 0;
    }

}
