package pl.carsrental.branch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/oddzialy")
public class BranchController {

    private final BranchService branchService;

    @Autowired
    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping
    public List<Branch> getBranch() {
        return branchService.getBranches();
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
