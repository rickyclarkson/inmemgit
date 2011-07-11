package inmemgit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

final class Commit implements HasCommit {
  private final Map<String, Object> snapshot = new HashMap<String, Object>();
  private final String message;
  public final List<Commit> parents;

  public Commit(String message, Index index, List<Commit> parents, Merge merge) {
    this.message = message;
    this.parents = parents;
    for (Commit parent: parents)
      for (Map.Entry<String, Object> entry: parent.snapshot.entrySet())
        if (snapshot.containsKey(entry.getKey()))
	        snapshot.put(entry.getKey(), merge.merge(snapshot.get(entry.getKey()), entry.getValue()));
        else
          snapshot.put(entry.getKey(), entry.getValue());

    for (Entry<String, Object> item: index.items())
      snapshot.put(item.getKey(), item.getValue());
  }

  public Object show(String name) {
    return snapshot.get(name);
  }

  public Commit minus(int number) {
    if (parents.size() > 1)
        throw null;

    if (number == 1)
        return parents.get(0);

    return parents.get(0).minus(number - 1);
  }

  @Override
  public Commit getCommit() {
    return this;
  }

  @Override
  public <R> R accept(Visitor<R> visitor) {
    return visitor.visitCommit(this);
  }

  public boolean isDescendentOf(Commit commit) {
    for (Commit parent: parents)
      if (parent.equals(commit) || parent.isDescendentOf(commit))
        return true;
    return false;
  }
}
