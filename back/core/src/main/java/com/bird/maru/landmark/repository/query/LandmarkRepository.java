package com.bird.maru.landmark.repository.query;

import com.bird.maru.domain.model.entity.Landmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LandmarkRepository extends JpaRepository<Landmark, Long> {

}
