package net.thumbtack.school.database.func;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class FunctionalCase {
    //1)
    String str = "cтрока с пробелами";
    Function<String, List<String>> split = s -> Arrays.asList(s.split(" "));
    Function<List<?>, Integer> count = list -> list.size();
    List<String> stringParts = split.apply(str);
    int elements = split.andThen(count).apply(str);

    /*
        2)
        Есть возможность избавиться от декларации типов в параметрах функций
        благодаря тому, что компилятор может сам определять тип на сонове контекста
    */

    //3)
    //Замена возможна в случае, если лямбда-выражение просто вызывает метод без дополнительной логики или вычислений
    Function<String, List<String>> splitRef = StringExecuter::split;
    Function<List<?>, Integer> countRef = List::size;

    //4)
    //andThen() и compose() позволяют композировать функции в более явном и читаемом виде,
    //а count.apply(split.apply(str)) позволяет передавать результаты одних функций (split.apply()) и
    //передавать в другие (count.apply())
    //a
    Function<String, Integer> andThenVersion = split.andThen(count);
    //b
    Function<String, Integer> composeVersion = count.compose(split);

    //5)
    Function<String, Person> personByLamda = name -> new Person(name);
    Function<String, Person> personByRef = Person::new;

    //6)
    //BinaryOperator принимает два аргумента одного типа и возвращает результат того же типа
    BinaryOperator<Integer> max = Math::max;

    //7)
    //Supplier определяет метод get() без параметров и возвращает значение типа T
    Supplier<Date> getCurrentDate = Date::new;
    Date currentDate = getCurrentDate.get();

    //8)
    //Predicate принимает параметр T и возвращает Boolean
    Predicate<Integer> isEven = a -> a % 2 == 0;

    //9)
    //BiPredicate принимает T и U и возвращает Boolean
    BiPredicate<Integer, Integer> areEqual = (a, b) -> Objects.equals(a, b);

    //10)
    MyFunction<String, List<String>> mySplit = s -> Arrays.asList(s.split(" "));

    //11)
    //Т.к. MyFunction больше не функциональный интерфейс, то получим ошибку компиляции,
    //@FunctionalInterface сообщает компилятору, что интерфейс должен содержать только один абстрактный метод,
    //но это не так, поскольку интерфейс будет иметь больше одного абстрактного метода

    //12)
    //См. классы PersonWithOptional и PersonWithoutOptional

    //13)
    public static void transform(IntStream stream, IntUnaryOperator op) {
        stream.map(op).forEach(System.out::println);
    }

    //14)
    public static IntStream transformAnother(IntStream stream, IntUnaryOperator op) {
        return stream.map(op);
    }
    IntStream stream = Arrays.stream(new int[]{1, 2, 3, 4, 5});
    IntUnaryOperator op = x -> x * 2;
    IntStream result = transformAnother(stream, op).parallel();

    //15)
    List<PersonWithAge> personList = Arrays.asList(
            new PersonWithAge("Анатолий", 13),
            new PersonWithAge("Мирон", 38),
            new PersonWithAge("Игнат", 11),
            new PersonWithAge("Василий", 72),
            new PersonWithAge("Александр", 45),
            new PersonWithAge("Пётр", 24)
    );
    List<String> uniqueNamesByNameLength = personList
            .stream()
            .filter(p -> p.getAge() > 30)
            .map(PersonWithAge::getName)
            .sorted(Comparator.comparing(String::length).reversed())
            .collect(Collectors.toList());

    //16)
    List<String> uniqueNamesBySameNames = personList
            .stream()
            .filter(p -> p.getAge() > 30)
            .collect(Collectors.groupingBy(PersonWithAge::getName, Collectors.counting()))
            .entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());

    //17)
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
    int sum = numbers.stream().reduce(0, Integer::sum);
    int product = numbers.stream().reduce(1, (a,b) -> a * b);
}
