import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntFunction;

/*
---------------------------------------------
   наносек: ArrayList
---------------------------------------------
   1874900: Добавление в конец полностью заполненного ArrayList
   2088300: Добавление в начало полностью заполненного ArrayList
   1912500: Добавление в середину полностью заполненного ArrayList
    242500: Добавление в начало ArrayList
    122600: Добавление в середину ArrayList
      4500: Добавление в конец ArrayList
    242500: Удаление из начала ArrayList
    181300: Удаление из середины ArrayList
      2300: Удаление из конца ArrayList
      1900: Индексация в конце ArrayList
      1800: Индексация в середине ArrayList
      1800: Индексация в начале ArrayList
---------------------------------------------
   наносек: LinkedList
---------------------------------------------
      6400: Добавление в начало LinkedList
   2665400: Добавление в середину LinkedList
      4300: Добавление в конец LinkedList
      5200: Удаление из начала LinkedList
   1314100: Удаление из середины LinkedList
      1100: Удаление из конца LinkedList
      1300: Индексация в конце LinkedList
    632000: Индексация в середине LinkedList
       600: Индексация в начале LinkedList
*/
public class Main {
    public static void main(String[] args) {
        //Бонус
        ArrayList<Double> arrayList = new Random()
                .doubles(1000000, 0, 100)
                .boxed()
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        LinkedList<Double> linkedList = new LinkedList<>(arrayList);

        //Тестирование ArrayList
        System.out.println("---------------------------------------------");
        System.out.printf("%10s: ArrayList%n", "наносек");
        System.out.println("---------------------------------------------");

        arrayList.trimToSize();
        System.out.printf("%10d: Добавление в конец полностью заполненного ArrayList%n", test(arrayList::add, 5.0));
        arrayList.trimToSize();
        System.out.printf("%10d: Добавление в начало полностью заполненного ArrayList%n", test(arrayList::add, 0, 5.0));
        arrayList.trimToSize();
        System.out.printf("%10d: Добавление в середину полностью заполненного ArrayList%n", test(arrayList::add, arrayList.size() / 2, 5.0));

        benchmark(arrayList);

        //Тестирование LinkedList
        System.out.println("---------------------------------------------");
        System.out.printf("%10s: LinkedList%n", "наносек");
        System.out.println("---------------------------------------------");

        benchmark(linkedList);
    }

    public static void benchmark(List<Double> list) {
        String type = list.getClass().getSimpleName();
        System.out.printf("%10d: Добавление в начало %s%n", test(list::add, 0, 5.0), type);
        System.out.printf("%10d: Добавление в середину %s%n", test(list::add, list.size() / 2, 5.0), type);
        System.out.printf("%10d: Добавление в конец %s%n", test(list::add, 5.0), type);
        System.out.printf("%10d: Удаление из начала %s%n", test(list::remove, 0), type);
        System.out.printf("%10d: Удаление из середины %s%n", test(list::remove, list.size() / 2), type);
        System.out.printf("%10d: Удаление из конца %s%n", test(list::remove, list.size() - 1), type);
        System.out.printf("%10d: Индексация в конце %s%n", test(list::get, list.size() - 1), type);
        System.out.printf("%10d: Индексация в середине %s%n", test(list::get, list.size() / 2), type);
        System.out.printf("%10d: Индексация в начале %s%n", test(list::get, 0), type);
    }

    public static <T, R> long test(Function<T, R> function, T value) {
        long start = System.nanoTime();
        function.apply(value);
        long end = System.nanoTime();
        return end - start;
    }

    public static <T, U> long test(BiConsumer<T, U> biConsumer, T value1, U value2) {
        long start = System.nanoTime();
        biConsumer.accept(value1, value2);
        long end = System.nanoTime();
        return end - start;
    }

    public static <R> long test(IntFunction<R> intFunction, int value) {
        long start = System.nanoTime();
        intFunction.apply(value);
        long end = System.nanoTime();
        return end - start;
    }
}