package com.pcbuilder.build.repository;

import com.pcbuilder.build.entity.PcBuild;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PcBuildRepository extends JpaRepository<PcBuild, Long> {
}
