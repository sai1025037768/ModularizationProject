package com.travel.feature.transportation;

import com.travel.feature.transportation.api.TransportationData;
import com.travel.feature.transportation.api.TransportationProvider;
import com.travel.feature.transportation.contract.TransportationContractMapper;
import com.travel.feature.transportation.di.TransportationComponent;
import com.travel.feature.transportation.domain.model.Transportation;

/**
 * @author byron (byron[dot]zhanglei[at]gmail[dot]com)
 */
public class TransportationProviderImpl implements TransportationProvider {

    private final TransportationComponent component = TransportationComponent.getInstance();
    private final TransportationContractMapper contractMapper = new TransportationContractMapper();

    @Override
    public TransportationData fetchTransportationData() {
        Transportation transportation = component.getFeaturedTransportationUseCase().execute();
        return contractMapper.toApiData(transportation);
    }
}
