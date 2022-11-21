package com.ssafy.db.repository;

import com.ssafy.db.entity.Gifticon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GifticonRepository extends JpaRepository<Gifticon, Long> {
    Optional<List<Gifticon>> findAllByUserUserIdOrderByGifticonPeriod(Long userId);
    Optional<Gifticon> findByGifticonId(Long gifticonId);
}
