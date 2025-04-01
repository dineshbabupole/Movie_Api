package com.movieflex.movieApi.repositories;

import com.movieflex.movieApi.entites.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Integer> {
}
