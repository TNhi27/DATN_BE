package com.okteam.restcontroller;

import com.okteam.dto.InfoBanksRepository;
import com.okteam.entity.InfoBanks;
import com.okteam.exception.NotFoundSomething;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/infobank")
public class InfoBanksController {
    @Autowired
    InfoBanksRepository banksRepository;

    @GetMapping("/get/{username}")
    public ResponseEntity<InfoBanks> selectInfoBanks(@PathVariable("username") String id) {
        return new ResponseEntity<InfoBanks>(
                banksRepository.selectByUsername(id).orElseThrow(() -> new NotFoundSomething(":( KHONG TIM THAY INFO")),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<InfoBanks> selectInfoBankss(@RequestParam String name, @RequestParam String num,
            @RequestParam String bank) {
        var names = SecurityContextHolder.getContext().getAuthentication().getName();
        InfoBanks infoBanks = banksRepository.selectByUsername(names).orElse(new InfoBanks());

        infoBanks.setBankname(bank);
        infoBanks.setBanknumber(num);
        infoBanks.setIsctv(null);
        infoBanks.setName(name);
        infoBanks.setUsername(names);
        return new ResponseEntity<InfoBanks>(banksRepository.save(infoBanks), HttpStatus.OK);
    }
}