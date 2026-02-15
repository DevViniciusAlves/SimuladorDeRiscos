package com.ploydev.SimuladorDeRiscos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // Redireciona para o index estático para evitar loop de forward
        return "redirect:/index.html";
    }

    // Removido o mapeamento de /index.html para deixar o recurso estático ser servido diretamente
}
