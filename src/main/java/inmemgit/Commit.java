package inmemgit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

final class Commit {
  private final Map<String, Object> snapshot = new HashMap<String, Object>();
  private final String message;
  private final List<Commit> parents;

  public Commit(String message, Index index, List<Commit> parents) {
    this.message = message;
    this.parents = parents;
    for (Commit parent: parents)
      for (Map.Entry<String, Object> entry: parent.snapshot.entrySet())
        if (snapshot.containsKey(entry.getKey()))
	        throw new IllegalStateException("No merge has been implemented.");
        else
          snapshot.put(entry.getKey(), entry.getValue());

    for (Entry<String, Object> item: index.items())
      if (snapshot.containsKey(item.getKey()))
        throw new IllegalStateException("No merge has been implemented.");
      else
        snapshot.put(item.getKey(), item.getValue());
  }

  public Object show(String name) {
    return snapshot.get(name);
  }
}
