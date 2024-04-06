package org.jenkinsci.plugins.ghprb;

/**
 * A representation of text that can check its container thread for interruptions.
 * This allows a break within tight charAt(..) calling loops, which can otherwise become infinite in a corrupt find.
 */
final class ThreadString implements CharSequence {
    private final CharSequence delegate;

    ThreadString(final CharSequence input) {
        delegate = input;
    }

    @Override
    public char charAt(final int index) {
        if (Thread.currentThread().isInterrupted()) {
            throw new RuntimeException(new InterruptedException());
        }
        return delegate.charAt(index);
    }

    @Override
    public int length() {
        return delegate.length();
    }

    @Override
    public CharSequence subSequence(final int start, final int end) {
        if (Thread.currentThread().isInterrupted()) {
            throw new RuntimeException(new InterruptedException());
        }
        return new ThreadString(delegate.subSequence(start, end));
    }

    @Override
    public String toString() {
        return delegate.toString();
    }
}
