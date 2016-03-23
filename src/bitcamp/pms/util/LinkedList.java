package bitcamp.pms.util;

import bitcamp.pms.exception.OutOfIndexException;


public class LinkedList<T> {
  Bucket<T> start;
    Bucket<T> end;
    int cnt;

    public int size() {
      return cnt;
    }

    public LinkedList() {
      start = new Bucket<>();
      end = start;

    }

    public void add(T value) {
      end.value = value;
      end.next = new Bucket<>();
      end = end.next;
      cnt++;
    }

    public void add(int index, T value) {
      if(index < 0 || index > cnt) {
        throw new OutOfIndexException("error");
      }
      cnt++;
      if(index == cnt) {
        add(value);
        return;
      }

      Bucket<T> cursor = previousBucket(index, start);
      Bucket<T> newBucket = cursor.next;
      newBucket.value = value;
      newBucket.next = cursor.next.next;


    }

    public T remove(int index) {
      if(index < 0 || index >= cnt) {
        throw new OutOfIndexException("error");
      }
      cnt--;
      Bucket<T> previousBucket = previousBucket(index, start);
      Bucket<T> deleteBucket = previousBucket.next;
      previousBucket.next = previousBucket.next.next;

      return deleteBucket.value;
    }

    public T set(int index, T value) {
      if(index < 0 || index >= cnt) {
        throw new OutOfIndexException("error");
      }

        Bucket<T> previousBucket = currentBucket(index, start);
        Bucket<T> updateBucket = previousBucket;
        updateBucket.value = value;
        return previousBucket.value;
    }
    public T get(int index) {
      if(index < 0 || index >= cnt) {
        throw new OutOfIndexException("error");
      }

      Bucket<T> print = currentBucket(index, start);
      return print.value;
    }

    public Bucket<T> previousBucket(int index, Bucket<T> cursor) {
      for (int i = 1; i <index; i++) {
        cursor = cursor.next;
      }

      return cursor;
    }

    public Bucket<T> currentBucket(int index, Bucket<T> cursor) {
      for (int i = 0; i <index; i++) {
        cursor = cursor.next;
      }

      return cursor;
    }
}
