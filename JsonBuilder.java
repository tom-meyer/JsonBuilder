public class JsonBuilder {

  public static JsonValue theNullValue = new JsonToken("null");

  public JsonValue value(String value) {
    return value == null ? theNullValue : new JsonString(value);
  }

  public JsonValue value(boolean value) {
    return new JsonToken(value ? "true" : "false");
  }

  public JsonValue value(int value) {
    return new JsonToken(Integer.toString(value));
  }

  public JsonValue number(String value) {
    return value == null ? theNullValue : new JsonToken(value);
  }

  public JsonValue nullValue() {
    return theNullValue;
  }

  public JsonArray array(String ... elements) {
    java.util.ArrayList<JsonValue> values = new java.util.ArrayList<JsonValue>();
    for(String element : elements) {
      values.add(new JsonString(element));
    }
    return new JsonArray(values.toArray(new JsonValue[0]));
  }

  public JsonArray array(int ... elements) {
    java.util.ArrayList<JsonValue> values = new java.util.ArrayList<JsonValue>();
    for(int element : elements) {
      values.add(new JsonToken(Integer.toString(element)));
    }
    return new JsonArray(values.toArray(new JsonValue[0]));
  }

  public JsonArray array(JsonValue ... elements) {
    return new JsonArray(elements);
  }

  public JsonObject pair(String name, String value) {
    return new JsonObject(name, value);
  }

  public JsonObject pair(String name, JsonValue value) {
    return new JsonObject(name, value);
  }

  public static interface JsonValue { }

  public static class JsonToken implements JsonValue {
    String value;

    public JsonToken(String value) {
      this.value = value;
    }

    public String toString() {
      return value;
    }
  }

  public static class JsonString implements JsonValue {
    String value;

    public JsonString(String value) {
      this.value = value;
    }

    public String toString() {
      return String.format("\"%s\"", value.replaceAll("\"", "\\\\\""));
    }
  }

  public static class JsonArray implements JsonValue {
    JsonValue elements[];

    public JsonArray(JsonValue ... elements) {
      this.elements = elements;
    }

    public String toString() {
      String comma = "";
      StringBuilder sb = new StringBuilder();
      sb.append("[");
      for(JsonValue element : elements) {
        sb.append(comma);
        sb.append(element.toString());
        comma = ",";
      }
      sb.append("]");
      return sb.toString();
    }
  }

  public static class JsonObject implements JsonValue {
    java.util.Map<String,JsonValue> pairs;

    public JsonObject(String name, JsonValue value) {
      pairs = new java.util.HashMap<String,JsonValue>();
      pairs.put(name, value);
    }

    public JsonObject(String name, String value) {
      pairs = new java.util.HashMap<String,JsonValue>();
      pairs.put(name, new JsonString(value));
    }

    public JsonObject pair(String name, JsonValue value) {
      pairs.put(name, value);
      return this;
    }

    public JsonObject pair(String name, String value) {
      pairs.put(name, new JsonString(value));
      return this;
    }

    public String toString() {
      String comma = "";
      StringBuilder sb = new StringBuilder();
      sb.append("{");
      for(String name : pairs.keySet()) {
        sb.append(comma);
        sb.append(String.format("\"%s\":%s", name, pairs.get(name).toString()));
        comma = ",";
      }
      sb.append("}");
      return sb.toString();
    }
  }
}
