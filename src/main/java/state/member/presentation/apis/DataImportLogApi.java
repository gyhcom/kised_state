package state.member.presentation.apis;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import state.member.application.fasade.DataImportLogManager;
import state.member.domain.entity.DataImport;

import java.security.Principal;

@Controller
@RequestMapping("/data-import-log")
@RequiredArgsConstructor
public class DataImportLogApi {
    private final DataImportLogManager manager;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String showLogPage(Model model) {
        model.addAttribute("logs", manager.getAll());
        return "services/dataImport/data-import-log";
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("type") String type,
                         Principal principal) {
        try {
            manager.save(new DataImport(null, type, file.getOriginalFilename(), "성공", null, null, principal.getName()));
        } catch (Exception e) {
            manager.save(new DataImport(null, type, file.getOriginalFilename(), "실패", e.getMessage(), null, principal.getName()));
        }
        return "redirect:/data-import-log";
    }
}
