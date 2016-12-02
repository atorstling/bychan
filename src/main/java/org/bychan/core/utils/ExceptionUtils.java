package org.bychan.core.utils;

/**
 * Created by alext on 2016-12-02.
 */
public class ExceptionUtils {
    // From https://github.com/fge/throwing-lambdas/issues/9
    public static <T extends Throwable> void sneakyThrow(Throwable t) throws T {
       throw (T) t;
     }
}
