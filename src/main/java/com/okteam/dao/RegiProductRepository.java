package com.okteam.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import com.okteam.dto.ReportFollowInNcc;
import com.okteam.entity.Ncc;
import com.okteam.entity.Products;
import com.okteam.entity.RegiProducts;

public interface RegiProductRepository extends JpaRepository<RegiProducts, Integer> {

    @Query("select o from RegiProducts o where o.ctv.username=?1 and o.products.category.idcate like ?2 and o.products.name like ?3")
    public Page<RegiProducts> getRegiProductsWithCtv(String id, String category, String name, Pageable pageable);

    @Query("select o.products.ncc from RegiProducts o where o.ctv.username = ?1 group by o.products.ncc")
    public Page<Ncc> getAllNccOfCtv(String id, Pageable pageable);

    @Query("select o from RegiProducts o where o.products.ncc.username like ?1 and o.ctv.username = ?2")
    public Page<RegiProducts> getProductsOfNcc(String ncc, String ctv, Pageable pageable);

    @Query("select o from RegiProducts o where o.products.ncc.username like ?1 and o.products.idpro like ?2 and o.ctv.fullname like ?3")
    public Page<RegiProducts> getCtvRegi(String ncc, String idpro, String namectv, Pageable pageable);

}
