package com.example.WebBanNhac.services;

import com.example.WebBanNhac.entity.Phieudathang;
import com.example.WebBanNhac.repository.IPdhRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PDHServices
{
    @Autowired
    private IPdhRepository phdRepository;

    public List<Phieudathang> getAllPDHs (){
        return phdRepository.findAll();
    }
    public Phieudathang getPDHById(Long Id) {
        return phdRepository.findById(Id).orElse(null);
    }

    public void addPDH(Phieudathang PDH) {phdRepository.save(PDH);
    }
    public void deletePDH(Long id){
        phdRepository.deleteById(id);
    }
    public void updatePDH(Phieudathang Id){
        phdRepository.save(Id);
    }

}
