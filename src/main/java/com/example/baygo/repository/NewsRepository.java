package com.example.baygo.repository;

import com.example.baygo.db.dto.response.NewsResponse;
import com.example.baygo.db.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    @Query("select new com.example.baygo.db.dto.response.NewsResponse(n.id,n.topic,n.description,n.date) from News n order by n.date desc limit 2")
    List<NewsResponse> getAllNews();
}
