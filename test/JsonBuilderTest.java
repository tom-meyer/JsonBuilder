import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class JsonBuilderTest {

  JsonBuilder J = new JsonBuilder();

  @Test
  public void string() {
    assertEquals("\"name\"", J.value("name").toString());
  }

  @Test
  public void stringWithDoubleQuote() {
    assertEquals("\"He said \\\"Hello\\\"\"", J.value("He said \"Hello\"").toString());
  }

  @Test
  public void integer() {
    assertEquals("333", J.value(333).toString());
  }

  @Test
  public void number() {
    assertEquals("3.3", J.number("3.3").toString());
  }

  @Test
  public void booleanValue() {
    assertEquals("true", J.value(true).toString());
  }

  @Test
  public void nullValue() {
    assertEquals("null", J.nullValue().toString());
    assertEquals("null", J.value(null).toString());
  }

  @Test
  public void implicitArrayOfStrings() {
    assertEquals("[\"one\",\"two\",\"three\"]", J.value("one", "two", "three").toString());
  }

  @Test
  public void implicitArrayOfNumbers() {
    assertEquals("[1,1,2,3,5]", J.value(1, 1, 2, 3, 5).toString());
  }

  @Test
  public void array() {
    assertEquals("[\"three\",3]", J.array(J.value("three"), J.value(3)).toString());
  }

  @Test
  public void arrayOfStrings() {
    assertEquals("[\"cat\",\"dog\"]", J.array("cat", "dog").toString());
  }

  @Test
  public void arrayOfArray() {
    Object array = J.array(J.array("cat", "kitty"),
                           J.array("dog", "doggy"));
    assertEquals("[[\"cat\",\"kitty\"],[\"dog\",\"doggy\"]]", array.toString());
  }

  @Test
  public void object() {
    Object value = J.pair("one", "1").pair("two", "2");
    assertEquals("{\"one\":\"1\",\"two\":\"2\"}", value.toString());
  }

  @Test
  public void objectWithArray() {
    Object value = J.pair("numbers", J.array("1", "2"));
    assertEquals("{\"numbers\":[\"1\",\"2\"]}", value.toString());
  }
}
