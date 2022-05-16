package pl.carsrental.branch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
public class BranchService {

    private final BranchRepository branchRepository;

    @Autowired
    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public List<Branch> getBranches() {
        return branchRepository.findAll();
    }

    public void addBranch(Branch branch) {
        branchRepository.save(branch);
        log.info("Added " + branch);
    }

    public void deleteBranchById(Long branchId) {
        boolean exists = branchRepository.existsById(branchId);
        if (!exists) {
            throw new IllegalStateException("branch with id " + branchId + " does not exists");
        }
        branchRepository.deleteById(branchId);
    }
}