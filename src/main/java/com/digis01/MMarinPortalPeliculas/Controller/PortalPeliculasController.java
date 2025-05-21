package com.digis01.MMarinPortalPeliculas.Controller;

import com.digis01.MMarinPortalPeliculas.Model.Pelicula;
import com.digis01.MMarinPortalPeliculas.Model.Reaccion;
import com.digis01.MMarinPortalPeliculas.Model.ResultMedia;
import com.digis01.MMarinPortalPeliculas.Model.Usuario;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/AficionPelis")
public class PortalPeliculasController {

    private String APIKEY = "?api_key=05452f9f66341a2b27f7389fa8ba94cb";
    private String BASEURL = "https://api.themoviedb.org/3";
    private String APITOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwNTQ1MmY5ZjY2MzQxYTJiMjdmNzM4OWZhOGJhOTRjYiIsIm5iZiI6MTc0NzMzNDA2Ny45NDEsInN1YiI6IjY4MjYzM2IzMGUwMGNmMjZmNDZlZjljMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Hu-KqluKL_NjVAekNW9SguYxLCe6S0vtyN_crhEQXaQ";
    private RestTemplate restTemplate;

    @GetMapping()
    public String Login(Model model) {
        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);
        return "Login";
    }

    @PostMapping("Login")
    public String CreaSession(@ModelAttribute Usuario usuario, Model model, HttpSession session) {
        try {
            this.restTemplate = new RestTemplate();
            if ((!usuario.getUsername().isEmpty()) && (!usuario.getPassword().isEmpty())) {

                ResponseEntity<Map<String, Object>> responseRequestToken = this.restTemplate.exchange(
                        this.BASEURL + "/authentication/token/new" + this.APIKEY,
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        new ParameterizedTypeReference<Map<String, Object>>() {
                });

                boolean success = (boolean) responseRequestToken.getBody().get("success");

                if (success) {
                    usuario.setRequest_token((String) responseRequestToken.getBody().get("request_token"));
                    ResponseEntity<Usuario> responseLogin = this.restTemplate.exchange(this.BASEURL + "/authentication/token/validate_with_login" + this.APIKEY,
                            HttpMethod.POST,
                            new HttpEntity(usuario),
                            new ParameterizedTypeReference<Usuario>() {
                    });
                    Usuario usuarioLogin = responseLogin.getBody();
                    if (usuarioLogin.getSuccess()) {
                        ResponseEntity<Usuario> responseSession = this.restTemplate.exchange(
                                this.BASEURL + "/authentication/session/new" + this.APIKEY,
                                HttpMethod.POST,
                                new HttpEntity(usuarioLogin),
                                new ParameterizedTypeReference<Usuario>() {
                        });
                        Usuario usuarioSession = responseSession.getBody();
                        if (usuarioSession.getSuccess()) {
                            String sessionId = usuarioSession.getSession_id();
                            session.setAttribute("session_id",sessionId);                          
                            return "redirect:/AficionPelis/Popular";
                        } else {
                            return "";
                        }

                    } else {
                        return "";
                    }

                } else {
                    return "";
                }

            } else {
                model.addAttribute("error", "Error datos vacios");
                return "Login";
            }

        } catch (HttpStatusCodeException e) {
            if (HttpStatus.UNAUTHORIZED == e.getStatusCode()) {
                model.addAttribute("error", "Username o Contraseña incorrectos");
                return "Login";
            } else {
                return "";
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return "";

        }

    }

    @GetMapping("/Popular")
    public String Popular(Model model, HttpSession session) {
        try {           
            if (session.getAttribute("session_id")== null ) {
                return "redirect:/AficionPelis";
            }
            this.restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(this.APITOKEN);
            ResponseEntity<ResultMedia<Pelicula>> responsePeliculas =  this.restTemplate.exchange(
                    this.BASEURL+"/movie/popular?region=es-ES&language=es", 
                    HttpMethod.GET, 
                    new HttpEntity(headers), 
                    new ParameterizedTypeReference<ResultMedia<Pelicula>>(){});
            ResultMedia<Pelicula> resultPeliculas = responsePeliculas.getBody();
            List<Pelicula> peliculas = resultPeliculas.results;
            for (Pelicula pelicula : peliculas) {
                ResponseEntity<Reaccion> resultEstatus = this.restTemplate.exchange(
                        this.BASEURL + "/movie/"+pelicula.getId()+"/account_states?session_id="+session.getAttribute("session_id").toString(),
                        HttpMethod.GET,
                        new HttpEntity(headers),
                        new ParameterizedTypeReference<Reaccion>(){});
                pelicula.reaccion = resultEstatus.getBody();   
                
            }
            model.addAttribute("peliculas", peliculas);
            model.addAttribute("session_id", session.getAttribute("session_id"));
            return "Popular";
        } catch (Exception e) {
            return "";
        }
        
        
    }
}
