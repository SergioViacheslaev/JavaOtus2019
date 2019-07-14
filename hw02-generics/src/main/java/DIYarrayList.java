import java.util.*;

public class DIYarrayList<E> implements List<E> {

    //Default initial capacity.
    private static final int DEFAULT_CAPACITY = 10;


    //empty array instance used for empty instances.
    private static final Object[] EMPTY_ELEMENTDATA = {};

    //Empty
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};


    //array for instances
    private Object[] elementData;


    // The size of the ArrayList (the number of elements it contains).
    private int size;

    /**
     * Constructs an empty list with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity
     *                                  is negative
     */
    public DIYarrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " +
                    initialCapacity);
        }
    }

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public DIYarrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Метод не реализован.");
    }

    @Override
    public Iterator<E> iterator() {
        return new DIYListIterator<E>(this);
    }


    /**
     * @return Copy of elementData
     */
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException("Метод не реализован.");
    }

    @Override
    public boolean add(E e) {

        if (size == elementData.length)
            elementData = grow(size + 1);
        elementData[size] = e;
        size++;


        return true;
    }

    private Object[] grow(int minCapacity) {
        return elementData = Arrays.copyOf(elementData,
                newCapacity(minCapacity));
    }

    /**
     * The maximum size of array to allocate (unless necessary).
     * Some VMs reserve some header words in an array.
     * Attempts to allocate larger arrays may result in
     * OutOfMemoryError: Requested array size exceeds VM limit
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     * Returns a capacity at least as large as the given minimum capacity.
     * Returns the current capacity increased by 50% if that suffices.
     * Will not return a capacity greater than MAX_ARRAY_SIZE unless
     * the given minimum capacity is greater than MAX_ARRAY_SIZE.
     *
     * @param minCapacity the desired minimum capacity
     * @throws OutOfMemoryError if minCapacity is less than zero
     */
    private int newCapacity(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity <= 0) {
            if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA)
                return Math.max(DEFAULT_CAPACITY, minCapacity);
            if (minCapacity < 0) // overflow
                throw new OutOfMemoryError();
            return minCapacity;
        }
        return (newCapacity - MAX_ARRAY_SIZE <= 0)
                ? newCapacity
                : hugeCapacity(minCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE)
                ? Integer.MAX_VALUE
                : MAX_ARRAY_SIZE;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Метод не реализован.");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Метод не реализован.");
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException("Метод не реализован.");
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException("Метод не реализован.");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Метод не реализован.");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Метод не реализован.");
    }

    /**
     * Removes all of the elements from this list.  The list will
     * be empty after this call returns.
     */
    @Override
    public void clear() {
        for (int i = 0; i < elementData.length; i++)
            elementData[i] = null;
        size = 0;
    }

    /**
     * At first method checks if this has specified index
     *
     * @param index of element
     * @return element at specified index
     */
    @Override
    public E get(int index) {
        checkIndex(index);
        return (E) elementData[index];
    }


    /**
     * Replaces the element at the specified position in this list with
     * the specified element.
     *
     * @param index   index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public E set(int index, E element) {

        checkIndex(index);
        E oldValue = (E) elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    //Check arrays has specified index
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            System.out.println();
            throw new IndexOutOfBoundsException(String.format("Index %d does not exist !", index));
        }
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException("Метод не реализован.");
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException("Метод не реализован.");
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException("Метод не реализован.");
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Метод не реализован.");
    }

    @Override
    public ListIterator<E> listIterator() {
        return new DIYListIterator<>(this);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException("Метод не реализован.");
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Метод не реализован.");
    }

 /*   *//**
     * @param c specified comparator
     *//*
    @Override
    public void sort(Comparator<? super E> c) {
        Object[] a = this.toArray();
        Arrays.sort(a, (Comparator) c);
        ListIterator<E> i = this.listIterator();
        for (Object e : a) {
            i.set((E) e);
        }
    }*/

    /**
     * @return string presentation of list
     * in this format [ firstElem, secondElem, ...]
     * and [] if list has no elements
     */
    @Override
    public String toString() {
        if (size == 0) return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {

            sb.append((E) elementData[i] + ", ");
        }

        sb = new StringBuilder(sb.substring(0, sb.lastIndexOf(",")) + "]");

        return sb.toString();


    }

    public class DIYListIterator<E> implements ListIterator<E> {
        int startPos; //Start position-index to iterate
        int cursor;       // index of next element to return
        int lastElemIndex = -1; // index of last element returned; -1 if no such
        private DIYarrayList<E> list;

        DIYListIterator() {
        }

        public DIYListIterator(DIYarrayList<E> list) {
            /*Every new Iterator has cursor at position index = 0
              To start every time from beginnig of array
             */
            cursor = 0;
            this.list = list;
        }

        public boolean hasNext() {
            return (cursor + 1) != size;
        }


        public E next() {
            cursor = startPos;
            startPos++;
            return list.get(cursor);


        }

        @Override
        public void set(E e) {
            if (cursor != -1) {
                list.set(cursor, e);
            }

        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public E previous() {
            return null;
        }

        @Override
        public int nextIndex() {
            return 0;
        }

        @Override
        public int previousIndex() {
            return 0;
        }

        @Override
        public void remove() {

        }

        @Override
        public void add(E t) {

        }
    }


}
