package com.asejnr.quizapp.repository;

import com.asejnr.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByCategoryIgnoreCase(String category);

    @Query(value = "SELECT * FROM question q WHERE LOWER(q.category)=LOWER(:category) ORDER BY RANDOM() LIMIT :numberOfQuestions", nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(@Param("category") String category,@Param("numberOfQuestions") int numberOfQuestions);
}
