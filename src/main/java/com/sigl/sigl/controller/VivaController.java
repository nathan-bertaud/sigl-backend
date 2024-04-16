package com.sigl.sigl.controller;

import com.sigl.sigl.dto.UserNameFirstNameDto;
import com.sigl.sigl.dto.VivaDto;
import com.sigl.sigl.dto.VivaEditDto;
import com.sigl.sigl.service.VivaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("viva")
public class VivaController {

    @Autowired
    private VivaService service;
    @PostMapping("addJuries")
    public ResponseEntity<Object> addVivaWithJuries(Principal auth, @RequestBody Map<String, List<UserNameFirstNameDto>> requestBody) {
        try {
            List<UserNameFirstNameDto> juries = requestBody.get("juries");
            service.addJuries(auth.getName(), juries);
            return ResponseEntity.ok("juries créé avec succès");
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la création d'un juries", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("getVivas")
    public ResponseEntity<Page<VivaDto>> getJuries(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<VivaDto> juries = service.findAllJuries(pageable);
        return ResponseEntity.ok(juries);
    }

    @PostMapping("editViva")
    public ResponseEntity<Object> addVivaWithJuries(Principal auth, @RequestBody VivaEditDto vivas) {
        try {
            service.editViva(auth.getName(), vivas);
            return ResponseEntity.ok("viva edit avec succès");
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de l'edit d'un viva", HttpStatus.CONFLICT);
        }
    }
}
