# DIY ArrayList
Написать свою реализацию ArrayList на основе массива.
class ru.otus.generics.DIYarrayList<T> implements List<T>{...}

Проверить, что на ней работают методы из java.util.Collections:
Collections.addAll(Collection<? super T> c, T... elements)
Collections.static <T> void copy(List<? super T> dest, List<? extends T> src)
Collections.static <T> void sort(List<T> list, Comparator<? super T> c)

1) Проверяйте на коллекциях с 20 и больше элементами.
2) ru.otus.generics.DIYarrayList должен имплементировать ТОЛЬКО ОДИН интерфейс - List.
3) Если метод не имплементирован, то он должен выбрасывать исключение UnsupportedOperationException.