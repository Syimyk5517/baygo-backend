package com.example.baygo.repository;

import com.example.baygo.db.dto.request.UpdateSizeDTO;
import com.example.baygo.db.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {

    @Query("SELECT NEW com.example.baygo.db.dto.request.UpdateSizeDTO(s.id, s.size) FROM SubProduct sp JOIN sp.sizes s WHERE sp.id = ?1")
    List<UpdateSizeDTO> getSizesBySubProductId(Long subProductId);
}