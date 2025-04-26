package com.mumu.mumu.service;

import com.mumu.mumu.domain.Edu;
import com.mumu.mumu.domain.Space;
import com.mumu.mumu.dto.EduMarkerDTO;
import com.mumu.mumu.dto.SpaceMarkerDTO;
import com.mumu.mumu.repository.EducationRepository;
import com.mumu.mumu.repository.SpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MapMarkerService {

    private final EducationRepository eduRepository;
    private final SpaceRepository spaceRepository;

    public List<EduMarkerDTO> getEduMarkers(List<String> regions, List<String> fields, List<String> statuses) {
        List<Edu> eduList = eduRepository.findAll();

        if (regions != null && !regions.isEmpty()) {
            eduList = eduList.stream()
                    .filter(edu -> regions.contains(edu.getEduRegion()))
                    .collect(Collectors.toList());
        }

        if (fields != null && !fields.isEmpty()) {
            eduList = eduList.stream()
                    .filter(edu -> fields.contains(edu.getField()))
                    .collect(Collectors.toList());
        }

        if (statuses != null && !statuses.isEmpty()) {
            eduList = eduList.stream()
                    .filter(edu -> statuses.contains(edu.getRecruitmentStatus()))
                    .collect(Collectors.toList());
        }

        return eduList.stream()
                .map(edu -> new EduMarkerDTO(
                        edu.getEduId(),
                        edu.getEduName(),
                        edu.getEduLocationName(),
                        edu.getEduAddress(),
                        edu.getEduLocationLongitude(),
                        edu.getEduLocationLatitude(),
                        edu.getEduImage()
                ))
                .collect(Collectors.toList());
    }

    public List<SpaceMarkerDTO> getSpaceMarkers(List<String> regions, List<String> targets, List<String> types) {
        List<Space> spaceList = spaceRepository.findAll();

        if (regions != null && !regions.isEmpty()) {
            spaceList = spaceList.stream()
                    .filter(space -> regions.contains(space.getRegion()))
                    .collect(Collectors.toList());
        }

        if (targets != null && !targets.isEmpty()) {
            spaceList = spaceList.stream()
                    .filter(space -> targets.contains(space.getSpaceTarget()))
                    .collect(Collectors.toList());
        }

        if (types != null && !types.isEmpty()) {
            spaceList = spaceList.stream()
                    .filter(space -> types.contains(space.getSpaceType()))
                    .collect(Collectors.toList());
        }

        return spaceList.stream()
                .map(space -> new SpaceMarkerDTO(
                        space.getSpaceId(),
                        space.getSpaceName(),
                        space.getSpaceLocation(),
                        space.getSpaceLocationLongitude(),
                        space.getSpaceLocationLatitude(),
                        space.getSpaceImage()
                ))
                .collect(Collectors.toList());
    }
}