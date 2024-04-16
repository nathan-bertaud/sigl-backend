package com.sigl.sigl.repository;

import com.sigl.sigl.model.BiannualMeeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface BiannualMeetingRepository extends JpaRepository<BiannualMeeting, Long> {

}
