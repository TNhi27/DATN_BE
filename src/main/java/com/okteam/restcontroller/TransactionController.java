package com.okteam.restcontroller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.okteam.dao.InfoBanksRepository;
import com.okteam.dao.TransactionRepository;
import com.okteam.dto.TransactionDTO;
import com.okteam.entity.Comments;
import com.okteam.entity.InfoBanks;
import com.okteam.entity.RegiProducts;
import com.okteam.entity.Transaction;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/transaction")
public class TransactionController {
	@Autowired
	TransactionRepository TranRep;
	@Autowired
	InfoBanksRepository infoRep;

	@GetMapping
	public ResponseEntity<List<Transaction>> getAllTran() {
		return new ResponseEntity<List<Transaction>>(TranRep.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("{idtran}")
	public ResponseEntity<Transaction> getTranById(@PathVariable("idtran") Integer idtran) {
		Transaction tran = TranRep.findById(idtran).get();
		ResponseEntity.notFound().build();
		
		return new ResponseEntity<Transaction>(tran, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Transaction> postTran(@RequestBody TransactionDTO Transactiondto) {
		Transaction tran = new Transaction();
		
		if(!TranRep.existsById(Transactiondto.getIdtran())) {
			System.out.println("Không tìm thấy ID");
			   return new ResponseEntity<Transaction>(tran, HttpStatus.NOT_FOUND);
		}else if(!infoRep.existsById(Transactiondto.getId())){
			System.out.println("Không tim thấy Info");
			   return new ResponseEntity<Transaction>(tran, HttpStatus.NOT_FOUND);
		}
		else {
		InfoBanks info = infoRep.findById(Transactiondto.getId()).get();
		
		tran.setIdtran(Transactiondto.getIdtran());
		tran.setType(Transactiondto.getType());
		tran.setUsername(Transactiondto.getUsername());
		tran.setValue(Transactiondto.getValue());
		tran.setNote(Transactiondto.getNote());
		tran.setDone(true);
		tran.setCreatedate(Transactiondto.getCreatedate());
		tran.setTran_bank(info);
		}
		
	return new ResponseEntity<Transaction>(TranRep.save(tran), HttpStatus.CREATED);
	}
	
    @PutMapping("/{idtran}")
    public ResponseEntity<Transaction> updateTran(@RequestBody TransactionDTO Transactiondt, @PathVariable Integer idtran) {
    	Transaction transac = TranRep.findById(idtran).get();
    	
    	if(!TranRep.existsById(Transactiondt.getIdtran())) {
			System.out.println("ko tìm thấy ID");
			   return new ResponseEntity<Transaction>(transac, HttpStatus.NOT_FOUND);
		}else if(!infoRep.existsById(Transactiondt.getId())){
			System.out.println("CTV không tim thấy");
			   return new ResponseEntity<Transaction>(transac, HttpStatus.NOT_FOUND);
		}else {
			transac.setType(Transactiondt.getType());
    		transac.setUsername(Transactiondt.getUsername());
    		transac.setValue(Transactiondt.getValue());
    		transac.setNote(Transactiondt.getNote());
    		transac.setDone(true);
    		transac.setCreatedate(Transactiondt.getCreatedate());
    		transac.setTran_bank(infoRep.findById(Transactiondt.getId()).get());
		}
    		
			return  new ResponseEntity<Transaction>(TranRep.save(transac),HttpStatus.OK);
    }
    
   
    @DeleteMapping("/{idtran}")
	public void deleteTran(@PathVariable("idtran") Integer idtran){
    	if (!TranRep.existsById(idtran)) {
    		System.out.println("ko tìm thấy");
		} else {
			TranRep.deleteById(idtran);
		}
    	
	}
    
}
