package inmemgit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class InMemGitTest {
  @Test
  public void retainsData() {
    InMemGit git = InMemGit.init();
    git.add("foo", "bar");
    git.add("spam", "eggs");
    git.commit("Initial commit.");

    assertEquals("bar", git.show("foo"));
    assertEquals("eggs", git.show("spam"));
  }

  @Test
  public void retainsDataAcrossTwoCommits() {
    InMemGit git = InMemGit.init();
    git.add("foo", "bar");
    git.commit("Initial commit.");

    git.add("spam", "eggs");
    git.commit("Second commit.");
    assertEquals("bar", git.show("foo"));
    assertEquals("eggs", git.show("spam"));
  }

  @Test
  public void branchesWork() {
    InMemGit git = InMemGit.init();
    git.add("foo", "bar");
    git.commit("Initial commit.");
    Branch master = git.getCurrentBranch().some();
    git.checkoutNewBranch("python");
    git.add("spam", "eggs");
    git.commit("Second commit.");
    git.checkout(master);
    assertEquals("bar", git.show("foo"));
    assertNotSame("eggs", git.show("spam"));
  }

  @Test
  public void canGoBackToAnOldCommit() {
    InMemGit git = InMemGit.init();
    git.add("foo", "bar");
    git.commit("Initial commit.");
    git.add("spam", "eggs");
    git.commit("Second commit.");
    git.checkout(git.head.minus(1));
    assertEquals("bar", git.show("foo"));
    assertNotSame("eggs", git.show("spam"));
  }

  @Test
  public void canMerge() {
    InMemGit git = InMemGit.init();
    git.add("foo", "bar");
    git.commit("Initial commit");
    Branch master = git.getCurrentBranch().some();
    Branch one = git.checkoutNewBranch("one");
    git.add("spam", "eggs");
    git.commit("One");
    git.checkout(master);
    git.add("spam", "fries");
    git.commit("Master");
    assertEquals("fries", git.show("spam"));
    git.merge(one, Merge.ours, "Merge");
    assertEquals("fries", git.show("spam"));
  }

  @Test
  public void canMergeWithDifferentStrategy() {
    InMemGit git = InMemGit.init();
    git.add("foo", "bar");
    git.commit("Initial commit");
    Branch master = git.getCurrentBranch().some();
    Branch one = git.checkoutNewBranch("one");
    git.add("spam", "eggs");
    git.commit("One");
    git.checkout(master);
    git.add("spam", "fries");
    git.commit("Master");
    git.merge(one, Merge.theirs, "Merge");
    assertEquals("eggs", git.show("spam"));
  }

  @Test
  public void canOverwrite() {
    InMemGit git = InMemGit.init();
    git.add("foo", "bar");
    git.commit("Initial commit");
    git.add("foo", "baz");
    git.commit("Second commit");
    assertEquals("baz", git.show("foo"));
  }

  @Test
  public void canClone() {
    InMemGit remote = InMemGit.init();
    remote.add("foo", "bar");
    remote.commit("Initial commit");

    InMemGit local = remote.cloneRepository();
    local.add("foo", "baz");
    local.commit("Second commit");
    local.push(remote, local.getCurrentBranch().some(), remote.getCurrentBranch().some());
    assertEquals("baz", remote.show("foo"));
  }
}