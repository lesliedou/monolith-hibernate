package com.begcode.demo.hibernate.repository.base;

import com.begcode.demo.hibernate.domain.Department;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface DepartmentRepositoryWithBagRelationships {
    Optional<Department> fetchBagRelationships(Optional<Department> department);

    List<Department> fetchBagRelationships(List<Department> departments);

    Page<Department> fetchBagRelationships(Page<Department> departments);
}
