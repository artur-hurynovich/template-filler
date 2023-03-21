package com.hurynovich.template_filler.service;

import java.util.List;

public interface PlaceholderKeyExtractor {

    List<String> extract(String templatePayload);
}
