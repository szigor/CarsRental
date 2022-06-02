package pl.carsrental.branch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;

    public List<Branch> getBranches() {
        return branchRepository.findAll();
    }

    public void addBranch(Branch branch) {
        branchRepository.save(branch);
        log.info("Added " + branch);
    }

    public Branch getBranchByAddress(String address) {
        List<Branch> all = branchRepository.findAll();
        for (Branch branch : all) {
            if(branch.getAddress().equals(address)) {
                return branch;
            }
        }
        return null;
    }

    public Branch getBranchById(Long branchId) {
        boolean exists = branchRepository.existsById(branchId);
        if (!exists) {
            throw new IllegalStateException("branch with id " + branchId + " does not exists");
        }
        return branchRepository.getById(branchId);
    }

    public void deleteBranchById(Long branchId) {
        boolean exists = branchRepository.existsById(branchId);
        if (!exists) {
            throw new IllegalStateException("branch with id " + branchId + " does not exists");
        }
        branchRepository.deleteById(branchId);
    }
}
