package bitcamp.pms.util;

public class LinkedList<T> {
  Bucket<T> start;
  Bucket<T> end;
  int count;

  public LinkedList() {
    start = new Bucket<>();
    end = start;
  }

  public int size() {
      return this.count;
  }

  public void add(T value) {
    end.value = value;
    end.next = new Bucket<>();
    end = end.next;
    count++;
  }

  public void add(int index, T value) {
    //1) index 유효범위 검사
    if (index < 0 || index > this.count) {
      return;
    }

    //2) index가 0일 때
    if (index == 0) {
      Bucket<T> newBucket = new Bucket<>(value, start);
      start = newBucket;
      count++;
      return;
    }

    //3) index가 빈 버킷을 가리킬 때
    if (index == this.count) {
      this.add(value);
      return;
    }

    //4) 그 밖에
    // => 인덱스가 가리키는 항목의 직전 항목을 찾아서 새 버킷을 연결한다.
    Bucket<T> cursor = start;
    for (int i = 0; i < (index - 1); i++) {
      cursor = cursor.next;
    }

    cursor.next = new Bucket<>(value, cursor.next);
    count++;
  }

  public T get(int index) {
    Bucket<T> cursor = start;
    for (int i = 0; i < index; i++) {
      cursor = cursor.next;
    }
    return cursor.value;
  }

  public T remove(int index) {
    //1) index의 유효여부 검사: 0 미만 또는 count 이상이면 무효한 인덱스.
    if (index < 0 || index >= count) {
      return null;
    }

    T temp = null;
    count--;

    //2) 0번째를 지울 때
    if (index == 0) {
      temp = start.value;
      start = start.next;
      return temp;
    }

    //3) 1 이상의 인덱스를 지우기
    // => 해당 인덱스의 전 항목을 찾아서
    //    다음 다음 항목으로 바로 연결한다.
    Bucket<T> cursor = start;
    for (int i = 0; i < (index - 1); i++) {
      cursor = cursor.next;
    }

    temp = cursor.next.value;
    cursor.next = cursor.next.next;
    return temp;
  }

  public T set(int index, T value) {
    //1) index의 유효여부 검사: 0 미만 또는 count 이상이면 무효한 인덱스.
    if (index < 0 || index >= count) {
      return null;
    }

    Bucket<T> cursor = start;
    for (int i = 0; i < index; i++) {
      cursor = cursor.next;
    }

    T temp = cursor.value;
    cursor.value = value;
    return temp;
  }
}
