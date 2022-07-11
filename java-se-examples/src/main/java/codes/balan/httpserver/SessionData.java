package codes.balan.httpserver;

public class SessionData {

private String user;
private String password;
private boolean logueado = false;

private final long createdTimestamp;

    public SessionData () {
        createdTimestamp = System.currentTimeMillis();
    }

    public void loggedIn(String user, String password) {
        logueado = true;
        this.user = user;
        this.password = password;
    }

    public boolean getLogueado() {
        return logueado;
    }

    public boolean isAdmin(){
        String alan="alan";
        if(this.user.equals(alan)){
            return true;
        }
        return false;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

}
