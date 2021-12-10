package com.okteam.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import com.okteam.dao.AdminRepository;
import com.okteam.dao.PostRepository;
import com.okteam.dto.PostDTO;
import com.okteam.dto.PostRespDTO;
import com.okteam.entity.Post;
import com.okteam.entity.Response;
import com.okteam.utils.DtoUtils;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/post")
public class PostController {
	@Autowired
	PostRepository poRepo;
	@Autowired
	AdminRepository adrepo;
	@Autowired
	DtoUtils dtoUtils;

	@GetMapping
	public ResponseEntity<Page<Post>> get() {
		Page<Post> rs = poRepo.findAll(PageRequest.of(0, 8, Sort.by("createdate")));
		return new ResponseEntity<Page<Post>>(rs, HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Post> getid(@PathVariable("id") int id) {
		Post rs = poRepo.findById(id).get();
		return new ResponseEntity<Post>(rs, HttpStatus.OK);
	}

	@GetMapping("/search")
	public ResponseEntity<List<Post>> search(@RequestParam Optional<String> title) {
		Page<Post> rs = poRepo.selectByTitle("%" + title.orElse("") + "%", PageRequest.of(0, 20));
		return new ResponseEntity<List<Post>>(rs.getContent(), HttpStatus.OK);
	}

	@GetMapping("/list")
	public Response<PostRespDTO> getList() {
		return new Response<PostRespDTO>(dtoUtils.mapPostToDto(poRepo.findAll()), null, "OK");
	}

	@PostMapping("/add")
	public Response<PostRespDTO> add(@RequestBody PostDTO po) {
		String messgae = "OK";
		if (!adrepo.existsById(po.getUsername())) {
			messgae = "Không xác định được quản trị viên!";
		} else {
			Post p = new Post().dtoReturnEntity(po);
			p.setAcc_post(adrepo.findById(po.getUsername()).get());
			poRepo.save(p);
		}
		return new Response<PostRespDTO>(dtoUtils.mapPostToDto(poRepo.findAll()), null, messgae);
	}

	@DeleteMapping("/delete")
	public Response<PostRespDTO> delete(@RequestBody PostDTO po) {
		String message = "OK";
		if (!poRepo.existsById(po.getIdpost())) {
			message = "Không tìm thấy bài viết!";
		} else {
			poRepo.deleteById(po.getIdpost());
		}
		return new Response<PostRespDTO>(dtoUtils.mapPostToDto(poRepo.findAll()), null, message);
	}

	@PutMapping("/update/{idpost}")
	public Response<PostRespDTO> update(@PathVariable("idpost") Integer idpost,
			@RequestParam("thaotac") Integer thaotac, @RequestParam("value") String value) {
		if (!poRepo.existsById(idpost)) {
			return new Response<PostRespDTO>(null, null, "Không tìm thấy bài viết!");
		}
		Post post = poRepo.findById(idpost).get();
		if(thaotac == 0 && value.isEmpty()) {
			return new Response<PostRespDTO>(null, post, "Giá trị không hợp lệ");
		}
		if(thaotac == 1 && value.replace("<br />", "").isEmpty()) {
			return new Response<PostRespDTO>(null, post, "Giá trị không hợp lệ");
		}
		switch (thaotac) {
		case 0:
			post.setTitle(value);
			break;
		case 1:
			post.setContent(value);
			break;
		case 2:
			post.setImage(value);
			break;
		default:
			return new Response<PostRespDTO>(null, null, "Thao tác không hợp lệ!");
		}
		poRepo.save(post);
		return new Response<PostRespDTO>(null, post, "OK");
	}
}
