package org.zerock.club.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
public class SampleController {
    @GetMapping("/all")
    public String exAll() {
        log.info("exAll..........");
        return "/all/all";
    }

    @GetMapping("/member")
    public String exMember() {
        log.info("exMember..........");
        return "/member/member";
    }

    @GetMapping("/admin")
    public String exAdmin() {
        log.info("exAdmin..........");
        return "/admin/admin";
    }
}
