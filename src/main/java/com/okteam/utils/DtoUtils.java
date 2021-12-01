package com.okteam.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.okteam.dto.BrandDTO;
import com.okteam.dto.Categorydto;
import com.okteam.dto.CtvResponseDTO;
import com.okteam.dto.NccResponseDTO;
import com.okteam.dto.OrdersResponseDTO;
import com.okteam.dto.ProductsResponseDTO;
import com.okteam.entity.Brand;
import com.okteam.entity.Category;
import com.okteam.entity.Ctv;
import com.okteam.entity.Ncc;
import com.okteam.entity.Orders;
import com.okteam.entity.Products;

@Service
public class DtoUtils {

	public List<CtvResponseDTO> mapCtvToDto(List<Ctv> list){
		return list.stream().map(ctv->new CtvResponseDTO().createByEntity(ctv)).collect(Collectors.toList());
	}
	public List<NccResponseDTO> mapNccToDto(List<Ncc> list){
		return list.stream().map(ncc->new NccResponseDTO().createByEntity(ncc)).collect(Collectors.toList());
	}
	public List<Categorydto> mapCategoryToDto(List<Category> list){
		return list.stream().map(c->new Categorydto().createByEntity(c)).collect(Collectors.toList());
	}
	public List<BrandDTO> mapBrandToDto(List<Brand> list){
		return list.stream().map(b->new BrandDTO().createByEntity(b)).collect(Collectors.toList());
	}
	public List<ProductsResponseDTO> mapProductsToDto(List<Products> list){
		return list.stream().map(p->new ProductsResponseDTO().createByEntity(p)).collect(Collectors.toList());
	}
	public List<OrdersResponseDTO> mapOrdersToDto(List<Orders> list){
		return list.stream().map(o->new OrdersResponseDTO().createByEntity(o)).collect(Collectors.toList());
	}
}
