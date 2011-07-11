package inmemgit;

import fj.data.Option;
import java.util.Arrays;
import java.util.Collections;

public final class InMemGit {
  private Index index = new Index();
  public Commit head = new Commit("First commit", new Index(), Collections.<Commit>emptyList(), Merge.error);
  private Option<Branch> currentBranch = Option.some(new Branch("master", head));

  public static InMemGit init() {
    return new InMemGit();
  }

  public void add(String name, Object item) {
    index = index.add(name, item);
  }

  public void commit(String message) {
    commit(new Commit(message, index, Collections.singletonList(head), Merge.error));
  }

  private void commit(Commit commit) {
    head = commit;
    index = new Index();
    for (Branch b: currentBranch)
      b.head = head;
  }

  public Object show(String name) {
    return head.show(name);
  }

  public Branch checkoutNewBranch(String name) {
    Branch branch = new Branch(name, head);
    currentBranch = Option.some(branch);
    return branch;
  }

  public void checkout(HasCommit branch) {
    currentBranch = branch.accept(new HasCommit.Visitor<Option<Branch>>() {
      @Override
      public Option<Branch> visitBranch(Branch branch) {
        return Option.some(branch);
      }

      @Override
      public Option<Branch> visitCommit(Commit commit) {
        return Option.none();
      }
    });
    head = branch.getCommit();
  }

  public Option<Branch> getCurrentBranch() {
    return currentBranch;
  }

  public void merge(Branch branch, Merge merge, String message) {
    commit(new Commit(message, new Index(), Arrays.asList(head, branch.getCommit()), merge));
  }
}