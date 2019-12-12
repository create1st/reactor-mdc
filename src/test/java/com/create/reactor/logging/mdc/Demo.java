package com.create.reactor.logging.mdc;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;

import java.util.List;
import java.util.stream.Stream;

import static com.create.reactor.logging.mdc.MdcLogger.mdcDebug;
import static com.create.reactor.logging.mdc.MdcLogger.mdcInfo;
import static java.util.stream.IntStream.range;

class Demo {
    private final static Logger LOGGER = LoggerFactory.getLogger(Demo.class);

    @Test
    void testWithoutSubscriberContext() {
        Flux<List<Integer>> flux = getSampleFlux();
        flux.subscribe();
    }

    @Test
    void testWithSubscriberContextFlow() {
        Flux<List<Integer>> flux = getSampleFlux();
        flux.subscriberContext(MdcContext.of(Flow.Numbers))
            .subscribe();
    }

    @Test
    void testWithSubscriberContextFlowAndId() {
        Flux<List<Integer>> flux = getSampleFlux();
        flux.subscriberContext(MdcContext.of(Flow.Numbers, 1, "TEST"))
            .subscribe();
    }

    private Flux<List<Integer>> getSampleFlux() {
        Stream<Integer> stream = range(0, 10)
                .boxed();
        return Flux.fromStream(stream)
                   .doOnEach(mdcDebug(LOGGER))
                   .groupBy(integer -> integer % 2 == 0)
                   .flatMap(GroupedFlux::collectList)
                   .doOnEach(mdcInfo(LOGGER));
    }

    private enum Flow implements MdcContextFlow {
        Numbers;

        @Override
        public String getName() {
            return name();
        }
    }

}
