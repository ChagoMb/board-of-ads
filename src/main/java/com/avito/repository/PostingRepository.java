package com.avito.repository;

import com.avito.models.Category;
import com.avito.models.User;
import com.avito.models.posting.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostingRepository extends JpaRepository<Posting, Long> {

    List<Posting> findAllByUser(User user);

    List<Posting> findAllByLocationCode(String locationCode);

    List<Posting> findByCategory(Category category);

    List<Posting> findAllByFullDescription(String fullDescription);


}
