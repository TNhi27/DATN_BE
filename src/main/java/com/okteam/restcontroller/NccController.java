package com.okteam.restcontroller;

import com.okteam.dao.NccRepository;
import com.okteam.entity.Ncc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class NccController {

	@Autowired
	NccRepository nccRepository;
    @GetMapping("/ncc/{idncc}")
    public ResponseEntity<Ncc> getNcc(@PathVariable("idncc")Integer idncc){
        Ncc n = nccRepository.findById(idncc).get();
		System.out.println(n.getIdncc());
        return new ResponseEntity<Ncc>(n,HttpStatus.OK);
    }

}
