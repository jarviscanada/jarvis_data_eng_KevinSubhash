package ca.jrvs.apps.practice;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaStreamImp implements LambdaStreamExc{

    @Override
    public Stream<String> createStrStream(String... strings) {
        Stream<String> stream = Stream.of(strings);
        return stream;
    }

    @Override
    public Stream<String> toUpperCase(String... strings) {
        Stream<String> stream = createStrStream(strings).map(s -> s.toUpperCase());
        return stream;
    }

    @Override
    public Stream<String> filter(Stream<String> stringStream, String pattern) {
        Stream<String> stream = stringStream.filter((s -> !s.contains(pattern)));
        return stream;
    }

    @Override
    public IntStream createIntStream(int[] arr) {
        IntStream intStream = Arrays.stream(arr);
        return intStream;
    }

    @Override
    public <E> List<E> toList(Stream<E> stream) {
        List<E> list = stream.collect(Collectors.toList());
        return list;
    }

    @Override
    public List<Integer> toList(IntStream intStream) {
        List<Integer> list = intStream.boxed().collect(Collectors.toList());
        return list;
    }

    @Override
    public IntStream createIntStream(int start, int end) {
        IntStream stream = IntStream.range(start, end);
        return stream;
    }

    @Override
    public DoubleStream squareRootIntStream(IntStream intStream) {
        DoubleStream stream = intStream.asDoubleStream().map(n -> Math.sqrt(n));
        return stream;
    }

    @Override
    public IntStream getOdd(IntStream intStream) {
        IntStream stream = intStream.filter(n -> n%2 == 1);
        return stream;
    }

    @Override
    public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
        Consumer<String> printer = s -> System.out.println(prefix + s + suffix);
        return printer;
    }

    @Override
    public void printMessages(String[] messages, Consumer<String> printer) {
        createStrStream(messages).forEach(printer);
    }

    @Override
    public void printOdd(IntStream intStream, Consumer<String> printer) {
        getOdd(intStream).mapToObj(Integer::toString).forEach(printer);
    }

    @Override
    public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
        Stream<Integer> stream = ints.flatMap(n -> n.stream()).map(n -> n*n);
        return stream;
    }
}