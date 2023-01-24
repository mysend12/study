package io.my.r2dbcentity.content.mapstruct;

import io.my.r2dbcentity.content.entity.Content;
import io.my.r2dbcentity.content.payload.ContentPayload;
import io.my.r2dbcentity.convert.impl.ConvertWithMapstruct;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class ContentMapstructTest {

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(ContentMapstructTest.class.getSimpleName())
                .warmupTime(TimeValue.NONE)
                .measurementTime(TimeValue.NONE)
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.MILLISECONDS)
                .threads(1)
                .build();
        new Runner(options).run();
    }

    @Test
    @Benchmark
    public void payloadToEntityTest() {
        ContentPayload contentPayload = new ContentPayload(1L, "title");
        Content content = ConvertWithMapstruct.INSTANCE.contentPayloadToContent(contentPayload);
        assertInstanceOf(Content.class, content);
    }

    @Test
    @Benchmark
    public void entityToPayloadTest() {
        Content content = new Content(1L, "title", 1L);
        ContentPayload contentPayload = ConvertWithMapstruct.INSTANCE.contentToContentPayload(content);
        assertInstanceOf(ContentPayload.class, contentPayload);
    }

}