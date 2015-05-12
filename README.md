# JsonBuilder

## Basic usage
    JsonBuilder j = new JsonBuilder();
    String json = j
      .pair("make", "VW")
      .pair("model", "GTI")
      .toString();
    System.out.println(json); // -> {"model":"GTI","make":"VW"}

    json = j.array("VW", "BMW", "Mercedes").toString();
    System.out.println(json); // -> ["VW","BMW","Mercedes"]

## Variable substitution
    JsonBuilder j = new JsonBuilder();
    json = j.pair("name", j.var("$NAME"))
        .replace("$NAME", "Hodor").toString();
    System.out.println(json); // -> {"name":"Hodor"}
