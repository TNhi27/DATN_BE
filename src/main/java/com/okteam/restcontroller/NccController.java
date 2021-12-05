package com.okteam.restcontroller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import com.okteam.dao.FollowSellRepository;
import com.okteam.dao.NccRepository;
import com.okteam.dao.ProductRepository;
import com.okteam.dto.NccDto;
import com.okteam.dao.CtvAndListOrder;
import com.okteam.dao.CtvRepository;
import com.okteam.dto.NccRequestDTO;
import com.okteam.dto.NccResponseDTO;
import com.okteam.entity.Ctv;
import com.okteam.entity.Ncc;
import com.okteam.entity.Orders;
import com.okteam.entity.Products;
import com.okteam.entity.Response;
import com.okteam.exception.NotFoundSomething;
import com.okteam.utils.DtoUtils;
import com.okteam.exception.UsersException;
import com.okteam.utils.RegisterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/ncc")
public class NccController {

    @Autowired
    NccRepository nccRepository;
    @Autowired
    ProductRepository productDAO;
    @Autowired
    FollowSellRepository flRepo;
    @Autowired
    RegisterService service;
    @Autowired
    DtoUtils dtoUtils;

    @Autowired
    CtvRepository ctvRepository;

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

    @GetMapping("/productsv2")
    public ResponseEntity<Page<Products>> getProductv2(@RequestParam String idncc,
            @RequestParam Optional<Integer> pageNumber, @RequestParam Optional<Integer> size,
            @RequestParam Optional<String> category, @RequestParam Optional<String> name) {

        Sort s = Sort.by("createdate").descending();
        Page<Products> page = productDAO.getProductWithNccV2(idncc, category.orElse("%%"), name.orElse(""),
                PageRequest.of(pageNumber.orElse(0), size.orElse(100), s));
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
    
    @GetMapping("/list")
    public Response<NccResponseDTO> getNccs(){
    	return new Response<NccResponseDTO>(dtoUtils.mapNccToDto(nccRepository.findAll(Sort.by(Direction.DESC,"createdate"))), null, "OK");
    }
    
    @GetMapping("/check-id/{username}")
	public Boolean checkusernamectv(@PathVariable("username") String username) {
		return service.checkUsername(username);
	}
    
    @PostMapping("/add")
    public Response<NccResponseDTO> addNcc(@RequestBody NccDto ncc){
    	String message = "OK";
    	if(service.checkUsername(ncc.getUsername())) {
    		message = "Tài khoản đã tồn tại, vui lòng chọn tên khác!";
    	} else {
    		nccRepository.save(new Ncc().dtoReturnEntity(ncc));
    	}
    	return new Response<NccResponseDTO>(dtoUtils.mapNccToDto(nccRepository.findAll(Sort.by(Direction.DESC,"createdate"))), null, message);
    }
    
    @DeleteMapping("/delete")
    public Response<NccResponseDTO> deleteNcc(@RequestBody NccDto ncc){
    	String message = "OK";
    	if(!service.isNcc(ncc.getUsername())) {
    		message = "Tài khoản nhà cung cấp không chính xác!";
    	} else {
    		Ncc n = nccRepository.findById(ncc.getUsername()).get();
    		if(n.getProducts().size() > 0) {
    			message = "Nhà cung cấp đã có sản phẩm!";
    		} else if(n.getOrders().size() > 0) {
    			message = "Nhà cung cấp đã có đơn hàng!";
    		} else {
    			flRepo.deleteAll(n.getFollowSell());
    			nccRepository.delete(n);
    		}
    	}
    	return new Response<NccResponseDTO>(dtoUtils.mapNccToDto(nccRepository.findAll(Sort.by(Direction.DESC,"createdate"))), null, message);
    }
    
    @PutMapping("/update-trangthai")
    public Response<NccResponseDTO> update_trangthai(@RequestParam("username") String username){
    	String message = "OK";
    	if(!service.isNcc(username)) {
    		message = "Tài khoản nhà cung cấp không chính xác!";
    	} else {
    		Ncc ncc  = nccRepository.findById(username).get();
        	ncc.setActive(!ncc.isActive());
        	ncc.setVerify(null);
        	nccRepository.save(ncc);
    	}
    	return new Response<NccResponseDTO>(null, null, message);
    }
    
    @PutMapping("/update/{username}")
    public Response<NccResponseDTO> updateNcc(@PathVariable("username") String username, 
    		@RequestParam("thaotac") Integer thaotac, @RequestParam("value") String value){
    	if(!service.isNcc(username)) {
    		return new Response<NccResponseDTO>(null, null, "Tài khoản nhà cung cấp không chính xác!");
    	}
    	Ncc ncc= nccRepository.findById(username).get();
    	switch (thaotac) {
		case 0:
			ncc.setPassword(value);
			break;
		case 1:
			ncc.setNccname(value);
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
		case 7:
			ncc.setIdghn(value);
		case 8:
			ncc.setDescription(value);
			break;
		case 9:
			ncc.setFullname(value);
			break;
		default:
			return new Response<NccResponseDTO>(null, null, "Thao tác không hợp lệ!");
		}
    	nccRepository.save(ncc);
    	return new Response<NccResponseDTO>(null, null, "OK");
    }

    @PostMapping("/info")
    public ResponseEntity<Ncc> getInfo() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();

        Ncc ctv = nccRepository.findById(username).orElseThrow(() -> new UsersException());

        return new ResponseEntity<Ncc>(ctv, HttpStatus.OK);
    }

    @PostMapping("/dangkishop")
    public ResponseEntity<Ncc> dkshop(@RequestParam String code) {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();

        Ncc ncc = nccRepository.findById(username).orElseThrow(() -> new UsersException());
        ncc.setIdghn(code);

        return new ResponseEntity<Ncc>(nccRepository.save(ncc), HttpStatus.OK);
    }

    @GetMapping("/getdto/{id}")
    public ResponseEntity<NccResponseDTO> getnccdto(@PathVariable("id") String username) {
        NccResponseDTO dto = new NccResponseDTO();
        Ncc ncc = nccRepository.findById(username, true);

        return new ResponseEntity<NccResponseDTO>(dto.createByEntity(ncc), HttpStatus.OK);
    }

    @PutMapping("/{username}")
    public ResponseEntity<NccRequestDTO> putdata(@PathVariable("username") String username,
            @RequestBody NccRequestDTO dto) {

        Ncc ncc = nccRepository.findById(username).map((e) -> {

            e.setAddress(dto.getAddress());
            e.setCity(dto.getCity());
            e.setDescription(dto.getDescription());
            e.setEmail(dto.getEmail());
            e.setFullname(dto.getFullname());
            e.setNcclogo(dto.getNcclogo());
            e.setNccname(dto.getNccname());
            e.setSdt(dto.getSdt());
            e.setSex(dto.getSex());
            return e;
        }).orElseThrow(() -> new UsersException());

        return new ResponseEntity<NccRequestDTO>(dto.createByEntity(nccRepository.save(ncc)), HttpStatus.OK);
    }

    @GetMapping("/details_ctv/{ctv}")
    public ResponseEntity<CtvAndListOrder> getCtvAndlIST(@PathVariable("ctv") String id) {
        CtvAndListOrder rs = new CtvAndListOrder();
        Ctv ctv = ctvRepository.findById(id).orElseThrow(() -> new NotFoundSomething(":("));
        List<Orders> list = ctv.getOrders().stream().filter((e) -> e.getStatus() == 5).collect(Collectors.toList());
        list.sort((e1, e2) -> {
            if (e1.getDatefinish().getTime() < e2.getDatefinish().getTime()) {
                return 1;
            } else {
                return -1;
            }
        });

        rs.setCtv(ctv);
        rs.setOrders_done(list);

        return new ResponseEntity<CtvAndListOrder>(rs, HttpStatus.OK);

    }

}
