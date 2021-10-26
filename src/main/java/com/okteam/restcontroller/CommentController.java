package com.okteam.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.okteam.dao.CommentRepository;
import com.okteam.entity.Comments;

@RestController
@RequestMapping("/api/v2")
public class CommentController {

	@Autowired
	CommentRepository CmtRep;
	
	@GetMapping()
	public ResponseEntity<List<Comments>> rest(){
		return new ResponseEntity<List<Comments>>(CmtRep.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("{idcmt}")
	public ResponseEntity<Comments> getOne(@PathVariable("idcmt") Integer idcmt){
		if(!CmtRep.existsById(idcmt)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(CmtRep.findById(idcmt).get());
	}

	@PostMapping("/comments")
	public ResponseEntity<Comments> post(@RequestBody Comments cmt){
		if(CmtRep.existsById(cmt.getIdcmt())) {
			return ResponseEntity.badRequest().build();
		}
		CmtRep.save(cmt);
		return ResponseEntity.ok(cmt);
	}

}
