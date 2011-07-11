package inmemgit;

public class Branch implements HasCommit {
  private final String name;
  public Commit head;

  public Branch(String name, Commit head) {
    this.name = name;
    this.head = head;
  }

  @Override
  public Commit getCommit() {
    return head;
  }

  @Override
  public <R> R accept(Visitor<R> visitor) {
    return visitor.visitBranch(this);
  }
}
