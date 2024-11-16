package app.db.utils;

public interface Factory {
    <T> T createService(Class<T> serviceClass);

    <T> T createService(String name);
}