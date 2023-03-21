package com.hurynovich.template_filler.converter.impl;

import com.hurynovich.template_filler.converter.PlaceholderKeyApiConverter;
import com.hurynovich.template_filler.dto.PlaceholderKeyDto;
import com.hurynovich.template_filler.response.PlaceholderKeyResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasePlaceholderKeyApiConverter implements PlaceholderKeyApiConverter {

    @Override
    public PlaceholderKeyResponse convert(final List<PlaceholderKeyDto> placeholderKeyDtoList) {
        return new PlaceholderKeyResponse(placeholderKeyDtoList
                .stream()
                .map(PlaceholderKeyDto::placeholderKey)
                .toList());
    }
}
