package com.okteam.restcontroller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.okteam.dao.CtvRepository;
import com.okteam.dao.FollowSellRepository;
import com.okteam.dao.NccRepository;
import com.okteam.dao.RegiProductRepository;
import com.okteam.entity.Ctv;
import com.okteam.entity.FollowSell;
import com.okteam.entity.Ncc;
import com.okteam.entity.RegiProducts;
import com.okteam.exception.NotFoundSomething;
import com.okteam.exception.UserAlreadyExists;
import com.okteam.exception.UsersException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/follow")
public class FollowController {
    @Autowired
    FollowSellRepository fdao;
    @Autowired
    NccRepository ndao;
    @Autowired
    CtvRepository cdao;
    @Autowired
    RegiProductRepository regiProductRepository;

    @GetMapping("/ctv/{ctv}")
    public ResponseEntity<Page<FollowSell>> getFollowByCtv(@PathVariable("ctv") String ctv,
            @RequestParam Optional<Integer> p, @RequestParam Optional<Integer> size) {
        Sort s = Sort.by("date").descending();
        Page page = fdao.getNccFollow(ctv, PageRequest.of(p.orElse(0), size.orElse(100), s));

        return new ResponseEntity<Page<FollowSell>>(page, HttpStatus.OK);
    }

    @GetMapping("/ncc/{ncc}")
    public ResponseEntity<Page<RegiProducts>> getFollowByNcc(@PathVariable("ncc") String ncc,
            @RequestParam Optional<String> idpro, @RequestParam Optional<String> namectv,
            @RequestParam Optional<Integer> p, @RequestParam Optional<Integer> size) {

        Page<RegiProducts> page = regiProductRepository.getCtvRegi(ncc, idpro.orElse("%%"),
                "%" + namectv.orElse("") + "%",
                PageRequest.of(0, 100));
        return new ResponseEntity<Page<RegiProducts>>(page, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FollowSell> follow(@RequestParam Optional<String> idncc) {
        var name = SecurityContextHolder.getContext().getAuthentication().getName();
        Ctv ctv = cdao.findById(name).get();
        Ncc ncc = ndao.findById(idncc.orElse("")).orElseThrow(() -> new NotFoundSomething(":("));

        List<FollowSell> all = ctv.getFollowSell();
        boolean ck = true;
        for (var fl : all) {
            if (fl.getFl_ncc().getUsername().equals(ncc.getUsername())) {
                ck = false;
                break;
            }
        }
        FollowSell flo = new FollowSell();
        if (ck) {

            flo.setDate(new Date());
            flo.setFl_ncc(ncc);
            flo.setFl_ctv(ctv);

        }

        return new ResponseEntity<FollowSell>(fdao.save(flo), HttpStatus.OK);
    }

    @DeleteMapping("/unfollow/{ncc}")
    public ResponseEntity<String> unfollow(@PathVariable("ncc") String ncc, @RequestParam String ctv) {
        var fl = fdao.getFollow(ctv, ncc);
        fdao.delete(fl);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        fdao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}