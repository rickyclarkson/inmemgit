package inmemgit;

public interface HasCommit {
  Commit getCommit();

  <R> R accept(Visitor<R> visitor);

  public interface Visitor<R> {
    R visitBranch(Branch branch);
    R visitCommit(Commit commit);
  }
}
