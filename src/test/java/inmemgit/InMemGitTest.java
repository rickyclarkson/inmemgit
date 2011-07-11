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
}