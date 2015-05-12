public class JsonBuilder {

  public static JsonValue theNullValue = new JsonToken("null");

  public static JsonValue value(String value) {
    return value == null ? theNullValue : new JsonString(value);
  }

  public static JsonValue value(boolean value) {
    return new JsonToken(value ? "true" : "false");
  }

  public static JsonValue value(int value) {
    return new JsonToken(Integer.toString(value));
  }

  public static JsonValue number(String value) {
    return value == null ? theNullValue : new JsonToken(value);
  }

  public static JsonValue nullValue() {
    return theNullValue;
  }

  public JsonArray value(String value, String ... elements) {
    String newElements[] = new String[elements.length+1];
    newElements[0] = value;
    for(int i = 0; i < elements.length; ++i) {
      newElements[i+1] = elements[i];
    }
    return array(newElements);
  }

  public JsonArray value(int value, int ... elements) {
    int newElements[] = new int[elements.length+1];
    newElements[0] = value;
    for(int i = 0; i < elements.length; ++i) {
      newElements[i+1] = elements[i];
    }
    return array(newElements);
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

  public static JsonArray array(JsonValue ... elements) {
    return new JsonArray(elements);
  }

  public static JsonObject pair(String name, int value) {
    return new JsonObject().pair(name, value);
  }

  public static JsonObject pair(String name, String value) {
    return new JsonObject().pair(name, value);
  }

  public static JsonObject pair(String name, JsonValue value) {
    return new JsonObject().pair(name, value);
  }

  public static JsonVariable var(String name) {
    return new JsonVariable(name);
  }

  //////////////////////////////////////////////////

  public interface JsonValue {
    JsonValue replace(String name, int value);
    JsonValue replace(String name, String value);
    JsonValue replace(String name, JsonValue value);
  }

  public static class JsonVariable implements JsonValue {
    String name;

    public JsonVariable(String name) {
      this.name = name;
    }

    public JsonValue replace(String name, int value) {
      return this.replace(name, JsonBuilder.value(value));
    }

    public JsonValue replace(String name, String value) {
      return this.replace(name, JsonBuilder.value(value));
    }

    public JsonValue replace(String name, JsonValue value) {
      return this.name.equals(name) ? value : this;
    }

    public String toString() {
      return theNullValue.toString();
    }
  }

  public static class JsonToken implements JsonValue {
    String value;

    public JsonToken(String value) {
      this.value = value;
    }

    public JsonValue replace(String name, int value) {
      return this.replace(name, JsonBuilder.value(value));
    }

    public JsonValue replace(String name, String value) {
      return this.replace(name, JsonBuilder.value(value));
    }

    public JsonValue replace(String name, JsonValue value) {
      return this;
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

    public JsonValue replace(String name, int value) {
      return this.replace(name, JsonBuilder.value(value));
    }

    public JsonValue replace(String name, String value) {
      return this.replace(name, JsonBuilder.value(value));
    }

    public JsonValue replace(String name, JsonValue value) {
      return this;
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

    public JsonValue replace(String name, int value) {
      return this.replace(name, JsonBuilder.value(value));
    }

    public JsonValue replace(String name, String value) {
      return this.replace(name, JsonBuilder.value(value));
    }

    public JsonValue replace(String name, JsonValue value) {
      JsonValue newElements[] = new JsonValue[elements.length];
      int i = 0;
      for(JsonValue element : elements) {
        newElements[i++] = element.replace(name, value);
      }
      return new JsonArray(newElements);
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

    public JsonObject() {
      pairs = new java.util.HashMap<String,JsonValue>();
    }

    public JsonObject pair(String name, JsonValue value) {
      pairs.put(name, value);
      return this;
    }

    public JsonObject pair(String name, String value) {
      return this.pair(name, JsonBuilder.value(value));
    }

    public JsonObject pair(String name, int value) {
      return this.pair(name, JsonBuilder.value(value));
    }

    public JsonValue replace(String name, int value) {
      return this.replace(name, JsonBuilder.value(value));
    }

    public JsonValue replace(String name, String value) {
      return this.replace(name, JsonBuilder.value(value));
    }

    public JsonValue replace(String varName, JsonValue value) {
      JsonObject newObject = new JsonObject();
      for(String name : pairs.keySet()) {
        newObject.pair(name, pairs.get(name).replace(varName, value));
      }
      return newObject;
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
