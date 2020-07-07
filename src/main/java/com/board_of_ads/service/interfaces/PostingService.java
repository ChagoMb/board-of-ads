package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.User;
import com.board_of_ads.models.posting.Posting;

import java.util.List;

public interface PostingService {

    Posting addPosting(Posting posting);

    Posting getPostingById(Long id);

    List<Posting> getUserPostings(User user);

    List<Posting> getAllPostings();

    List<Posting> getPostingsByCityId(String cityId);

    List<Posting> getPostingsByRegionId(String regionId);

}
