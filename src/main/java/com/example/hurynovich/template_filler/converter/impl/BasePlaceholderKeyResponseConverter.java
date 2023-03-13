package com.example.hurynovich.template_filler.converter.impl;

import com.example.hurynovich.template_filler.converter.PlaceholderKeyResponseConverter;
import com.example.hurynovich.template_filler.dto.PlaceholderKeyDto;
import com.example.hurynovich.template_filler.response.PlaceholderKeyResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasePlaceholderKeyResponseConverter implements PlaceholderKeyResponseConverter {

    @Override
    public PlaceholderKeyResponse convert(final List<PlaceholderKeyDto> placeholderKeyDtoList) {
        return new PlaceholderKeyResponse(placeholderKeyDtoList
                .stream()
                .map(PlaceholderKeyDto::placeholderKey)
                .toList());
    }
}
