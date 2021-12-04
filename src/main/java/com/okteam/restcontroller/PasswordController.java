package com.okteam.restcontroller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.okteam.dao.AdminRepository;
import com.okteam.dao.CtvRepository;
import com.okteam.dao.NccRepository;
import com.okteam.dto.CtvReqDTO;
import com.okteam.dto.CtvResponseDTO;
import com.okteam.dto.NccDto;
import com.okteam.dto.NccResponseDTO;
import com.okteam.entity.Ctv;
import com.okteam.entity.Ncc;
import com.okteam.entity.Response;
import com.okteam.utils.RegisterService;

@RestController
@CrossOrigin
@RequestMapping("/password")
public class PasswordController {

	@Autowired
	RegisterService service;
	@Autowired
	CtvRepository ctvRepo;
	@Autowired
	NccRepository nccRepo;
	@Autowired
	AdminRepository adRepo;

	
	
	@PostMapping("/ctv/register")
	public Response<CtvResponseDTO> registerCtv(@RequestBody CtvReqDTO ctv)
			throws UnsupportedEncodingException, MessagingException {
		String message = "OK";
		Ctv ctvDangky = new Ctv().dtoReturnEntity(ctv);
		if (service.checkUsername(ctv.getUsername())) {
			message = "Username đã tồn tại!";
		} else {
			ctvDangky = service.registerCtv(ctvDangky);
		}
		return new Response<CtvResponseDTO>(null, ctvDangky, message);
	}

	@PostMapping("/ctv/active")
	public Response<CtvResponseDTO> activeCtv(@RequestParam("username") String username, @RequestParam("verify") String verify){
		String message = "OK";
		if(!ctvRepo.existsById(username)) {
			message = "Tài khoản cộng tác viên không tồn tại!";
		} else {
			Ctv ctv = ctvRepo.findById(username).get();
			if(!ctv.getVerify().equals(verify)) {
				message = "Mã xác nhận không chính xác!";
			} else {
				ctv.setActive(true);
				ctv.setVerify(null);
				ctvRepo.save(ctv);
			}	
		}
		return new Response<CtvResponseDTO>(null, null, message);
	}
	
	@PostMapping("/ncc/register")
    public Response<NccResponseDTO> registerNcc(@RequestBody NccDto ncc)
            throws UnsupportedEncodingException, MessagingException {
        String message = "OK";
        Ncc nccDangky = new Ncc().dtoReturnEntity(ncc);
        if (service.checkUsername(ncc.getUsername())) {
            message = "Username đã tồn tại!";
        } else {
        	nccDangky = service.registerNcc(nccDangky);
        }
        return new Response<NccResponseDTO>(null, nccDangky, message);
    }

	@PostMapping("/ncc/active")
    public Response<NccResponseDTO> activeNcc(@RequestParam("username") String username, @RequestParam("verify") String verify){
    	String message = "OK";
    	if(!nccRepo.existsById(username)) {
    		message = "Tài khoản nhà cung cấp không tồn tai!";
    	} else {
    		Ncc ncc = nccRepo.findById(username).get();
    		if(!ncc.getVerify().equals(verify)) {
    			message = "Mã xác nhận không chính xác!";
    		}else {
    			ncc.setActive(true);
    			ncc.setVerify(null);
    			nccRepo.save(ncc);
    		}
    	}
    	return new Response<NccResponseDTO>(null, null, message);
    }
	
	
	@PostMapping("/forget")
	public Response<Object> forget(@RequestParam("username") String username, @RequestParam("email") String email)
			throws UnsupportedEncodingException, MessagingException {
		String message = "OK";
		if (!service.checkUsername(username)) {
			message = "Không tìm thấy tài khoản!";
		} else {
			if (service.isCtv(username) && !email.equalsIgnoreCase(ctvRepo.findById(username).get().getEmail())) {
				return new Response<Object>(null, null, "Email tài khoản không đúng!");
			}
			if (service.isNcc(username) && !email.equalsIgnoreCase(nccRepo.findById(username).get().getEmail())) {
				return new Response<Object>(null, null, "Email tài khoản không đúng!");
			}
			if (service.isAdmin(username) && !email.equalsIgnoreCase(adRepo.findById(username).get().getEmail())) {
				return new Response<Object>(null, null, "Email tài khoản không đúng!");
			}
			service.forgetPW(username);
		}
		return new Response<Object>(null, null, message);
	}

	@PostMapping("/change")
	public Response<Object> change(@RequestParam("password") String password, @RequestParam("newP") String newP,
			@RequestParam("confirmP") String confirmP) {
		String message = "OK";
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if(service.isAdmin(username)) {
			message = "Tài khoản quản trị không dùng chức năng này!";
		} else {
			if(service.isCtv(username) && !password.equalsIgnoreCase(ctvRepo.findById(username).get().getPassword())) {
				return new Response<Object>(null, null, "Mật khẩu không đúng!");
				
			}
			if(service.isNcc(username) && !password.equalsIgnoreCase(nccRepo.findById(username).get().getPassword())) {
				return new Response<Object>(null, null, "Mật khẩu không đúng!");
			}
			if(!newP.equalsIgnoreCase(confirmP)) {
				return new Response<Object>(null, null, "Xác nhận mật khẩu không đúng!");
			}
			service.changePW(username, newP);
		}
		return new Response<Object>(null, null, message);
	}

}