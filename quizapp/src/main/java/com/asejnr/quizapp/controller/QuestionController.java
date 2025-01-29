package com.asejnr.quizapp.controller;

import com.asejnr.quizapp.model.Question;
import com.asejnr.quizapp.service.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions() throws Exception {
        List<Question> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @PostMapping
    public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
        Question newQuestion = questionService.createQuestion(question);
        System.out.println(newQuestion);
        return ResponseEntity.status(HttpStatus.CREATED).body(newQuestion);
    }
    @GetMapping("/category")
    public  ResponseEntity<List<Question>> getQuestionsByCategory(@RequestParam(name = "category") String category) {
        List<Question> questions = questionService.getQuestionsByCategoryIgnoreCase(category);

        if (questions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(questions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@RequestBody Question question, @PathVariable int id) {
        if (!questionService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Question updatedQuestion = questionService.updateQuestion(id,question);
        return ResponseEntity.ok(updatedQuestion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Question> deleteQuestion(@PathVariable int id) {
        if(!questionService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
