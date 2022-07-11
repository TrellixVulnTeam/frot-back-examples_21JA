package codes.balan.httpserver;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import org.json.JSONObject;
import org.json.JSONException;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class BackendPelado {
    private static final byte[] LOGIN_FALSE_JSON = """
        {"login":"false"}
        """.getBytes(StandardCharsets.UTF_8);
    private static final byte[] LOGIN_TRUE_JSON = """
                {"login":"true"}
                """.getBytes(StandardCharsets.UTF_8);
    private static final byte[] ADMIN_TRUE_JSON = """
                {"admin":"true"}
                """.getBytes(StandardCharsets.UTF_8);
    private static final byte[] ADMIN_FALSE_JSON = """
                {"admin":"false"}
                """.getBytes(StandardCharsets.UTF_8);

    public static void main(String[] args) throws Exception {
        SessionManagementFilter smf = new SessionManagementFilter();
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 1);

//----------------------------------------------------------------------------------------------------------
        //session devuelve un json {"logget": true o false} para saber si esta logeado
        // y para crear una session si no la tiene y esto tiene que correr ni vien el angular funcione
        HttpContext sessionCtx = server.createContext("/session", exchange -> {
            if (!exchange.getRequestMethod().equals("POST")) {
                return;
            }

            boolean loggedIn = smf.isLogged(exchange);
            System.out.println(loggedIn);
            exchange.sendResponseHeaders(loggedIn ? 200 :  401, 0);
            exchange.getResponseBody().write(loggedIn ? LOGIN_TRUE_JSON : LOGIN_FALSE_JSON);
        });
        sessionCtx.getFilters().add(smf);

        HttpContext isadminCtx = server.createContext("/isadmin", exchange -> {
            if (!exchange.getRequestMethod().equals("POST")) {
                return;
            }

            boolean isAdmin = smf.isAdmin(exchange);
            exchange.sendResponseHeaders(isAdmin ? 200 :  401, 0);
            exchange.getResponseBody().write(isAdmin ? ADMIN_TRUE_JSON : ADMIN_FALSE_JSON);


        });
        isadminCtx.getFilters().add(smf);

        //logout es para setear el cookie a null de vuelta y devuelve
        HttpContext logoutContext = server.createContext("/logout", exchange -> {
            if (!exchange.getRequestMethod().equals("POST")) {
                return;
            }
            System.out.println("Entramos en Post");
            smf.logout(exchange);
            exchange.sendResponseHeaders(200, 0);
            exchange.getResponseBody().write("{\"logout\": true}".getBytes(StandardCharsets.UTF_8));
        });
        logoutContext.getFilters().add(smf);

//-------------------------------------------------------------------------------------------------------------------------
        //login es para saber si estas logeado pero todavia falta arreglar
        HttpContext loginContext = server.createContext("/login", exchange -> {
            if(!exchange.getRequestMethod().equals("POST")) {
                return;
            }

            String user= null;
            String password= null;
            String text = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            try {
                JSONObject jsonObject = new JSONObject(text);
                user = jsonObject.getString("user");
                password = jsonObject.getString("password");
            } catch (JSONException err) {
                err.printStackTrace();
            }
            // obtener usuario/contrasenha de exchage
            System.out.println("user: " + user + " and password: " + password);

            // Comprobar si usuario puede ingresar
            if(smf.doLogin(user,password, exchange)){
                System.out.println("Credenciales correctas");
                exchange.sendResponseHeaders(200, 0);
                exchange.getResponseBody().write(LOGIN_TRUE_JSON);
            }else{
                System.out.println("Credenciales incorrectas");
                exchange.sendResponseHeaders(401, 0);
                exchange.getResponseBody().write(LOGIN_FALSE_JSON);
            }
        });
        loginContext.getFilters().add(smf);

        server.start();
    }
}

