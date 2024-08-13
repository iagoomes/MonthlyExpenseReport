package br.com.postech.grupo7.monthlyexpensereport.domain.file.server;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileServerRepository extends JpaRepository<FileServer, Integer> {
}
