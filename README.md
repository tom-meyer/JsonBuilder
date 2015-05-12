# JsonBuilder

    JsonBuilder j = new JsonBuilder();
    String json = j
      .pair("make", "VW")
      .pair("model", "GTI")
      .toString();
    System.out.println(json); // -> {"model":"GTI","make":"VW"}

    json = j.array("VW", "BMW", "Mercedes").toString();
    System.out.println(json); // -> ["VW","BMW","Mercedes"]
