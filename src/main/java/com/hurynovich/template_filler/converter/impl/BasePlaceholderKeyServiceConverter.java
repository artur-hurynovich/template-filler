package com.hurynovich.template_filler.converter.impl;

import com.hurynovich.template_filler.converter.PlaceholderKeyServiceConverter;
import com.hurynovich.template_filler.dto.PlaceholderKeyDto;
import com.hurynovich.template_filler.entity.PlaceholderKeyEntity;
import org.springframework.stereotype.Service;

@Service
public class BasePlaceholderKeyServiceConverter implements PlaceholderKeyServiceConverter {

    @Override
    public PlaceholderKeyEntity convert(final String placeholderKey) {
        final var placeholderKeyEntity = new PlaceholderKeyEntity();
        placeholderKeyEntity.setPlaceholderKey(placeholderKey);

        return placeholderKeyEntity;
    }

    @Override
    public PlaceholderKeyDto convert(final PlaceholderKeyEntity source) {
        return new PlaceholderKeyDto(source.getId(), source.getPlaceholderKey());
    }
}
