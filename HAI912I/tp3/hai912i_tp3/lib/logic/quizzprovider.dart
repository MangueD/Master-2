import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';
import 'package:hai912i_tp3/logic/question.dart';
import 'package:hai912i_tp3/presentation/results.dart';

import '../main.dart';

class QuizzProvider with ChangeNotifier{
  List<Question> quizz = [];
  int currQ = 0;
  int score = 0;

  QuizzProvider(this.quizz);

  void setQuizz(List<Question> q){
    quizz = q;
  }

  void nextQuestion(BuildContext context) {
    int size = quizz.length;
    if(currQ < size - 1){
      currQ++;
    }
    else {
      Navigator.pushReplacement(context, MaterialPageRoute(builder: (context) => Results(score, quizz.length)));
    }
    notifyListeners();
  }

  void checkAnswer(bool userChoice, BuildContext context){
    if(userChoice == quizz.elementAt(currQ).isCorrect) {
      score ++;
    }
    notifyListeners();
  }

}