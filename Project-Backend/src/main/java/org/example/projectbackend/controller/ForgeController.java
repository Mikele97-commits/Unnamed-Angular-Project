package org.example.projectbackend.controller;

import org.example.projectbackend.dto.ForgingInfoDto;
import org.example.projectbackend.service.ForgeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/forge")
@CrossOrigin(origins = "http://localhost:4200")
public class ForgeController {

    ForgeService forgeService;
    public ForgeController(ForgeService forgeService){
        this.forgeService=forgeService;
    }
    @GetMapping("/getForgingInfo/{id}")
    public ForgingInfoDto getUpgradeCost(@PathVariable Long id){
        return forgeService.getForgingInfo(id);
    }

    @PostMapping("/upgrade/{id}")
    public String upgrade(@PathVariable Long id, @AuthenticationPrincipal String username){
        return forgeService.upgrade(id,username);
    }
}