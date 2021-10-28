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
import com.okteam.dao.CtvRepository;
import com.okteam.dao.ProductRepository;
import com.okteam.dto.CommentsDTO;
import com.okteam.entity.Comments;
import com.okteam.entity.Ctv;
import com.okteam.entity.Products;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

	@Autowired
	CommentRepository CmtRep;
	@Autowired
	CtvRepository CtvRep;
	@Autowired
	ProductRepository ProRep;
	
	@GetMapping()
	public ResponseEntity<List<Comments>> getAllComments(){
		
		return new ResponseEntity<List<Comments>>(CmtRep.findAll(), HttpStatus.OK);
	}

	@GetMapping("{idcmt}")
	public ResponseEntity<Comments> getCommentById(@PathVariable("idcmt") Integer idcmt){
		Comments cmt = CmtRep.findById(idcmt).get();
		ResponseEntity.notFound().build();
		
		return new ResponseEntity<Comments>(cmt, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Comments> postComment(@RequestBody CommentsDTO commentsDto) {
			Ctv ctv = CtvRep.findById(commentsDto.getUsername()).get();
			Comments comment = new Comments();
			comment.setContent(commentsDto.getContent());
			comment.setCreatedate(commentsDto.getCreatedate());
			comment.setStar(commentsDto.getStar());
			comment.setCtv_cmt(ctv);
			Products products = ProRep.findById(commentsDto.getProducts()).get();
			comment.setProducts(products);
		return new ResponseEntity<Comments>(CmtRep.save(comment), HttpStatus.CREATED);
	}

}
