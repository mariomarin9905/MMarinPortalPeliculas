package com.digis01.MMarinPortalPeliculas.Controller;


import com.digis01.MMarinPortalPeliculas.Model.Auth;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/AficionPelis")
public class PortalPeliculasController {

    private String ApiKey = "05452f9f66341a2b27f7389fa8ba94cb";
    private String BaseUrl = "https://api.themoviedb.org/3/";
    private RestTemplate restTemplate;
    
    @GetMapping()
    public String Login(Model model) {
        Auth auth = new Auth();
        
        model.addAttribute("auth", auth);
        return "Login";
    }
    @PostMapping("Login")
    public String CreaSession(@ModelAttribute Auth auth, Model model){
        try {
            if((!auth.getUserName().isEmpty())&&(!auth.getPassword().isEmpty()) ){
                
                return "";
            }else{
                model.addAttribute("error", "Error datos vacios");
                return "Login";
            }
            
        } catch (Exception e) {
        }        
            return "";
    }
}
