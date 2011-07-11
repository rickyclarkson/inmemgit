package inmemgit;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

final class Index {
  private final Map<String, Object> hash = new HashMap<String, Object>();

  public Index add(String name, Object item) {
    Index index = new Index();
    index.hash.putAll(hash);
    index.hash.put(name, item);
    return index;
  }

  public Iterable<Entry<String, Object>> items() {
    return hash.entrySet();
  }
}
