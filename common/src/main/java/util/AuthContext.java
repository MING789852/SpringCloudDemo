package util;

public class AuthContext {
    private static ThreadLocal<String> auth=new ThreadLocal<>();

    public static void setAuth(String auth) {
        AuthContext.auth.set(auth);
    }

    public static String getAuth() {
        return AuthContext.auth.get();
    }

    public static void remove(){
        AuthContext.auth.remove();
    }
}
