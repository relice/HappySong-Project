package com.happysong.app.utils;

import android.text.TextUtils;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * @Package: com.example.relicemxd.happysong.Utils
 * @Author: Relice
 * @Des: 预先处理Utils:
 * 1.{@link Object}的non-null判断 ,见{@link #checkNotNull(Object)}
 * 2.{@link List,Map,String,Object[]} non-null 或 no-empty 判断 ,见{@link #isNullOrEmpty(String)}}
 * 3.校验调用我们的方法的方法传参是否正确 ,见{@link #checkArgument(boolean, String, Object...)}
 *
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

    /**
     * 检查value是否为null，该方法直接返回value，因此可以内嵌使用checkNotNull。
     * 检查失败时抛出的异常 NullPointerException
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    /**
     * 1.检查value是否为null，该方法直接返回value，因此可以内嵌使用checkNotNull.
     *
     * @param reference 需要检查的value参数
     * @param errorMessage 异常信息,如果参数为null,将会抛出
     * string using {@link String#valueOf(Object)}
     * @return 返回非空 value
     * @throws NullPointerException 检查失败时抛出的异常
     */
    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

    /**
     * 返回给定的null string 转 空字符串。
     *
     * @param string 需要检测的 string
     * @return {@code string} 非空返回本身,反之.
     * @des 1.这些方法主要用来与混淆null/空的API进行交互.
     * 2.好的做法是积极地把null和空区分开，以表示不同的含义，
     * 在代码中把null和空同等对待是一种令人不安的坏味道。
     */
    public static String nullToEmpty(@Nullable String string) {
        return (string == null) ? "" : string;
    }

    /**
     * @see {@link #nullToEmpty(String)}
     */
    @Nullable
    public static String emptyToNull(@Nullable String string) {
        return isNullOrEmpty(string) ? null : string;
    }

    /**
     * 如果string为空或长度为0 返回 {@code true}
     * <p>
     * 在不需要检查非空情况下,也可以规范性的使用 {@link String#isEmpty()}
     * string 也可 @see {@link TextUtils# isEmpty() }
     *
     * @param string
     * @return {@code true}
     */
    public static boolean isNullOrEmpty(@Nullable String string) {
        return string == null || string.length() == 0; // string.isEmpty() in Java 6
    }

    public static boolean isNullOrEmpty(@Nullable List list) {
        return list == null || list.size() == 0;
    }

    public static boolean isNullOrEmpty(@Nullable Map map) {
        return map == null || map.size() == 0;
    }

    public static boolean isNullOrEmpty(@Nullable Object[] objs) {
        return objs == null || objs.length == 0;
    }


    /**
     *描述见 {@link #checkArgument(boolean, String, Object...)}
     * @param expression
     */
    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * 描述见 {@link #checkArgument(boolean, String, Object...)}
     * @param expression
     * @param errorMessage
     */
    public static void checkArgument(boolean expression, @Nullable Object errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    /**
     * 如
     * if (count <= 0) { throw new IllegalArgumentException("must be positive: " + count); }
     * 使用preconditions 后:
     * checkArgument(count > 0, "must be positive: %s", count);
     * 校验调用我们的方法的方法传参是否正确，如果错了，应该发出一个“他出错了”的警告信息。
     *
     * @param expression a boolean expression
     * @param errorMessageTemplate a template for the exception message should the check fail. The
     *     message is formed by replacing each {@code %s} placeholder in the template with an
     *     argument. These are matched by position - the first {@code %s} gets {@code
     *     errorMessageArgs[0]}, etc.  Unmatched arguments will be appended to the formatted message
     *     in square braces. Unmatched placeholders will be left as-is.
     * @param errorMessageArgs the arguments to be substituted into the message template. Arguments
     *     are converted to strings using {@link String#valueOf(Object)}.
     * @throws IllegalArgumentException if {@code expression} is false
     * @throws NullPointerException if the check fails and either {@code errorMessageTemplate} or
     *     {@code errorMessageArgs} is null (don't let this happen)
     */
    public static void checkArgument(boolean expression,
                                     @Nullable String errorMessageTemplate,
                                     @Nullable Object... errorMessageArgs) {
        if (!expression) {
            throw new IllegalArgumentException(format(errorMessageTemplate, errorMessageArgs));
        }
    }

    static String format(String template, @Nullable Object... args) {
        template = String.valueOf(template); // null -> "null"

        // start substituting the arguments into the '%s' placeholders
        StringBuilder builder = new StringBuilder(template.length() + 16 * args.length);
        int templateStart = 0;
        int i = 0;
        while (i < args.length) {
            int placeholderStart = template.indexOf("%s", templateStart);
            if (placeholderStart == -1) {
                break;
            }
            builder.append(template.substring(templateStart, placeholderStart));
            builder.append(args[i++]);
            templateStart = placeholderStart + 2;
        }
        builder.append(template.substring(templateStart));

        // if we run out of placeholders, append the extra args in square braces
        if (i < args.length) {
            builder.append(" [");
            builder.append(args[i++]);
            while (i < args.length) {
                builder.append(", ");
                builder.append(args[i++]);
            }
            builder.append(']');
        }

        return builder.toString();
    }
}
