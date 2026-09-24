package com.pcbuilder.build.service;

import com.pcbuilder.build.dto.BuildRequest;
import com.pcbuilder.build.dto.BuildResponse;
import java.util.List;

public interface BuildService {
    BuildResponse create(BuildRequest request);
    List<BuildResponse> getAll();
    BuildResponse getById(Long id);
    BuildResponse update(Long id, BuildRequest request);
    BuildResponse validate(Long id);
    void delete(Long id);
}
