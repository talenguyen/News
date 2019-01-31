package com.besimplify.news.mics;

/**
 * Not a real crash reporting library!
 */
final class FakeCrashLibrary {
    private FakeCrashLibrary() {
        throw new AssertionError("No instances.");
    }

    static void log(int priority, String tag, String message) {
        // TODO add log entry to circular buffer.
    }

    static void logWarning(Throwable t) {
        // TODO report non-fatal warning.
    }

    static void logError(Throwable t) {
        // TODO report non-fatal error.
    }
}