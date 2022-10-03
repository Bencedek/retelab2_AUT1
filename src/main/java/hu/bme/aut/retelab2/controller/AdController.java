package hu.bme.aut.retelab2.controller;

import hu.bme.aut.retelab2.domain.Ad;
import hu.bme.aut.retelab2.repository.AdRepository;
import hu.bme.aut.retelab2.util.SecretGenerator;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ads")
public class AdController {

    @Autowired
    private AdRepository adRepository;

    @GetMapping
    public List<Ad> getAll(
            @RequestParam(required = false, defaultValue = "0") int min,
            @RequestParam(required = false, defaultValue = "10000000") int max) {
        List<Ad> ads = adRepository.findByPrice(min, max);
        for (Ad ad : ads) {
            ad.setSecretCode(null);
        }
        return ads;
    }

    @GetMapping("{tag}")
    public ResponseEntity<List<Ad>> getByTag(@PathVariable String tag) {
        List<Ad> adsWithTag = adRepository.findAll();
        for (Ad ad : adsWithTag) {
            if(!ad.getTags().contains(tag)) adsWithTag.remove(ad);
        }
        if (adsWithTag.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(adsWithTag);
    }

    @PostMapping
    public Ad create(@RequestBody Ad ad) {
        ad.setId(null);
        ad.setDateCreated();
        ad.setSecretCode(SecretGenerator.generate());
        return adRepository.save(ad);
    }

    @PutMapping
    public ResponseEntity<Ad> update(@RequestBody Ad ad) {
        Ad a;
        try {
            a = adRepository.modify(ad);
            return ResponseEntity.ok(a);
        } catch (NotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    /*@DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        Ad ad = adRepository.findById(id);
        if (ad == null)
            return ResponseEntity.notFound().build();
        else {
            adRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }*/
    }
