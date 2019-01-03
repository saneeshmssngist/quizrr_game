package com.astalavista.saneesh.quizrrgame;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.astalavista.saneesh.quizrrgame.Interfaces.MyDao;
import com.astalavista.saneesh.quizrrgame.Model.QuizTable;


/**
 * Created by saNeesH on 2018-07-25.
 */

@Database(entities = {QuizTable.class}, version = 1)
public abstract class RoomDatabaseRoom extends RoomDatabase {

    public abstract MyDao myDao();


}
