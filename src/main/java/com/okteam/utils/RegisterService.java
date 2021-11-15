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
	
	 private void sendVerificationEmail(Object account, Integer thaotac) throws UnsupportedEncodingException, MessagingException{
	    	String toAddress = thaotac == 0 ? ((Ctv) account).getEmail() : ((Ncc) account).getEmail();
	    	String fromAddress = "dinhtppc00576@fpt.edu.vn";
	    	String senderName = "Okteam shop";
	    	String subject = "Kích hoạt tài khoản!";
	    	String content = "Chào "+ (thaotac == 0 ? ((Ctv) account).getFullname() : ((Ncc) account).getEmail()) +",<br>"
	                + "Mã kích hoạt tài khoản của ban là:<br>"
	                + "<h3>[[VERIFY]]</h3>"
	                + "Cảm ơn bạn đã chọn chúng tôi!,<br>"
	                + "Okteam shop.";
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
			 return false;
		 }
		 return true;
	 }
	 
}
