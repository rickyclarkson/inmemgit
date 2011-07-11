package inmemgit;

import java.util.Collections;

public final class InMemGit {
  private Index index = new Index();
  private Commit head = new Commit("First commit", new Index(), Collections.<Commit>emptyList());

  public static InMemGit init() {
    return new InMemGit();
  }

  public void add(String name, Object item) {
    index = index.add(name, item);
  }

  public void commit(String message) {
    head = new Commit(message, index, Collections.singletonList(head));
    index = new Index();
  }

  public Object show(String name) {
    return head.show(name);
  }
}