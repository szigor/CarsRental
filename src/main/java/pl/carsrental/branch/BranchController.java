package pl.carsrental.branch;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @GetMapping(path = "/branches")
    public String getBranch(ModelMap modelMap) {
        modelMap.addAttribute("branches", branchService.getBranches());
        return "branch-list";
    }

    @PostMapping
    public void addBranch(@RequestBody Branch branch) {
        branchService.addBranch(branch);
    }

    @DeleteMapping(path = "{branchId}")
    public void deleteBranch(@PathVariable("branchId") Long branchId) {
        branchService.deleteBranchById(branchId);
    }
}
