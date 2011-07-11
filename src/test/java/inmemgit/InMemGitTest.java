package inmemgit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
}