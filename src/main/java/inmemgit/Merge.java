package inmemgit;

public abstract class Merge {
  public static final Merge ours = new Merge() {
    @Override
    public Object merge(Object ours, Object... theirs) {
      return ours;
    }
  };
  
  public static Merge theirs = new Merge() {
    @Override
    public Object merge(Object ours, Object... theirs) {
      if (theirs.length == 1)
        return theirs[0];
      throw null;
    }
  };
  public static final Merge error = new Merge() {
    @Override
    public Object merge(Object ours, Object... theirs) {
      throw null;
    }
  };

  public abstract Object merge(Object ours, Object... theirs);
}
