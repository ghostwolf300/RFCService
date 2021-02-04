package org.rfc.material.rundata;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RunDataRepository extends JpaRepository<RunData, RunDataKey>,RunDataRepositoryCustom {

}
