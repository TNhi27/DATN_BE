package com.okteam.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.okteam.dao.AdminRepository;
import com.okteam.dao.CtvRepository;
import com.okteam.dao.NccRepository;
import com.okteam.entity.Admin;
import com.okteam.entity.Ctv;
import com.okteam.entity.Ncc;

import net.bytebuddy.utility.RandomString;

@Service
public class RegisterService {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	NccRepository nccRepo;
	@Autowired
	CtvRepository ctvRepo;
	@Autowired
	AdminRepository adRepo;
	List<MimeMessage> list = new ArrayList<>();

	public Ctv registerCtv(Ctv ctv) throws UnsupportedEncodingException, MessagingException {
		String verify = RandomString.make(64);
		ctv.setVerify(verify);
		ctvRepo.save(ctv);
		sendVerificationEmail(ctv, 0);
		return ctv;
	}
	
	public Ncc registerNcc(Ncc ncc) throws UnsupportedEncodingException, MessagingException {
		String verify = RandomString.make(64);
		ncc.setVerify(verify);
		nccRepo.save(ncc);
		sendVerificationEmail(ncc, 1);
		return ncc;
	}
	
	public void forgetPW(String username) throws UnsupportedEncodingException, MessagingException {
		if(ctvRepo.existsById(username)) {
			sendMail(ctvRepo.findById(username).get(), 0);
		}
		if(nccRepo.existsById(username)) {
			sendMail(nccRepo.findById(username).get(), 1);
		}
		if(adRepo.existsById(username)) {
			sendMail(adRepo.findById(username).get(), 2);
		}
	}
	
	public void changePW(String username, String newPass) {
		if(ctvRepo.existsById(username)) {
			Ctv ctv = ctvRepo.findById(username).get();
			ctv.setPassword(newPass);
			ctvRepo.save(ctv);
		}
		if(nccRepo.existsById(username)) {
			Ncc ncc = nccRepo.findById(username).get();
			ncc.setPassword(newPass);
			nccRepo.save(ncc);
		}
	}
	
	 private void sendVerificationEmail(Object account, Integer thaotac) throws UnsupportedEncodingException, MessagingException{
	    	String toAddress = thaotac == 0 ? ((Ctv) account).getEmail() : ((Ncc) account).getEmail();
	    	String fromAddress = "dinhtppc00576@fpt.edu.vn";
	    	String senderName = "Okteam Shop";
	    	String subject = "Kích hoạt tài khoản!";
	    	String content = "Chào "+ (thaotac == 0 ? ((Ctv) account).getFullname() : ((Ncc) account).getFullname()) +",<br>"
	                + "Mã kích hoạt tài khoản của ban là:<br>"
	                + "<h3>[[VERIFY]]</h3>"
	                + "Cảm ơn bạn đã chọn chúng tôi!,<br>"
	                + "Okteam Shop.";
	    	MimeMessage message = mailSender.createMimeMessage();
	    	MimeMessageHelper helper = new MimeMessageHelper(message);
	    	helper.setFrom(fromAddress, senderName);
	    	helper.setTo(toAddress);
	    	helper.setSubject(subject);
	    	String verify = (thaotac == 0 ? ((Ctv) account).getVerify() : ((Ncc) account).getVerify());
	    	content = content.replace("[[VERIFY]]", verify);
	    	helper.setText(content, true);
	    	queue(message);
	    }

	 public void sendMail(Object account, Integer thaotac) throws UnsupportedEncodingException, MessagingException {
	    	String toAddress = "" ;
	    	String fromAddress = "dinhtppc00576@fpt.edu.vn";
	    	String senderName = "Okteam Shop";
	    	String subject = "Cấp lại mật khẩu!";
	    	String fullname = "";
	    	if(thaotac == 0) {
	    		toAddress = ((Ctv) account).getEmail();
	    		fullname = ((Ctv) account).getFullname();
	    	}
	    	if(thaotac == 1) {
	    		toAddress = ((Ncc) account).getEmail();
	    		fullname = ((Ncc) account).getFullname();
	    	}
	    	String content = "Chào "+ fullname +",<br>"
	                + "Mật khẩu tài khoản của bạn là : <h3>[[password]]</h3>"
	                + "Okteam Shop.";
	    	if(thaotac == 2) {
	    		toAddress = ((Admin) account).getEmail();
	    		content = "Chào "+ ((Admin) account).getFullname() +",<br>"
		                + "Vì đây là tài khoản quản trị viên, bạn vui lòng liên hệ địa chỉ <b>dinhtppc00576@gmail.com</b> để giải quyết"
		                + "Okteam Shop.";
	    	}
	    	MimeMessage message = mailSender.createMimeMessage();
	    	MimeMessageHelper helper = new MimeMessageHelper(message);
	    	helper.setFrom(fromAddress, senderName);
	    	helper.setTo(toAddress);
	    	helper.setSubject(subject);
	    	if(thaotac != 2) {
	    		content = content.replace("[[password]]", thaotac == 0 ? ((Ctv) account).getPassword() : ((Ncc) account).getPassword());
	    	}
	    	helper.setText(content, true);
	    	queue(message);
	    }
	 
	 public void queue(MimeMessage message) {
			list.add(message);
		}
	 
	 @Scheduled(fixedDelay = 5000)
		public void run() {
			while (!list.isEmpty()) {
				MimeMessage message = list.remove(0);
				try {
					mailSender.send(message);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	 
	 public Boolean checkUsername(String username) {
		 if(ctvRepo.existsById(username) || nccRepo.existsById(username) || adRepo.existsById(username)) {
			 return true;
		 }
		 return false;
	 }
	 
	 public Boolean isAdmin(String username) {
		 if(adRepo.existsById(username)) {
			 return true;
		 }
		 return false;
	 }
	 
	 public Boolean isCtv(String username) {
		 if(ctvRepo.existsById(username)) {
			 return true;
		 }
		 return false;
	 }
	 public Boolean isNcc(String username) {
		 if(nccRepo.existsById(username)) {
			 return true;
		 }
		 return false;
	 }
	 
}
