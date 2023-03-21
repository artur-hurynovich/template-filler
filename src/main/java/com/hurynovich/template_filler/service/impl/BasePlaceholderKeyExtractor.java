package com.hurynovich.template_filler.service.impl;

import com.hurynovich.template_filler.service.PlaceholderKeyExtractor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.Collections.unmodifiableList;
import static java.util.regex.Pattern.compile;

@Service
public class BasePlaceholderKeyExtractor implements PlaceholderKeyExtractor {

    private static final String PLACEHOLDER_REGEX = "\\{\\{(.*?)}}";

    private static final int PLACEHOLDER_PREFIX_LENGTH = 2;
    private static final int PLACEHOLDER_POSTFIX_LENGTH = 2;

    private final Pattern pattern = compile(PLACEHOLDER_REGEX);

    @Override
    public List<String> extract(final String templatePayload) {
        final var placeholderKeys = new ArrayList<String>();
        final var matcher = pattern.matcher(templatePayload);

        while (matcher.find()) {
            final var group = matcher.group();
            placeholderKeys.add(
                    group.substring(PLACEHOLDER_PREFIX_LENGTH, group.length() - PLACEHOLDER_POSTFIX_LENGTH));
        }

        return unmodifiableList(placeholderKeys);
    }
}
