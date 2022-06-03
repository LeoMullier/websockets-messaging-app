package fr.utc.sr03.chat.controller;

import fr.utc.sr03.chat.dao.UserRepository;
import fr.utc.sr03.chat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//@RequestMapping(value = "/NewCustomer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
public class restController
{
    // Ajout des repository
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/test")
    public void afficherget()
    {
        System.out.println("(*) Requete entrante GET.");
    }



    @PostMapping("/test")
    public User afficher(@RequestBody User utilisateur)
    {
        System.out.println("(*) Requete entrante POST1.");

        List<User> utilisateursTrouves = userRepository.findByLoginAndMdpAndDesactive(utilisateur.getLogin(), utilisateur.getMdp(), 0);
        System.out.println("(*) test Requete entrante POST1.");
        if (utilisateursTrouves.isEmpty() == true || utilisateursTrouves.size() > 1 || utilisateursTrouves.get(0).getAdmin() == 0)
        {
            // Dans le cas d'un problème d'authentification de l'utilisateur
            System.out.println("(*) Authentification refusée pour l'utilisateur " + utilisateur.getLogin() + ".");

            // Retour de la méthode
            User user_temp = new User();
            return user_temp;

        } else {

            // Dans le cas d'une authentification valide de l'utilisateur
            System.out.println("(*) Authentification réussie pour l'utilisateur " + utilisateur.getLogin() + ".");
            User user_temp = utilisateursTrouves.get(0);
            return user_temp;
        }
    }


    @PostMapping(value = "/test2", consumes = MediaType.APPLICATION_JSON_VALUE)
    public User afficherPOST2(@RequestBody User utilisateur)
    {
        System.out.println("(*) Requete entrante****.");

        List<User> utilisateursTrouves = userRepository.findById(1);

        User user_temp = utilisateursTrouves.get(0);
        return user_temp;

    }
}










/*
    @GetMapping("/one/{login}")
    public TrombiIndividusDTO getIndividu(@PathVariable String login)
    {
        TrombiIndividus trombiIndividus = trombiService.getOne(login);

        ModelMapper modelMapper = ApiService.getModelMapper(Optional.empty());

        return user;
    }

    @GetMapping("/liste")
    @ApiOperation(value = "Trombi complet", notes = "Notes")
    private List<TrombiIndividusDTO> getAnnuaire()
    {
        List<TrombiIndividus> trombiIndividus = trombiService.getAnnuaire();

        ModelMapper modelMapper = ApiService.getModelMapper(Optional.empty());

        return modelMapper.map(trombiIndividus, new TypeToken<List<TrombiIndividusDTO>>() {}.getType());
    }

    @GetMapping("/gi")
    @ApiOperation(value = "Trombi complet", notes = "Notes")
    private List<TrombiIndividusDTO> getGI(@RequestParam Optional<String> name, @RequestParam Optional<String> firstname, @RequestParam Optional<String> photo)
    {
        List<TrombiIndividus> trombiIndividus = trombiService.getGI();


        if(photo.isPresent()) {
            if (photo.get().equals("N")) {
                for (int i = 0; i < trombiIndividus.size(); i++) {
                    trombiIndividus.get(i).setPhoto(null);
                }
            }
        }

        if(name.isPresent()) {
            trombiIndividus = trombiIndividus.stream().filter(p -> p.getNomAz().contains(name.get())).collect(Collectors.toList());
        }

        if(firstname.isPresent()) {
            trombiIndividus = trombiIndividus.stream().filter(p -> p.getPrenomAz().contains(firstname.get())).collect(Collectors.toList());
        }

        ModelMapper modelMapper = ApiService.getModelMapper(Optional.empty());

        return modelMapper.map(trombiIndividus, new TypeToken<List<TrombiIndividusDTO>>() {}.getType());
    }

    @GetMapping("/hds")
    @ApiOperation(value = "Trombi complet", notes = "Notes")
    private List<TrombiIndividusDTO> getHDS()
    {
        List<TrombiIndividus> trombiIndividus = trombiService.getHDS();

        ModelMapper modelMapper = ApiService.getModelMapper(Optional.empty());

        return modelMapper.map(trombiIndividus, new TypeToken<List<TrombiIndividusDTO>>() {}.getType());
    }

    @GetMapping("/lmac")
    @ApiOperation(value = "Trombi complet", notes = "Notes")
    private List<TrombiIndividusDTO> getLMAC()
    {
        List<TrombiIndividus> trombiIndividus = trombiService.getLMAC();

        ModelMapper modelMapper = ApiService.getModelMapper(Optional.empty());

        return modelMapper.map(trombiIndividus, new TypeToken<List<TrombiIndividusDTO>>() {}.getType());
    }




}
*/
