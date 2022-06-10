package fr.utc.sr03.chat.controller;

import fr.utc.sr03.chat.dao.UserRepository;
import fr.utc.sr03.chat.model.User;
import fr.utc.sr03.chat.model.UserTmp;
import fr.utc.sr03.chat.model.ValidationAuthentification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.stream.Collectors;
//import java.util.Math;
import javax.servlet.http.HttpServletRequest;

//@RequestMapping(value = "/NewCustomer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class restController
{
    // Ajout des repository
    @Autowired
    private UserRepository userRepository;


    // Gestion des utilisateurs authentifiés
    public Dictionary<String, String> utilisateursAuthentifies = new Hashtable<>();
    //                ip      token

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
    public ValidationAuthentification afficherPOST2(@RequestBody UserTmp utilisateurTmp, HttpServletRequest request)
    {
        String adresseIpClient = request.getRemoteAddr();
        System.out.println("(blaip) "+adresseIpClient);

        System.out.println("(!) userLOGIN - NULL - Requête POST entrante pour authentification de l'utilisateur.");
        String loginTmp = utilisateurTmp.getLogin();
        String mdpTmp = utilisateurTmp.getMdp();
        System.out.println("(!) userLOGIN - NULL - login demandé : " + loginTmp);
        System.out.println("(!) userLOGIN - NULL - mdp demandé : " + mdpTmp);
        List<User> utilisateursTrouves = userRepository.findByLoginAndMdpAndDesactive(loginTmp, mdpTmp, 0);

        if (utilisateursTrouves.isEmpty() == true || utilisateursTrouves.size() > 1 || utilisateursTrouves.get(0).getAdmin() == 0)
        {
            // Dans le cas d'un problème d'authentification de l'utilisateur
            System.out.println("(*) Authentification refusée pour l'utilisateur ");

            // Retour de la méthode
            return null;
        } else {

            // Dans le cas d'une authentification valide de l'utilisateur

            String alphabetsInUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String alphabetsInLowerCase = "abcdefghijklmnopqrstuvwxyz";
            String numbers = "0123456789";
            // create a super set of all characters
            String allCharacters = alphabetsInLowerCase + alphabetsInUpperCase + numbers;
            // initialize a string to hold result
            StringBuffer randomString = new StringBuffer();
            // loop for 10 times
            for (int i = 0; i < 64; i++) {
                // generate a random number between 0 and length of all characters
                int randomIndex = (int)(Math.random() * allCharacters.length());
                // retrieve character at index and add it to result
                randomString.append(allCharacters.charAt(randomIndex));
            }
            String tokenClient = randomString.toString();

            utilisateursAuthentifies.put(adresseIpClient, tokenClient);

            ValidationAuthentification retourAuthentification = new ValidationAuthentification(utilisateursTrouves.get(0).getId(), tokenClient);
            System.out.println("(*) Authentification réussie pour l'utilisateur .");

            return retourAuthentification;
        }

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
