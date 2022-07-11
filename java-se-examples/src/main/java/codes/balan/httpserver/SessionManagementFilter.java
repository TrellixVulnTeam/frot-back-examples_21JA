package codes.balan.httpserver;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SessionManagementFilter extends Filter {

    private final SecureRandom sr = new SecureRandom();
    private final Map<Integer, SessionData> sessions = new HashMap<>();

    @Override
    public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "http://localhost:4200");
        exchange.getResponseHeaders().add("Content-Type", "application/json");
//        if (exchange.getRequestURI().toString().equals("/session") && exchange.getRequestMethod().equals("POST")) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Credentials", "true");
//        }

        // si entramos en /session y no estamos logueados? chau

        System.out.println("----------------------------");
        System.out.println(exchange.getRequestMethod()+" "+ exchange.getRequestURI());

        getSessionCookie(exchange);

        chain.doFilter(exchange);

        if (exchange.getRequestMethod().equals("OPTIONS")) {
            System.out.println("Entramos en Options");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "*, content-type");
            exchange.sendResponseHeaders(200, 0);
        }

        exchange.close();
    }



    private Integer getSessionCookie(HttpExchange exchange) {
        System.out.println("getCookie()");
        try {
            Integer a =Integer.parseInt(exchange.getRequestHeaders().get("Cookie").get(0).split("=")[1]);
            System.out.println(a);
            return a;
        } catch (Exception ignore) {
        }
        return null;
    }




    private SessionData getSessionData(HttpExchange exchange) {
        System.out.println("getSessionData()");
        Integer sessionCookieId = getSessionCookie(exchange);
        if (sessionCookieId == null || !sessions.containsKey(sessionCookieId)) {
            System.out.println("Cookie venia vacia");
            sessionCookieId = Math.abs(sr.nextInt());
            exchange.getResponseHeaders().add("Set-Cookie", "JSESSIONID=" + sessionCookieId);
            sessions.put(sessionCookieId, new SessionData());
            System.out.println(sessionCookieId);
        }
        return sessions.get(sessionCookieId);
    }



    public boolean isLogged(HttpExchange exchange) {
        System.out.println("isLogged()");
        SessionData sd = getSessionData(exchange);
        System.out.println("Logueado  =  "+sd.getLogueado());
        return sd.getLogueado();
    }
    public boolean doLogin(String user, String password, HttpExchange exchange){
        SessionData sd = getSessionData(exchange);
        boolean loggedIn = user.toUpperCase(Locale.ROOT).equals(password);
        if (loggedIn) {
            sd.loggedIn(user, password);
        }
        return loggedIn;
    }
    public void logout(HttpExchange exchange){
        Integer sessionCookieId = getSessionCookie(exchange);
        if (sessionCookieId!= null) {
            exchange.getResponseHeaders().add("Set-Cookie", "JSESSIONID="+null);
            sessions.remove(sessionCookieId);
        }
    }
    public boolean isAdmin(HttpExchange exchange){
        SessionData sd = getSessionData(exchange);
        return sd.isAdmin();
    }
    @Override
    public String description() {
        return "SessionManagementg";
    }
}
