package com.astalavista.saneesh.quizrrgame.Interfaces;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.astalavista.saneesh.quizrrgame.Model.QuizTable;

import java.util.List;

/**
 * Created by saNeesH on 2018-07-25.
 */

@Dao
public interface MyDao {


    @Insert
    void addQuizQuestions(QuizTable quizTable);

    @Query("SELECT * FROM `game_all` WHERE `status` = :paperNameNo ORDER BY random() LIMIT 1")
    QuizTable getQuizQuestions(String paperNameNo);

    @Query("SELECT * FROM `game_all` ORDER BY random() LIMIT 1")
    QuizTable getQuizQuestion();


}
