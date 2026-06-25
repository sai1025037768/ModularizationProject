package com.travel.feature.transportation.api;

/**
 * @author byron (byron[dot]zhanglei[at]gmail[dot]com)
 */
public interface TransportationProvider {

    TransportationData fetchTransportationData();

    default Object createBlankFragment(String message) {
        throw new UnsupportedOperationException("Transportation UI factory is not implemented.");
    }
}
