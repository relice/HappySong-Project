package com.example.relicemxd.happysong.utils;

import javax.annotation.Nullable;

/**
 * @Package: com.example.relicemxd.happysong.Utils
 * @Author: Relice
 * @Date: 16/4/22
 * @Des: 预先检测工具
 */
public final class Preconditions {
    private static Preconditions mPdt;

    private Preconditions() {
    }

    public static Preconditions getInstance() {
        if (mPdt == null) {
            mPdt = new Preconditions();
        }
        return mPdt;
    }

    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference an object reference
     * @param errorMessage the exception message to use if the check fails; will be converted to a
     * string using {@link String#valueOf(Object)}
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }
}
