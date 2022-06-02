package pl.carsrental.branch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    List<Branch> getBranches();

    void addBranch(Branch branch);

    Branch getBranchByAddress(String address);

    Branch getBranchById(Long branchId);

    void deleteBranchById(Long branchId);

}
