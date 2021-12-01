package com.okteam.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.okteam.dao.ProductRepository;
import com.okteam.dao.PropertiesReponsitory;
import com.okteam.dto.PropertiesReqDto;
import com.okteam.entity.Properties;
import com.okteam.entity.Response;
import com.okteam.utils.DtoUtils;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/properties")
public class PropertiesController {
	
	@Autowired
	PropertiesReponsitory proRepo;
	@Autowired
	ProductRepository productRepo;
	@Autowired
	DtoUtils dtoUtils;

	@GetMapping("/list")
	public Response<PropertiesReqDto> listByIdpro(@RequestParam(value = "idpro", required = false) String idpro){
		String message = "Không lấy được dữ liệu!";
		List<Properties> list = proRepo.findAll();
		if(idpro == null) {
			message = "OK";
		} else {
			message = "OK";
			list = proRepo.findByIdpro(idpro);
		}
		return new Response<PropertiesReqDto>(dtoUtils.mapPropertiesToDto(list), null, message);
	}
	
	@PostMapping("/addTo/{idpro}")
	public Response<PropertiesReqDto> addProperty(@RequestBody PropertiesReqDto pro, @PathVariable("idpro") String idpro){
		String message = "OK";
		boolean check = proRepo.findByIdpro(idpro).stream().allMatch(rs->!rs.getKeyp().equalsIgnoreCase(pro.getKeyp()));
		if(!check) {
			message = "Thuộc tính đã tồn tại trong sản phẩm này!";
		} else {
			Properties property = new Properties().dtoReturnEntity(pro);
			property.setP_properties(productRepo.findById(idpro).get());
			proRepo.save(property);
		}
		return new Response<PropertiesReqDto>(dtoUtils.mapPropertiesToDto(proRepo.findByIdpro(idpro)), null, message);
	}
	
	@DeleteMapping("/delete/{id}/{isAll}")
	public Response<PropertiesReqDto> deleteProperty(@PathVariable("id") Integer id, @PathVariable("isAll") Boolean isAll){
		String message = "OK";
		if(!proRepo.existsById(id)) {
			return new Response<PropertiesReqDto>(null, null, "Không tìm thấy thuộc tính");
		}
		Properties pro = proRepo.findById(id).get();
		proRepo.deleteById(id);
		if(!isAll) {
			String idpro = pro.getP_properties().getIdpro();
			return new Response<PropertiesReqDto>(dtoUtils.mapPropertiesToDto(proRepo.findByIdpro(idpro)), null, message);
		}
		return new Response<PropertiesReqDto>(dtoUtils.mapPropertiesToDto(proRepo.findAll()), null, message);
	}
	
	@PutMapping("/reform/{id}")
	public Response<PropertiesReqDto> reform( @PathVariable("id") Integer id,
			@RequestParam("thaotac") Integer thaotac, @RequestParam("value") String value){
		if(!proRepo.existsById(id)) {
			return new Response<PropertiesReqDto>(null, null, "Không tìm thấy thuộc tính!");
		}
		String message = "OK";
		Properties pro = proRepo.findById(id).get();
		if(thaotac == 0) {
			if(value.isEmpty()) {
				message = "Thuộc tính không hợp lệ!";
			} else {
				String idpro = pro.getP_properties().getIdpro();
				boolean check = proRepo.findByIdpro(idpro).stream().allMatch(rs->!rs.getKeyp().equalsIgnoreCase(value));
				if (!check) {
					message = "Tên thuộc tính đã tồn tại trong cùng sản phẩm!";
				} else {
					pro.setKeyp(value);
					proRepo.save(pro);
				}
			}
		} else if(thaotac == 1) {
			if(value.isEmpty()) {
				message = "Giá trị không hợp lệ!";
			} else {
				pro.setValuep(value);
				proRepo.save(pro);
			}
		} else {
			return new Response<PropertiesReqDto>(null, null, "Thao tác không hợp lệ");
		}
		return new Response<PropertiesReqDto>(null, new PropertiesReqDto().createByEntity(pro), message);
	}
	
}
