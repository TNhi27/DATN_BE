package com.okteam.restcontroller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import com.okteam.dao.NccRepository;
import com.okteam.dao.ProductRepository;
import com.okteam.dto.NccResponseDTO;
import com.okteam.entity.Ncc;
import com.okteam.entity.Products;
import com.okteam.entity.Response;
import com.okteam.exception.NotFoundSomething;
import com.okteam.utils.RegisterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/ncc")
public class NccController {

    @Autowired
    NccRepository nccRepository;
    @Autowired
    ProductRepository productDAO;
    @Autowired
    RegisterService service;

    //
    @GetMapping("/get/{idncc}")
    public ResponseEntity<Ncc> get10Ncc(@PathVariable("idncc") String idncc) {
        Ncc n = nccRepository.findById(idncc).get();

        return new ResponseEntity<Ncc>(n, HttpStatus.OK);
    }

    @GetMapping("/top")
    public ResponseEntity<List<NccResponseDTO>> getNcc2() {
        List<Ncc> list = nccRepository.findAll();
        Collections.sort(list, new Comparator<Ncc>() {

            @Override
            public int compare(Ncc o1, Ncc o2) {
                if (o1.getFollowSell().size() <= o2.getFollowSell().size()) {
                    return 1;
                } else {
                    return -1;
                }
            }

        });

        List<NccResponseDTO> list_dto = new ArrayList<>();
        for (Ncc ncc : list) {
            NccResponseDTO dto = new NccResponseDTO();
            dto.createByEntity(ncc);
            list_dto.add(dto);
        }

        return new ResponseEntity<List<NccResponseDTO>>(list_dto.subList(0, 3), HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<Page<Products>> getProduct(@RequestParam String idncc,
            @RequestParam Optional<Integer> pageNumber, @RequestParam Optional<Integer> size) {
        Page<Products> page = productDAO.getProductWithNcc(idncc,
                PageRequest.of(pageNumber.orElse(0), size.orElse(100)));
        return new ResponseEntity<Page<Products>>(page, HttpStatus.OK);
    }

    @GetMapping("/get_ncc_by_product")
    public ResponseEntity<NccResponseDTO> getNccDTOByPro(@RequestParam("idpro") String id) {
        Ncc ncc = nccRepository.getNccByProduct(id);
        NccResponseDTO dto = new NccResponseDTO();
        dto.createByEntity(ncc);
        return new ResponseEntity<NccResponseDTO>(dto, HttpStatus.OK);

    }

    @GetMapping("/get_ncc_by_id")
    public ResponseEntity<NccResponseDTO> getNccDTOById(@RequestParam("id") Optional<String> id) {
        Ncc ncc = nccRepository.findById(id.orElse("")).orElseThrow(() -> new NotFoundSomething(":("));
        NccResponseDTO dto = new NccResponseDTO();
        dto.createByEntity(ncc);
        return new ResponseEntity<NccResponseDTO>(dto, HttpStatus.OK);

    }

    @PostMapping("/register")
    public Response<NccResponseDTO> register(@RequestBody Ncc ncc)
            throws UnsupportedEncodingException, MessagingException {
        String message = "OK";
        List<NccResponseDTO> list = new ArrayList<>();
        if (service.checkUsername(ncc.getUsername())) {
            message = "Username đã tồn tại!";
        } else {
            ncc = service.registerNcc(ncc);
            NccResponseDTO nccDTO = new NccResponseDTO();
            nccDTO.createByEntity(ncc);
            list.add(nccDTO);
        }
        return new Response<NccResponseDTO>(list, message);
    }

    @GetMapping("/list")
    public Response<Ncc> getNccs(){
    	return new Response<Ncc>(nccRepository.findAll(Sort.by(Direction.DESC,"createdate")), "OK");
    }
    
    @PostMapping("/add")
    public Response<Ncc> addNcc(@RequestBody Ncc ncc){
    	String message = "OK";
    	if(service.checkUsername(ncc.getUsername())) {
    		message = "Tài khoản đã tồn tại, vui lòng chọn tên khác!";
    	} else {
    		ncc.setFullname(ncc.getNccname());
    		nccRepository.save(ncc);
    	}
    	return new Response<Ncc>(nccRepository.findAll(Sort.by(Direction.DESC,"createdate")), message);
    }
    
    @DeleteMapping("/delete")
    public Response<Ncc> deleteNcc(@RequestBody Ncc ncc){
    	String message = "OK";
    	if(!service.isNcc(ncc.getUsername())) {
    		message = "Tài khoản không chính xác!";
    	} else {
    		ncc = nccRepository.findById(ncc.getUsername()).get();
    		if(ncc.getProducts().size() > 0) {
    			message = "Nhà cung cấp đã có sản phẩm!";
    		} else if(ncc.getOrders().size() > 0) {
    			message = "Nhà cung cấp đã có đơn hàng!";
    		} else if(ncc.getFollowSell().size() > 0) {
    			message = "Nhà cung cấp đang nằm trong danh sách follow!";
    		} else {
    			nccRepository.deleteById(ncc.getUsername());
    		}
    	}
    	return new Response<Ncc>(nccRepository.findAll(Sort.by(Direction.DESC,"createdate")), message);
    }
    
    @PutMapping("/update-trangthai")
    public Response<Ncc> update_trangthai(@RequestParam("username") String username){
    	String message = "OK";
    	if(!service.isNcc(username)) {
    		message = "Tài khoản không chính xác!";
    	} else {
    		Ncc ncc  = nccRepository.findById(username).get();
        	ncc.setActive(!ncc.isActive());
        	nccRepository.save(ncc);
    	}
    	return new Response<Ncc>(null, message);
    }
    
    @PutMapping("/update/{username}")
    public Response<Ncc> updateNcc(@PathVariable("username") String username, 
    		@RequestParam("thaotac") Integer thaotac, @RequestParam("value") String value){
    	String message = "OK";
    	Ncc ncc= nccRepository.findById(username).get();
    	switch (thaotac) {
		case 0:
			ncc.setPassword(value);
			break;
		case 1:
			ncc.setNccname(value);
    		ncc.setFullname(value);
			break;
		case 2:
			ncc.setNcclogo(value);
			break;
		case 3:
			ncc.setEmail(value);
			break;
		case 4:
			ncc.setSdt(value);
			break;
		case 5:
			ncc.setCity(value);
			break;
		case 6:
			ncc.setAddress(value);
			break;
		default:
			return new Response<Ncc>(null, "Thao tác không hợp lệ!");
		}
    	nccRepository.save(ncc);
    	return new Response<Ncc>(null, message);
    }
    
}
