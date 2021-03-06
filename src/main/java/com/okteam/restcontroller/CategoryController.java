package com.okteam.restcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.okteam.dao.BrandRepository;
import com.okteam.dao.CategoryRepository;
import com.okteam.dto.Categorydto;
import com.okteam.entity.Brand;
import com.okteam.entity.Category;
import com.okteam.entity.CategoryResponse;
import com.okteam.entity.Response;
import com.okteam.exception.NotFoundSomething;
import com.okteam.utils.DtoUtils;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/category")
public class CategoryController {
	@Autowired
	CategoryRepository categoryRepo;
	@Autowired
	DtoUtils dtoUtils;
	@Autowired
	BrandRepository brandRepository;

	@GetMapping
	public ResponseEntity<List<Category>> get_all() {
		return new ResponseEntity<List<Category>>(categoryRepo.findAll(), HttpStatus.OK);
	}

	@GetMapping("/list")
	public Response<Categorydto> getCategories() {
		return new Response<Categorydto>(dtoUtils.mapCategoryToDto(categoryRepo.findAll()), null, "OK");
	}
	
	@GetMapping("/check-id/{idcate}")
	public Boolean checkidcate(@PathVariable("idcate") String idcate) {
		return categoryRepo.existsById(idcate);
	}

	@PostMapping("/add")
	public Response<Categorydto> addCategory(@RequestBody Categorydto category) {
		String message = "OK";
		if (categoryRepo.existsById(category.getIdcate())) {
			message = "Mã loại đã tồn tại, vui lòng chọn mã khác!";
		} else if (category.getParent() == null) {
			categoryRepo.save(new Category().dtoReturnEntity(category));
		} else {
			if(!categoryRepo.existsById(category.getParent())){
				message = "Không tìm thấy menu cha!";
			} else {
				int lvlParent = categoryRepo.findById(category.getParent()).get().getLv();
				if(lvlParent >= category.getLv() || (category.getLv()-1) != lvlParent) {
					message = "Cấp menu loại cha không hợp lệ";
				} else {
					categoryRepo.save(new Category().dtoReturnEntity(category));
				}
			}
		}
		return new Response<Categorydto>(dtoUtils.mapCategoryToDto(categoryRepo.findAll()), null, message);
	}

	@PutMapping("/update")
	public Response<Categorydto> updateCategory(@RequestParam(name = "idcate") String idcate,
			@RequestParam(name = "value") String value, @RequestParam(name = "thaotac") Integer thaotac) {
		String message = "OK";
		Category category = new Category();
		if(!categoryRepo.existsById(idcate)) {
			message = "Không tìm thấy loại hàng!";
		} else {
			category = categoryRepo.findById(idcate).get();
			if (thaotac == 0) {
				if (value.isEmpty()) {
					message = "Không để trống tên loại!";
				} else {
					category.setTypename(value);
					categoryRepo.save(category);
				}
			} else if (thaotac == 1) {
				category.setImg(value);
				categoryRepo.save(category);
			} else if (thaotac == 2) {
				if (!value.isEmpty() && !categoryRepo.existsById(value.toUpperCase())) {
					message = "Không tìm thấy menu cha!";
				} else {
					if (value.isEmpty()) {
						value = null;
						category.setParent(value);
						categoryRepo.save(category);
					} else {
						int lvlParent = categoryRepo.findById(value).get().getLv();
						if(lvlParent >= category.getLv() || (category.getLv()-1) != lvlParent) {
							message = "Cấp menu loại cha không hợp lệ";
						} else {
							category.setParent(value.toUpperCase());
							categoryRepo.save(category);
						}
					}
					
				}
			} else if(thaotac == 3) {
				if(category.getParent() != null) {
					message = "Không thể cập nhật cấp menu khi đang là menu con!";
				} else {
					category.setLv(Integer.parseInt(value));
					categoryRepo.save(category);
				}
			} else {
				return new Response<Categorydto>(null, null, "Thao tác không hợp lệ");
			}
		}
		return new Response<Categorydto>(null, new Categorydto().createByEntity(category), message);
	}

	@DeleteMapping("/delete")
	public Response<Categorydto> deleteCategory(@RequestParam(name = "idcate") String idcate) {
		String message = "Không tìm thấy loại hàng!";
		if (categoryRepo.existsById(idcate)) {
			Category c = categoryRepo.findById(idcate).get();
			if (c.getBrands().size() > 0) {
				message = "Loại hàng đã có nhãn hàng!";
			} else if (c.getProducts().size() > 0) {
				message = "Loại hàng đã có sản phẩm!";
			} else {
				if (categoryRepo.findByParent(idcate).size() > 0) {
					message = "Loại hàng này có loại con!";

				} else {
					categoryRepo.deleteById(idcate);
					message = "OK";
				}
			}
		}
		return new Response<Categorydto>(dtoUtils.mapCategoryToDto(categoryRepo.findAll()), null, message);
	}

	@PostMapping
	public ResponseEntity<Category> saveCaterogy(@RequestBody Categorydto categorydto) {
		Category cate = new Category();
		try {
			if (categoryRepo.existsById(categorydto.getIdcate())) {
				System.out.print("ID loại sản phẩm đã tồn tại");
				return new ResponseEntity<Category>(cate, HttpStatus.NOT_FOUND);
			} else {
				cate.setIdcate(categorydto.getIdcate());
				cate.setTypename(categorydto.getTypename());
				cate.setImg(categorydto.getImg());
				cate.setParent(categorydto.getParent());
			}
			return new ResponseEntity<Category>(categoryRepo.save(cate), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// Update cate
	@PutMapping("/{id}")
	public ResponseEntity<Category> updateCategory(@PathVariable("id") String id,
			@RequestBody Categorydto categorydto) {
		try {
			Category cate = categoryRepo.findById(id).get();
			cate.setTypename(categorydto.getTypename());
			cate.setImg(categorydto.getImg());
			cate.setParent(categorydto.getParent());
			return new ResponseEntity<Category>(categoryRepo.save(cate), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// Delete cate
	@DeleteMapping("/{id}")
	public void deleteCate(@PathVariable("id") String id) {
		categoryRepo.deleteById(id);
	}

	@GetMapping("/get_parent")
	public ResponseEntity<List<CategoryResponse>> gResponseEntity() {
		List<Category> list = categoryRepo.findAll();

		List<CategoryResponse> rs = new ArrayList<>();
		for (Category category : list) {
			if (category.getParent() == null) {
				CategoryResponse cate = new CategoryResponse();
				List<Category> child = list.stream().filter((e) -> e.getParent() != null)
						.filter((e) -> e.getParent().equals(category.getIdcate())).collect(Collectors.toList());
				List<Brand> brand = brandRepository.findByIdcate(category.getIdcate());
				cate.setBrands(brand);
				cate.setCategory(category);
				cate.setChild(child);

				rs.add(cate);
			}
		}
		return new ResponseEntity<List<CategoryResponse>>(rs, HttpStatus.OK);

	}

	@GetMapping("/getone/{id}")
	public ResponseEntity<Category> getcate1(@PathVariable("id") String id) {
		Category cate = categoryRepo.findById(id).orElseThrow(() -> new NotFoundSomething(":("));
		return new ResponseEntity<Category>(cate, HttpStatus.OK);
	}

}
