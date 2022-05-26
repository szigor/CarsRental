package pl.carsrental.branch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.carsrental.client.Client;

@Slf4j
@Controller
@RequiredArgsConstructor
@Transactional
public class BranchController {

    private final BranchService branchService;

    @GetMapping(path = "/branches")
    public String getBranch(ModelMap modelMap) {
        modelMap.addAttribute("branches", branchService.getBranches());
        return "branch-list";
    }

    @GetMapping(path = "/admin/branches")
    public String getBranches(ModelMap modelMap) {
        modelMap.addAttribute("branches", branchService.getBranches());
        return "branch-panel";
    }

    @GetMapping(path = "/admin/branch/create")
    public String showCreateBranchForm(ModelMap modelMap) {
        modelMap.addAttribute("emptyBranch", new Branch());
        return "branch-create";
    }

    @PostMapping(path = "/admin/branch/save")
    public String handleNewBranch(@ModelAttribute("emptyBranch") Branch branch) {
        log.info("Handle new branch: " + branch);
        branchService.addBranch(branch);
        return "redirect:/admin/branches";
    }

    @GetMapping(path = "/admin/branch/delete/{branchId}")
    public String deleteBranch(@PathVariable("branchId") Long branchId) {
        branchService.deleteBranchById(branchId);
        log.info("Deleted branch with id " + branchId);
        return "redirect:/admin/branches";
    }
}
