package com.example.WebBanNhac.controller;

import com.example.WebBanNhac.entity.Phieudathang;
import com.example.WebBanNhac.services.PDHServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/PDHs")
public class PdhController {
    @Autowired

    private PDHServices pdhServices;

    @GetMapping
    public String showAllPdh(Model model){
        List<Phieudathang> PDHs = pdhServices.getAllPDHs();
        model.addAttribute("PDHs",PDHs);
        return "PDH/List";
    }
    @GetMapping("/add")
    public String addPDhForm(Model model){
        model.addAttribute("PDH", new Phieudathang());
        return "PDH/add";
    }
    @PostMapping("/add")
   public String AddPDH (@ModelAttribute("PDH")@Validated Phieudathang PDH, BindingResult result){
        if (result.hasErrors()){
            return "PDH/add";
        } else {
            pdhServices.updatePDH(PDH);
            return "redirect:/PDHs";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditPDHForm(@PathVariable("id") Long id, Model model) {
        Phieudathang phieudathang = pdhServices.getPDHById(id);
        model.addAttribute("PDH",phieudathang );
        return "PDH/edit";
    }

    @PostMapping("/edit")
    public String editPDH( @ModelAttribute("PDH") @Validated Phieudathang phieudathang, BindingResult result) {
        if (result.hasErrors()) {
            return "PDH/edit";
        } else {
            pdhServices.updatePDH(phieudathang);
            return "redirect:/PDHs";
        }
    }
    @GetMapping("/delete/{id}")
    public String deletePDH(@PathVariable("id") Long id){
        pdhServices.deletePDH(id);
        return "redirect:/PDHs";
    }


}
