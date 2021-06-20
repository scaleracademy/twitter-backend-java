package xyz.subho.clone.twitter.utility;

public interface Mapper<S, T> {

  T transform(S source);

  S transformback(T source);
}
