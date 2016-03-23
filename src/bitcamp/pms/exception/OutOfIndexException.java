package bitcamp.pms.exception;

public class OutOfIndexException extends RuntimeException{

  public OutOfIndexException() {
  super();
  }

  public OutOfIndexException(String msg) {
    super(msg);
  }
}
