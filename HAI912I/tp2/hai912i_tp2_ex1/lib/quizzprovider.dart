import 'package:flutter/material.dart';
import 'package:hai912i_tp1_ex2/results.dart';

import 'main.dart';

class QuizzProvider with ChangeNotifier{
  List<Question> quizz;
  int currQ;
  int score = 0;

  QuizzProvider(this.currQ, this.quizz);


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