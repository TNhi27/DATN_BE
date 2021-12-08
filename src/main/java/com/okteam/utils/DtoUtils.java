package com.okteam.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.okteam.dto.AdminResponseDto;
import com.okteam.dto.BrandDTO;
import com.okteam.dto.Categorydto;
import com.okteam.dto.CtvResponseDTO;
import com.okteam.dto.NccResponseDTO;
import com.okteam.dto.OrdersResponseDTO;
import com.okteam.dto.PostRespDTO;
import com.okteam.dto.ProductsResponseDTO;
import com.okteam.dto.PropertiesReqDto;
import com.okteam.dto.RegiProductsRespDto;
import com.okteam.dto.TransactionDTO;
import com.okteam.entity.Admin;
import com.okteam.entity.Brand;
import com.okteam.entity.Category;
import com.okteam.entity.Ctv;
import com.okteam.entity.Ncc;
import com.okteam.entity.Orders;
import com.okteam.entity.Post;
import com.okteam.entity.Products;
import com.okteam.entity.Properties;
import com.okteam.entity.RegiProducts;
import com.okteam.entity.Transaction;

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
	public List<AdminResponseDto> mapAdminToDto(List<Admin> list){
		return list.stream().map(ad->new AdminResponseDto().createByEntity(ad)).collect(Collectors.toList());
	}
	public List<PropertiesReqDto> mapPropertiesToDto(List<Properties> list){
		return list.stream().map(pro->new PropertiesReqDto().createByEntity(pro)).collect(Collectors.toList());
	}
	public List<RegiProductsRespDto> mapRegiProductsToDto(List<RegiProducts> list){
		return list.stream().map(regi->new RegiProductsRespDto().createByEntity(regi)).collect(Collectors.toList());
	}
	public List<OrdersResponseDTO> mapOrdersToDto(List<Orders> list){
		return list.stream().map(ord->new OrdersResponseDTO().createByEntity(ord)).collect(Collectors.toList());
	}
	public List<PostRespDTO> mapPostToDto(List<Post> list){
		return list.stream().map(po-> new PostRespDTO().createByEntity(po)).collect(Collectors.toList());
	}
	public List<TransactionDTO> mapTransactionToDto(List<Transaction> list){
		return list.stream().map(tr->new TransactionDTO().createByEntity(tr)).collect(Collectors.toList());
	}
}
