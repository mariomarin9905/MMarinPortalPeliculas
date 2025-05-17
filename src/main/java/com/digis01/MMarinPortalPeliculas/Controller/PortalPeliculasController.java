
package com.digis01.MMarinPortalPeliculas.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/AficionPelis")
public class PortalPeliculasController {
    
    @GetMapping()
    public String Login(){
        System.out.println("s");
        return "Login";
    }
    
}
