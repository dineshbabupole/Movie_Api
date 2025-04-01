package com.movieflex.movieApi.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;

    @Column(nullable = false)
    @NotBlank(message = "enter the proper value in filed!")
    private String title;


    @Column(nullable = false)
    @NotBlank(message = "enter the proper value in filed!")
    private String director;

    @Column(nullable = false)
    @NotBlank(message = "enter the proper value in filed!")
    private String studio;

    @ElementCollection
    @CollectionTable(name = "movieCast")
    private Set<String> movieCast;


    private Integer releaseYear;

    @Column(nullable = false)
    @NotBlank(message = "enter the proper value in filed!")
    private String poster;
}

