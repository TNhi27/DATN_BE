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
		String verify = RandomString.make(4);
		ctv.setVerify(verify);
		ctvRepo.save(ctv);
		sendVerificationEmail(ctv, 0);
		return ctv;
	}
	
	public Ncc registerNcc(Ncc ncc) throws UnsupportedEncodingException, MessagingException {
		String verify = RandomString.make(4);
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
	    	String subject = "K??ch ho???t t??i kho???n!";
	    	String content = "Ch??o "+ (thaotac == 0 ? ((Ctv) account).getFullname() : ((Ncc) account).getFullname()) +",<br>"
	                + "M?? k??ch ho???t t??i kho???n c???a ban l??:<br>"
	                + "<h3>[[VERIFY]]</h3>"
	                + "C???m ??n b???n ???? ch???n ch??ng t??i!,<br>"
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
	    	String subject = "C???p l???i m???t kh???u!";
	    	String fullname = "";
	    	if(thaotac == 0) {
	    		toAddress = ((Ctv) account).getEmail();
	    		fullname = ((Ctv) account).getFullname();
	    	}
	    	if(thaotac == 1) {
	    		toAddress = ((Ncc) account).getEmail();
	    		fullname = ((Ncc) account).getFullname();
	    	}
	    	String content = "Ch??o "+ fullname +",<br>"
	                + "M???t kh???u t??i kho???n c???a b???n l?? : <h3>[[password]]</h3>"
	                + "Okteam Shop.";
	    	if(thaotac == 2) {
	    		toAddress = ((Admin) account).getEmail();
	    		content = "Ch??o "+ ((Admin) account).getFullname() +",<br>"
		                + "V?? ????y l?? t??i kho???n qu???n tr??? vi??n, b???n vui l??ng li??n h??? ?????a ch??? <b>dinhtppc00576@gmail.com</b> ????? gi???i quy???t"
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
