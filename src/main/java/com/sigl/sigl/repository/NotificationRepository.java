package com.sigl.sigl.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sigl.sigl.model.Notification;

@RepositoryRestResource
public interface NotificationRepository extends JpaRepository<Notification, Long> {
  Optional<List<Notification>> findByUserId(Long userId);

  void deleteNotificationById(Long userId);
}
