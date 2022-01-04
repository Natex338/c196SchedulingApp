package com.example.c196schedulingapp.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "term_table")
    public class Term {
        @PrimaryKey(autoGenerate = true)
        private int termID;
        private String termName;
        private long startDate;

        public Term(int termID, String termName, long startDate) {
            this.termID = termID;
            this.termName = termName;
            this.startDate= startDate;
        }

        @Override
        public String toString() {
            return "Term{" +
                    "termID=" + termID +
                    ", termName='" + termName + '\'' +
                    '}';
        }

        public void setTerID(int termID) {
            this.termID = termID;
        }

        public void setTermName(String termName) {
            this.termName = termName;
        }

        public int getTermID() {
            return termID;
        }

        public String getTermName() {
            return termName;
        }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }
}

