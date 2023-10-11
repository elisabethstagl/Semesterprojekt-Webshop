package com.webshop.demo.config;

// Beschreibung:
// Dieser Code ist eine Java-Klasse namens CORSFilter, die im Paket com.webshop.demo.config platziert ist und für das Management der Cross-Origin Resource Sharing (CORS)-Einstellungen in einer Webanwendung unter Verwendung der Jakarta Servlet API und des Spring-Frameworks verantwortlich ist. CORS ist ein von Webbrowsern implementiertes Sicherheitsfeature, das einschränkt, wie Ressourcen auf einer Webseite von einer anderen Domain angefordert werden können, die nicht die Domain ist, von der die Ressource stammt. Durch das Setzen spezifischer HTTP-Header (wie Access-Control-Allow-Origin) können Server beschreiben, wie der Browser sich beim Umgang mit ausgehenden Anfragen verhalten soll. Dieser Filter stellt sicher, dass diese CORS-bezogenen HTTP-Header gesetzt sind, und ermöglicht die Handhabung von Interaktionen mit clientseitigen Webanwendungen, die auf verschiedenen Domains laufen. Die Annotationen @Component und @Order(Ordered.HIGHEST_PRECEDENCE) geben an, dass dieser Filter als Spring-Komponente behandelt und mit der höchsten Priorität in der Filterkette ausgeführt werden sollte.

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.io.IOException;


// Definition einer Spring-Komponente mit höchster Prioritätsreihenfolge, um sicherzustellen, dass dieser Filter zuerst ausgeführt wird
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter implements Filter {

    // Anfangskonfiguration des Filters. Laut der Super-Implementierung bleibt dies absichtlich leer.
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    // Die Kernmethode, in der das eigentliche Filtern implementiert ist
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // Casting von ServletRequest und ServletResponse Objekten zu ihren Http-Gegenstücken, um mit Http-Methoden und -Eigenschaften zu arbeiten
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // Setzen von CORS-Headern für das Response-Objekt, um Cross-Origin-Anfragen zu erlauben
        // "*" erlaubt alle Ursprünge, Methoden und Header. In der Produktion ist es ratsam, dies aus Sicherheitsgründen auf bekannte Ursprünge, Methoden und Header zu beschränken.
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");

        // Überprüfen, ob die eingehende Anfrage eine OPTIONS-Anfrage ist
        // Wenn ja, wird ein Status von OK zurückgegeben, da OPTIONS-Anfragen Vorab-Checks sind, die von Browsern gesendet werden
        if("OPTIONS".equalsIgnoreCase(request.getMethod())){
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else{
            // Wenn es keine OPTIONS-Anfrage ist, wird die Filterkette fortgesetzt
            filterChain.doFilter(request, response);
        }
    }

    // Aufräumen von Resourcen, die vom Filter verwendet wurden. Laut der Super-Implementierung bleibt dies absichtlich leer. Wird in unserem Fall nicht gebraucht.
    // @Override
    // public void destroy() {
    //     Filter.super.destroy();
    // }
}
