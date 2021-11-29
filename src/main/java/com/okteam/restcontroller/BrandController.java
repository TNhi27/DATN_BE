package com.okteam.restcontroller;

import java.util.ArrayList;
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

import com.okteam.dao.BrandRepository;
import com.okteam.dao.CategoryRepository;
import com.okteam.dto.BrandDTO;
import com.okteam.entity.Brand;
import com.okteam.entity.Category;
import com.okteam.entity.Response;
import com.okteam.utils.DtoUtils;

@CrossOrigin("*")
@RequestMapping("/api/v1/brand")
@RestController
public class BrandController {

	@Autowired
	BrandRepository brandRepo;
	@Autowired
	CategoryRepository categoryRepo;
	@Autowired
	DtoUtils dtoUtils;

	@GetMapping("/list")
	public Response<BrandDTO> getBrands(@RequestParam(value = "idcate", required = false) String idcate) {
		String message = "Không lấy được dữ liệu";
		List<Brand> list = new ArrayList<>();
		if (idcate == null) {
			list = brandRepo.findAll();
			message = "OK";
		} else {
			list = brandRepo.findByIdcate(idcate);
			message = "OK";
		}
		return new Response<BrandDTO>(dtoUtils.mapBrandToDto(list), message);
	}

	@PostMapping("/addTo/{idcate}")
	public Response<BrandDTO> addBrand(@PathVariable("idcate") String idcate, @RequestBody BrandDTO brand) {
		String message = "OK";
		boolean check = brandRepo.findByIdcate(idcate).stream()
				.allMatch(br -> !brand.getName().equalsIgnoreCase(br.getName()));
		if (!check) {
			message = "Tên nhãn hàng đã tồn tại trong loại hàng này!";
			return new Response<BrandDTO>(dtoUtils.mapBrandToDto(brandRepo.findByIdcate(idcate)), message);
		}
		Category c = categoryRepo.findById(idcate).get();
		Brand b =  new Brand().dtoReturnEntity(brand);
		b.setBr_category(c);
		brandRepo.save(b);
		return new Response<BrandDTO>(dtoUtils.mapBrandToDto(brandRepo.findByIdcate(idcate)), message);
	}

	@DeleteMapping("/delete/{id}/{idcate}")
	public Response<BrandDTO> deleteBrand(@PathVariable("id") Integer id, @PathVariable("idcate") String idcate) {
		String message = "Không tìm thấy nhãn hàng!";
		if (brandRepo.existsById(id)) {
			if (brandRepo.findById(id).get().getProducts().size() > 0) {
				message = "Nhãn hàng đã có sản phẩm!";
			} else {
				brandRepo.deleteById(id);
				message = "OK";
			}
		}
		return new Response<BrandDTO>(dtoUtils.mapBrandToDto(brandRepo.findByIdcate(idcate)), message);
	}

	@PutMapping("/update")
	public Response<BrandDTO> updateBrand(@RequestParam("id") Integer id, @RequestParam("value") String value) {
		String message = "OK";
		List<Brand>list = new ArrayList<>();
		Brand brand = brandRepo.findById(id).get();
		if (value.isEmpty()) {
			message = "Không được đễ trống tên";
		} else {
			String idcate = brandRepo.findById(id).get().getBr_category().getIdcate();
			boolean check = brandRepo.findByIdcate(idcate).stream().allMatch(br -> !value.equalsIgnoreCase(br.getName()));
			if (!check) {
				message = "Tên nhãn hàng đã tồn tại trong loại hàng này!";
			} else {
				brand.setName(value);
				brandRepo.save(brand);
				list.add(brand);
			}
		}
		list.add(brand);
		return new Response<BrandDTO>(dtoUtils.mapBrandToDto(list), message);
	}
}
