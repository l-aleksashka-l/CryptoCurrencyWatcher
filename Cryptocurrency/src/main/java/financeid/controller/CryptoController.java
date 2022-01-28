package financeid.controller;

import financeid.model.Crypto;
import financeid.repo.CryptoRepository;
import financeid.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class CryptoController {

    private CryptoService cryptoService;

    @Autowired
    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping
    public String Nothing(){return "main";}

    @GetMapping("/list")
    public String findAll(Model model){
        List<Crypto> cryptos = cryptoService.findAll();
        model.addAttribute("cryptos", cryptos);
        return "list";
    }

    @GetMapping("/crypto-create")
    public String createCryptoForm(Crypto crypto){
        return "crypto-create";
    }

    @PostMapping
    public String createCrypto(Crypto crypto){
        cryptoService.saveCrypto(crypto);
        return "redirect:/list";
    }

}
