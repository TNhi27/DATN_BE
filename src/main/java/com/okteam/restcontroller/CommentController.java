package com.okteam.restcontroller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.okteam.dao.CommentRepository;
import com.okteam.dao.CtvRepository;
import com.okteam.dao.ProductRepository;
import com.okteam.dto.CommentsDTO;
import com.okteam.dto.CommentsRespDto;
import com.okteam.entity.Comments;
import com.okteam.entity.Ctv;
import com.okteam.entity.Products;
import com.okteam.entity.Response;
import com.okteam.exception.UserAlreadyExists;
import com.okteam.exception.UserCommentExists;
import com.okteam.utils.DtoUtils;

@RestController

@RequestMapping("/api/v1/comments")
@CrossOrigin("*")

public class CommentController {

	@Autowired
	CommentRepository CmtRep;

	@Autowired
	CtvRepository CtvRep;
	@Autowired
	ProductRepository ProRep;
	@Autowired
	DtoUtils dtoUtils;

	@GetMapping()
	public ResponseEntity<List<Comments>> getAllComments() {

		return new ResponseEntity<List<Comments>>(CmtRep.findAll(), HttpStatus.OK);
	}

	@GetMapping("{idcmt}")
	public ResponseEntity<Comments> getCommentById(@PathVariable("idcmt") Integer idcmt) {
		Comments cmt = CmtRep.findById(idcmt).get();
		ResponseEntity.notFound().build();

		return new ResponseEntity<Comments>(cmt, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Comments> postComment(@RequestBody CommentsDTO commentsDto) {
		Ctv ctv = CtvRep.findById(commentsDto.getUsername()).get();
		Products products = ProRep.findById(commentsDto.getProducts()).get();

		List<Comments> list = CmtRep.findAll();

		for (var cmt : list) {
			if (cmt.getIdorder() == commentsDto.getIdorder()) {
				if (cmt.getProducts().getIdpro().equals(commentsDto.getProducts())
						&& cmt.getCtv_cmt().getUsername().equals(commentsDto.getUsername())) {
					throw new UserCommentExists();
				}

			}
		}

		Comments comment = new Comments();
		comment.setContent(commentsDto.getContent());
		comment.setCreatedate(new Date());
		comment.setStar(commentsDto.getStar());
		comment.setCtv_cmt(ctv);
		comment.setIdorder(commentsDto.getIdorder());
		comment.setProducts(products);
		return new ResponseEntity<Comments>(CmtRep.save(comment), HttpStatus.CREATED);

	}

	@GetMapping("/list")
	public Response<CommentsRespDto> getList(@RequestParam(value = "idpro", required = false) String idpro){
		List<Comments> list = CmtRep.findAll(Sort.by(Sort.Direction.DESC, "idcmt"));
		if(idpro !=null) {
			if(!ProRep.existsById(idpro)) {
				return new Response<CommentsRespDto>(null, null, "Không lấy được dữ liệu");
			} else {
				list = CmtRep.getCommentByIdpro(idpro);
			}
		}
		return new Response<CommentsRespDto>(dtoUtils.mapCommentsToDto(list), null, "OK");
	}
	
	@DeleteMapping("/delete/{idcmt}")
	public Response<CommentsRespDto> deleteCmts(@PathVariable("idcmt") Integer idcmt, @RequestParam(value = "idpro", required = false) String idpro){
		String message = "OK";
		List<Comments> list = new ArrayList<>();
		if(!CmtRep.existsById(idcmt)) {
			message = "Không tìm thấy bình luận!";
		} else {
			CmtRep.deleteById(idcmt);
		}
		if(idpro == null) {
			list = CmtRep.findAll(Sort.by(Sort.Direction.DESC, "idcmt"));
		} else {
			list = CmtRep.getCommentByIdpro(idpro);
		}
		return new Response<CommentsRespDto>(dtoUtils.mapCommentsToDto(list), null, message);
	}
	
}
