package com.okteam.dao;
import java.util.List;
import com.okteam.entity.Ncc;
import com.okteam.entity.Products;
import com.okteam.dto.NccResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
    
public interface NccRepository extends JpaRepository<Ncc, String> {
    @Query("SELECT o.ncc FROM Products o where o.idpro=?1 ")
    public Ncc getNccByProduct(String id);
    @Query(value = "SELECT * FROM Ncc ORDER BY RAND() LIMIT 1", nativeQuery = true)
    public List<Ncc> get10ncc();
}