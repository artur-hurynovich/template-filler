package com.example.hurynovich.template_filler.converter;

import com.example.hurynovich.template_filler.dto.PlaceholderKeyDto;
import com.example.hurynovich.template_filler.response.PlaceholderKeyResponse;

import java.util.List;

public interface PlaceholderKeyApiConverter {

    PlaceholderKeyResponse convert(List<PlaceholderKeyDto> placeholderKeyDtoList);
}
