package com.travel.feature.attraction;

import com.travel.feature.attraction.api.AttractionData;
import com.travel.feature.attraction.api.AttractionProvider;
import com.travel.feature.attraction.contract.AttractionContractMapper;
import com.travel.feature.attraction.di.AttractionComponent;
import com.travel.feature.attraction.domain.model.Attraction;

/**
 * @author byron (byron[dot]zhanglei[at]gmail[dot]com)
 */
public class AttractionProviderImpl implements AttractionProvider {

    private final AttractionComponent component = AttractionComponent.getInstance();
    private final AttractionContractMapper contractMapper = new AttractionContractMapper();

    @Override
    public AttractionData fetchAttractionData() {
        Attraction attraction = component.getFeaturedAttractionUseCase().execute();
        return contractMapper.toApiData(attraction);
    }
}
