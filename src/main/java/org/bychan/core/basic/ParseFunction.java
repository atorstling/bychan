package org.bychan.core.basic;

/**
 * Created by alext on 2016-12-06.
 */
public interface ParseFunction<N> {
    N parse(PrattParser<N> p);
}
