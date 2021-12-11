package com.okteam.restcontroller;

import java.util.Date;
import java.util.Optional;

import com.okteam.dao.CtvRepository;
import com.okteam.dao.NccRepository;
import com.okteam.dao.TransactionRepository;
import com.okteam.dto.TransactionDTO;
import com.okteam.entity.Ctv;
import com.okteam.entity.Ncc;
import com.okteam.entity.Response;
import com.okteam.entity.Transaction;
import com.okteam.utils.DtoUtils;
import com.okteam.utils.RegisterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    @Autowired
    TransactionRepository tdao;
    @Autowired
    RegisterService registerService;
    @Autowired
    CtvRepository ctvRepo;
    @Autowired
    NccRepository nccRepo;
    @Autowired
    DtoUtils dtoUtils;

    @PostMapping("/get")
    public ResponseEntity<Page<Transaction>> get(@RequestParam Optional<String> type,
            @RequestParam Optional<String> done) {
        Sort s = Sort.by("createdate").descending();
        var name = SecurityContextHolder.getContext().getAuthentication().getName();
        Page page = tdao.getTransaction(type.orElse("%%"), name, PageRequest.of(0, 100, s));

        return new ResponseEntity<Page<Transaction>>(page, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Transaction> create(@RequestParam Integer value, @RequestParam Integer type,
            @RequestParam Optional<String> paypal) {
        Transaction tr = new Transaction();
        tr.setCreatedate(new Date());
        tr.setDone(0);
        tr.setIdpaypal(paypal.orElse(""));
        tr.setNote("");
        tr.setType(type);
        tr.setValue(value);
        tr.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        return new ResponseEntity<Transaction>(tdao.save(tr), HttpStatus.OK);
    }
    
    @GetMapping("/list")
    public Response<TransactionDTO> getAll(){
    	return new Response<TransactionDTO>(dtoUtils.mapTransactionToDto(tdao.findAll(Sort.by(Sort.Direction.DESC, "createdate"))), null, "OK");
    }
    
    @PutMapping("/duyet")
    public Response<TransactionDTO> duyetTran(@RequestParam("idtran") Integer idtran){
    	String message = "OK";
    	if(!tdao.existsById(idtran)) {
    		message = "Không tìm thấy yêu cầu!";
    	} else {
    		Transaction tran = tdao.findById(idtran).get();
    		if(tran.getDone() == 2) {
    			message = "Yêu cầu đã được xét duyệt xong!";
    		} else if(!registerService.checkUsername(tran.getUsername()) || registerService.isAdmin(tran.getUsername())) {
    			message = "Tài khoản không hợp lệ!";
    		} else if(tran.getType() == 1 && tran.getValue() > returnMoney(tran.getUsername())) {
    			message = "Số tiền rút lớn hơn số dư tài khoản!";
    		} else {
    			tran.setDone(1);
    			handleMoney(tran.getUsername(), tran.getValue(), tran.getType());
    			tdao.save(tran);
    		}
    	}
    	return new Response<TransactionDTO>(null, null, message);
    }
    
    @PutMapping("/huy/{idtran}")
    public Response<TransactionDTO> huyTran(@PathVariable("idtran") Integer idtran, @RequestParam("lydo") String lydo){
    	String message = "OK";
    	if(!tdao.existsById(idtran)) {
    		message = "Không tìm thấy yêu cầu!";
    	} else {
    		Transaction tran = tdao.findById(idtran).get();
    		if(tran.getDone() == 2) {
    			message = "Yêu cầu đã được xét duyệt xong!";
    		} else if(!registerService.checkUsername(tran.getUsername()) || registerService.isAdmin(tran.getUsername())) {
    			message = "Tài khoản không hợp lệ!";
    		} else {
    			tran.setDone(2);
    			tran.setNote(lydo);
    			tdao.save(tran);
    		}
    	}
    	return new Response<TransactionDTO>(null, null, message);
    }
    
    public Integer returnMoney(String username) {
    	if(registerService.isCtv(username)) {
    		return ctvRepo.findById(username).get().getMoney();
    	} else {
    		return nccRepo.findById(username).get().getMoney();
    	}
    }
    
    public void handleMoney(String username, Integer sotien, Integer type) {
    	if(registerService.isCtv(username)) {
    		Ctv ctv = ctvRepo.findById(username).get();
    		if(type == 0) {
    			ctv.setMoney(ctv.getMoney() + sotien);
    		} else {
    			ctv.setMoney(ctv.getMoney() - sotien);
    		}
    		ctvRepo.save(ctv);
    	} else {
    		Ncc ncc = nccRepo.findById(username).get();
    		if(type ==0) {
    			ncc.setMoney(ncc.getMoney() + sotien);
    		} else {
    			ncc.setMoney(ncc.getMoney() - sotien);
    		}
    		nccRepo.save(ncc);
    	}
    }
    
}