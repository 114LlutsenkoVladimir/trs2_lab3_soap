package org.example.trs2_lab.controller;

import org.example.trs2_lab.dbType.DbSelectionContext;
import org.example.trs2_lab.dbType.DbType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/db-selection")
public class DbSelectController {
    private final DbSelectionContext context;

    @GetMapping("/")
    public String startPage() {
        return "db-select";
    }

    @ModelAttribute("dbTypes")
    public DbType[] dbTypes() {
        return DbType.values();
    }

    @ModelAttribute("currentDbType")
    public String currentDbType() {
        return context.getCurrentDbType().name();
    }

    public DbSelectController(DbSelectionContext context) {
        this.context = context;
    }

    @GetMapping("/db-select")
    public String dbSelect(@RequestParam DbType dbType) {
        context.setCurrentDbType(dbType);
        return "redirect:/";
    }
}
