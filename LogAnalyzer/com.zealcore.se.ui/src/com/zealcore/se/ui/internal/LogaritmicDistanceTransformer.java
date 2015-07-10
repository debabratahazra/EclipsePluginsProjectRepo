package com.zealcore.se.ui.internal;

public class LogaritmicDistanceTransformer implements IDistanceTransformer {

    private final double multiplier;

    public LogaritmicDistanceTransformer(final double log, final double k) {
        if (log <= 1) {
            throw new IllegalArgumentException(
                    "Logarithm must be greater than 1, but was " + log);
        }

        if (k <= 0) {
            throw new IllegalArgumentException(
                    "Constant must be greater than 0, but was " + k);
        }
        this.multiplier = k / Math.log(log);
    }

    public double transform(final double delta) {
        /*
         * 
         * log_k(x) Log_b(x) = ---------- log_k(b)
         * 
         * 
         * x = delta
         */
        if (delta == 0) {
            return 0;
        }
        return this.multiplier * Math.log(delta + 1);
    }
}
