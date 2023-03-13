package com.example.hurynovich.template_filler.converter;

import com.example.hurynovich.template_filler.dto.PlaceholderKeyDto;
import com.example.hurynovich.template_filler.response.PlaceholderKeyResponse;

import java.util.List;

public interface PlaceholderKeyResponseConverter {

    PlaceholderKeyResponse convert(List<PlaceholderKeyDto> placeholderKeyDtoList);
}
