package com.create.reactor.logging.mdc;

import com.google.common.io.Closer;
import org.slf4j.Logger;
import org.slf4j.MDC;
import reactor.core.publisher.Signal;
import reactor.util.context.Context;

import java.util.Optional;
import java.util.function.Consumer;

import static java.lang.String.valueOf;
import static java.util.Arrays.stream;

public class MdcLogger {
    private static final String FORMAT = "{}";

    private MdcLogger() {
    }

    public static <T> Consumer<? super Signal<T>> mdcError(Logger logger) {
        return logWithMdc(value -> logger.error(FORMAT, value));
    }

    public static <T> Consumer<? super Signal<T>> mdcWarn(Logger logger) {
        return logWithMdc(value -> logger.warn(FORMAT, value));
    }

    public static <T> Consumer<? super Signal<T>> mdcInfo(Logger logger) {
        return logWithMdc(value -> logger.info(FORMAT, value));
    }

    public static <T> Consumer<? super Signal<T>> mdcDebug(Logger logger) {
        return logWithMdc(value -> logger.debug(FORMAT, value));
    }

    public static <T> Consumer<? super Signal<T>> mdcTrace(Logger logger) {
        return logWithMdc(value -> logger.trace(FORMAT, value));
    }

    private static <T> Consumer<? super Signal<T>> logWithMdc(Consumer<T> logger) {
        return signal -> {
            if (signal.isOnNext()) {
                try (Closer closer = createCloser(signal.getContext())) {
                    logger.accept(signal.get());
                } catch (Exception e) {
                    logger.accept(signal.get());
                }
            }
        };
    }

    private static Closer createCloser(Context context) {
        return stream(MdcLogProperty.values())
              .map(property -> getMdcClosable(context, property))
              .filter(Optional::isPresent)
              .map(Optional::get)
              .collect(Closer::create, Closer::register, MdcLogger::doNothingCombiner);
    }

    private static Optional<MDC.MDCCloseable> getMdcClosable(Context context,
                                                             MdcLogProperty property) {
        String name = property.name();
        return context.getOrEmpty(name)
                      .map(value -> MDC.putCloseable(name, valueOf(value)));
    }

    private static <T> void doNothingCombiner(T t1,
                                              T t2) {
    }
}
