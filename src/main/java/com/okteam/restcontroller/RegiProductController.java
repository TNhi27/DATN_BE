package com.okteam.restcontroller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.okteam.dao.CtvRepository;
import com.okteam.dao.FollowSellRepository;
import com.okteam.dao.ProductRepository;
import com.okteam.dao.RegiProductRepository;
import com.okteam.dto.CommentsDTO;
import com.okteam.dto.RegiProductsDTO;
import com.okteam.dto.RegiProductsRespDto;
import com.okteam.entity.Comments;
import com.okteam.entity.Ctv;
import com.okteam.entity.FollowSell;
import com.okteam.entity.Ncc;
import com.okteam.entity.Products;
import com.okteam.entity.RegiProducts;
import com.okteam.entity.Response;
import com.okteam.utils.DtoUtils;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/regi_products")
public class RegiProductController {
	@Autowired
	RegiProductRepository RegiPro;

	@Autowired
	CtvRepository CtvRep;
	
	@Autowired
	ProductRepository ProRep;

	@Autowired
	FollowSellRepository fdao;
	
	@Autowired
	RegiProductRepository regiRepo;
	
	@Autowired
	DtoUtils dtoUtils;

	@GetMapping
	public ResponseEntity<List<RegiProducts>> getAllRegi() {
		return new ResponseEntity<List<RegiProducts>>(RegiPro.findAll(), HttpStatus.OK);
	}

	@GetMapping("/ctv/{username}")
	public ResponseEntity<Page<RegiProducts>> geriproductwithncc(@PathVariable("username") String idctv,
			@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size,
			@RequestParam Optional<String> category, @RequestParam Optional<String> name) {

		Sort sort = Sort.by("regidate").descending();
		Page p = RegiPro.getRegiProductsWithCtv(idctv, category.orElse("%%"), "%" + name.orElse("%%") + "%",
				PageRequest.of(page.orElse(0), size.orElse(100), sort));
		return new ResponseEntity<Page<RegiProducts>>(p, HttpStatus.OK);
	}

	@GetMapping("/getone/{idregi}")
	public ResponseEntity<RegiProducts> getRegiById(@PathVariable("idregi") Integer idregi) {
		RegiProducts rige = RegiPro.findById(idregi).get();
		ResponseEntity.notFound().build();

		return new ResponseEntity<RegiProducts>(rige, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<RegiProducts> postRegi(@RequestBody RegiProductsDTO regiproDto) {

		// lay username tu chuoi jwt client gui len
		var name = SecurityContextHolder.getContext().getAuthentication().getName();

		Ctv ctv = CtvRep.findById(name).get();
		RegiProducts regiproduct = new RegiProducts();
		regiproduct.setRegidate(new Date());
		regiproduct.setCtv(ctv);
		Products products = ProRep.findById(regiproDto.getIdpro()).get();
		regiproduct.setProducts(products);
		regiproduct.setPrice(regiproDto.getPrice());

		List<FollowSell> all = ctv.getFollowSell();
		boolean ck = true;
		for (var fl : all) {
			if (fl.getFl_ncc().getUsername().equals(products.getNcc().getUsername())) {
				ck = false;
				break;
			}
		}

		if (ck) {
			FollowSell flo = new FollowSell();
			flo.setDate(new Date());
			flo.setFl_ncc(products.getNcc());
			flo.setFl_ctv(ctv);
			fdao.save(flo);
		}

		return new ResponseEntity<RegiProducts>(RegiPro.save(regiproduct), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> detele(@PathVariable int id) {
		RegiPro.deleteById(id);
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}

	@GetMapping("/get_ncc/{ctv}")
	public ResponseEntity<Page<Ncc>> getNcc(@PathVariable("ctv") String ctv) {
		Page<Ncc> page = RegiPro.getAllNccOfCtv(ctv, PageRequest.of(0, 100));
		return new ResponseEntity<Page<Ncc>>(page, HttpStatus.OK);
	}

	@GetMapping("/get_product/{ncc}/{ctv}")
	public ResponseEntity<Page<RegiProducts>> getProducts(@PathVariable("ncc") Optional<String> ncc,
			@PathVariable("ctv") String ctv) {
		Page<RegiProducts> page = RegiPro.getProductsOfNcc(ncc.orElse("%%"), ctv, PageRequest.of(0, 100));
		return new ResponseEntity<Page<RegiProducts>>(page, HttpStatus.OK);
	}

	@GetMapping("/list")
	public Response<RegiProductsRespDto> listRegi(@RequestParam(value = "username", required = false) String username){
		String message = "Không lấy được dữ liệu";
		List<RegiProducts> list = RegiPro.findAll(Sort.by(Sort.Direction.DESC, "regidate"));
		if(username == null) {
			message = "OK";
		} else {
			if(!CtvRep.existsById(username)) {
				return new Response<RegiProductsRespDto>(null, null, message);
			} else {
				message = "OK";
				list = RegiPro.findbyIdCtv(username);
			}
		}
		return new Response<RegiProductsRespDto>(dtoUtils.mapRegiProductsToDto(list), null, message);
	}
	
	@PostMapping("/add")
	public Response<RegiProductsRespDto> addRegi(@RequestBody RegiProductsDTO regi,
			@RequestParam(value = "idctv", required = false) String idctv){
		String message = "OK";
		List<RegiProducts> list = new ArrayList<>();
		if(regiRepo.getByCtvSP(regi.getUsername(), regi.getIdpro()) != null) {
			message = "Sản phẩm đã được cộng tác viên đăng ký rồi!";
		} else {
			RegiProducts r = new RegiProducts();
			r.setPrice(regi.getPrice());
			r.setCtv(CtvRep.findById(regi.getUsername()).get());
			r.setProducts(ProRep.findById(regi.getIdpro()).get());
			regiRepo.save(r);
		}
		System.out.println(idctv);
		if(idctv == null) {
			list = RegiPro.findAll(Sort.by(Sort.Direction.DESC, "regidate"));
		} else {
			list = regiRepo.findbyIdCtv(idctv);
		}
		return new Response<RegiProductsRespDto>(dtoUtils.mapRegiProductsToDto(list), null, message);
	}
	
	@DeleteMapping("/delete")
	public Response<RegiProductsRespDto> deleteRegi(@RequestParam("idregi") Integer idregi,
			@RequestParam(value = "idctv", required = false) String idctv){
		String message = "OK";
		List<RegiProducts> list = new ArrayList<>();
		if(!regiRepo.existsById(idregi)) {
			message = "Không tìm thấy đăng ký!";
		} else {
			regiRepo.deleteById(idregi);
		}
		if(idctv == null) {
			list = RegiPro.findAll(Sort.by(Sort.Direction.DESC, "regidate"));
		} else {
			list = regiRepo.findbyIdCtv(idctv);
		}
		return new Response<RegiProductsRespDto>(dtoUtils.mapRegiProductsToDto(list), null, message);
	}
	
	@PutMapping("/update_gia/{idregi}")
	public Response<RegiProductsRespDto> update_gia(@PathVariable("idregi") Integer idregi, @RequestParam("value") Integer value){
		String message = "OK";
		if(!regiRepo.existsById(idregi)){
			message = "Không tìm thấy đăng ký!";
		} else {
			RegiProducts regi = regiRepo.findById(idregi).get();
			regi.setPrice(value);
			regiRepo.save(regi);
		}
		return new Response<RegiProductsRespDto>(null, null, message);
	}
	
}
