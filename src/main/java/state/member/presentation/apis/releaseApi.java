package state.member.presentation.apis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import state.member.application.fasade.ReleaseLogManager;
import state.member.domain.entity.ReleaseLog;

@Controller
@RequiredArgsConstructor
@RequestMapping("/release-log")
public class releaseApi {

    private final ReleaseLogManager releaseLogManager;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("logs", releaseLogManager.getAll());
        return "services/release/logPage";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("log", new ReleaseLog());
        return "services/release/logForm";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("log", releaseLogManager.getById(id));
        return "services/release/logDetail";
    }
    @PostMapping
    public String save(@ModelAttribute ReleaseLog log) {
        releaseLogManager.create(log);
        return "redirect:/release-log";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("log", releaseLogManager.getById(id));
        return "services/release/logForm";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute ReleaseLog updatedLog) {
        releaseLogManager.update(id, updatedLog);
        return "redirect:/release-log";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        releaseLogManager.delete(id);
        return "redirect:/release-log";
    }
}

