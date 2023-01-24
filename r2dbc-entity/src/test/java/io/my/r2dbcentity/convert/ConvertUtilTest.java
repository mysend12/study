package io.my.r2dbcentity.convert;

import io.my.r2dbcentity.convert.impl.ConvertWithBoilerplate;
import io.my.r2dbcentity.convert.impl.ConvertWithMapstruct;
import io.my.r2dbcentity.convert.impl.ConvertWithObjectMapper;
import io.my.r2dbcentity.user.entity.User;
import io.my.r2dbcentity.user.payload.UserPayload;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class ConvertUtilTest {

    @State(Scope.Benchmark)
    public static class ConvertUtilBenchmarkState {
        public Map<Class<? extends ConvertUtil>, ConvertUtil> convertUtilMap;
        public ConvertUtilBenchmarkState() {
            convertUtilMap = new HashMap<>();
            convertUtilMap.put(
                    ConvertWithMapstruct.class, Mappers.getMapper(ConvertWithMapstruct.class)
            );
            convertUtilMap.put(
                    ConvertWithObjectMapper.class, new ConvertWithObjectMapper()
            );
            convertUtilMap.put(
                    ConvertWithBoilerplate.class, new ConvertWithBoilerplate()
            );
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(ConvertUtilTest.class.getSimpleName())
                .warmupTime(TimeValue.seconds(1))
                .measurementTime(TimeValue.seconds(1))
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.NANOSECONDS)
                .threads(10)
                .build();
        new Runner(options).run();
    }

    @Test
    void userToUserPayloadTest() {
        Map<Class<? extends ConvertUtil>, ConvertUtil> convertUtilMap = new HashMap<>();
        convertUtilMap.put(
                ConvertWithMapstruct.class, Mappers.getMapper(ConvertWithMapstruct.class)
        );
        convertUtilMap.put(
                ConvertWithObjectMapper.class, new ConvertWithObjectMapper()
        );
        convertUtilMap.put(
                ConvertWithBoilerplate.class, new ConvertWithBoilerplate()
        );

        assertEquals(3, convertUtilMap.size());
        convertUtilMap.values().forEach(this::userToUserPayloadTest);
    }

    @Benchmark
    public void userToUserPayloadWithMapstruct(ConvertUtilBenchmarkState state) {
        Map<Class<? extends ConvertUtil>, ConvertUtil> convertUtilMap = state.convertUtilMap;
        ConvertUtil convertUtil = convertUtilMap.get(ConvertWithMapstruct.class);
        this.userToUserPayloadTest(convertUtil);
    }

    @Benchmark
    public void userToUserPayloadWithObjectMapper(ConvertUtilBenchmarkState state) {
        Map<Class<? extends ConvertUtil>, ConvertUtil> convertUtilMap = state.convertUtilMap;
        ConvertUtil convertUtil = convertUtilMap.get(ConvertWithObjectMapper.class);
        this.userToUserPayloadTest(convertUtil);
    }

    @Benchmark
    public void userToUserPayloadWithBoilerplate(ConvertUtilBenchmarkState state) {
        Map<Class<? extends ConvertUtil>, ConvertUtil> convertUtilMap = state.convertUtilMap;
        ConvertUtil convertUtil = convertUtilMap.get(ConvertWithBoilerplate.class);
        this.userToUserPayloadTest(convertUtil);
    }

    private void userToUserPayloadTest(ConvertUtil convertUtil) {
        User user = User.builder()
                .id(1L)
                .title("This is User!!!")
                .build()
                ;
        UserPayload userPayload = convertUtil.userToUserPayload(user);

        assertEquals(1L, userPayload.id());
        assertEquals("This is User!!!", userPayload.title());
    }
}