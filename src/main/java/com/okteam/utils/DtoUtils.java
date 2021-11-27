package com.okteam.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.okteam.dao.CtvRepository;
import com.okteam.dto.CtvResponseDTO;
import com.okteam.dto.NccResponseDTO;
import com.okteam.entity.Ctv;
import com.okteam.entity.Ncc;

@Service
public class DtoUtils {
	@Autowired
	CtvRepository ctvRepo;
	
	public List<CtvResponseDTO> mapCtvToDto(List<Ctv> list){
		return list.stream().map(ctv->new CtvResponseDTO().createByEntity(ctv)).collect(Collectors.toList());
	}
	public List<NccResponseDTO> mapNccToDto(List<Ncc> list){
		return list.stream().map(ncc->new NccResponseDTO().createByEntity(ncc)).collect(Collectors.toList());
	}
}
