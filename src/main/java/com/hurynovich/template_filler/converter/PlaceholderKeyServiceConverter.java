package com.hurynovich.template_filler.converter;

import com.hurynovich.template_filler.dto.PlaceholderKeyDto;
import com.hurynovich.template_filler.entity.PlaceholderKeyEntity;

public interface PlaceholderKeyServiceConverter {

    PlaceholderKeyEntity convert(String placeholderKey);

    PlaceholderKeyDto convert(PlaceholderKeyEntity source);
}
