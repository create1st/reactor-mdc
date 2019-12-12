package com.create.reactor.logging.mdc;

import reactor.util.context.Context;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.create.reactor.logging.mdc.MdcLogProperty.ContextFlow;
import static com.create.reactor.logging.mdc.MdcLogProperty.ContextId;

public class MdcContext {
    private static final String DELIMITER = ",";

    public static Context of(MdcContextFlow mdcContextFlow,
                             Object... values) {
        String stringValue = getStringValue(values);
        return Context.of(ContextFlow.name(), mdcContextFlow.getName(), ContextId.name(), stringValue);
    }

    private static String getStringValue(Object[] values) {
        return Arrays.stream(values)
                     .map(Object::toString)
                     .collect(Collectors.joining(DELIMITER));
    }
}
