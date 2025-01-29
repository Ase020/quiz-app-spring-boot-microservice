package com.asejnr.quizapp.service;

import com.asejnr.quizapp.model.Question;
import com.asejnr.quizapp.model.QuestionWrapper;
import com.asejnr.quizapp.model.Quiz;
import com.asejnr.quizapp.repository.QuestionRepository;
import com.asejnr.quizapp.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public ResponseEntity<Quiz> createQuiz(String category, int numberOfQuestions, String title) {
        List<Question> questions = questionRepository.findRandomQuestionsByCategory(category, numberOfQuestions);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quiz.setCategory(category);
        quiz.setNumberOfQuestions(numberOfQuestions);

        Quiz savedQuiz = quizRepository.save(quiz);
        return new ResponseEntity<>(savedQuiz, HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if (quiz.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Question> questions = quiz.get().getQuestions();
        if (questions == null || questions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<QuestionWrapper> questionsForUser = new ArrayList<>();

        for (Question question : questions) {
            QuestionWrapper questionWrapper = new QuestionWrapper(
                    question.getId(),
                    question.getQuestionTitle(),
                    question.getOption1(),
                    question.getOption2(),
                    question.getOption3(),
                    question.getOption4());
            questionsForUser.add(questionWrapper);
        }
        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }
}
